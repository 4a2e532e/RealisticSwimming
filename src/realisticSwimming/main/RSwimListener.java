/*
Copyright (c) 2016-2017 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import realisticSwimming.Config;
import realisticSwimming.Utility;
import realisticSwimming.events.PlayerStartSwimmingEvent;
import realisticSwimming.stamina.Stamina;

public class RSwimListener implements Listener{

    private Plugin plugin;

    RSwimListener(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){

        Player p = event.getPlayer();
        ItemStack elytra = p.getInventory().getChestplate();

        if(playerCanSwim(p)){
            if(event.getTo().getY()<=event.getFrom().getY() || Config.enableSwimmingUp){

                //Only start swimming animation if the user did not disable it
                if(!p.hasMetadata("swimmingDisabled") && Utility.playerHasPermission(p, "rs.user.swim")){

                    //fix NCP false alarm
                    //Utility.ncpFix(p);

                    //p.setGliding(true); Not needed for 1.13
                    startSwimming(p);
                    boost(p);
                }

                /* Not needed for 1.13
                //EXPERMIMENTAL fix to prevent elytra from loosing durability while swimming
                if(!Config.durabilityLoss && elytra!=null && elytra.getType()==Material.ELYTRA && !elytra.getEnchantments().containsKey(Enchantment.DURABILITY)){
                    ItemMeta meta = elytra.getItemMeta();
                    meta.addEnchant(Enchantment.DURABILITY, 100, true);
                    elytra.setItemMeta(meta);
                }
                */

            }
            /* Not needed for 1.13
            else if(event.getTo().getY()<=62){
                p.setGliding(false);
            }
            */

        /* Not needed for 1.13
        }else{

            //EXPERMIMENTAL fix to prevent elytra from loosing durability while swimming
            if(!Config.durabilityLoss && elytra!=null && elytra.getType()==Material.ELYTRA && elytra.getEnchantmentLevel(Enchantment.DURABILITY)==100){
                ItemMeta meta = elytra.getItemMeta();
                meta.removeEnchant(Enchantment.DURABILITY);
                elytra.setItemMeta(meta);
            }
            */
        }
    }

    /* Not needed for 1.13
    @EventHandler
    public void onEntityToggleGlideEvent(EntityToggleGlideEvent event){
        if(event.getEntity() instanceof Player){
            Player p = (Player) event.getEntity();
            if(playerCanSwim(p) && !p.hasMetadata("swimmingDisabled")){
                event.setCancelled(true);
            }
        }
    }
    */


    /* Not needed for 1.13
    //EXPERMIMENTAL fix to prevent elytra from loosing durability while swimming
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        try{
            if(event.getCurrentItem().getType()==Material.ELYTRA && event.getInventory().getHolder() instanceof Player){
                ItemStack elytra = event.getCurrentItem();
                if(!Config.durabilityLoss && elytra!=null && elytra.getType()==Material.ELYTRA && elytra.getEnchantmentLevel(Enchantment.DURABILITY)==100){
                    ItemMeta meta = elytra.getItemMeta();
                    meta.removeEnchant(Enchantment.DURABILITY);
                    elytra.setItemMeta(meta);
                }
            }
        }catch(NullPointerException ignored){

        }
    }
    */

    public void startSwimming(Player p){
        //start the stamina system
        if(!p.hasMetadata("swimming")){
            startStaminaSystem(p);
            FixedMetadataValue m = new FixedMetadataValue(plugin, null);
            p.setMetadata("swimming", m);

            //Fire PlayerStartSwimmingEvent
            PlayerStartSwimmingEvent event = new PlayerStartSwimmingEvent(p);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public boolean playerCanSwim(Player p){
        if(p.getLocation().getBlock().getType()==Material.WATER && p.getLocation().subtract(0, Config.minWaterDepth, 0).getBlock().getType()==Material.WATER && p.getVehicle()==null && !Utility.playerIsInCreativeMode(p) && !p.isFlying()){

            //TODO make configurable
            return !isInWaterElevator(p);

        }else{
            return false;
        }
    }

    public void boost(Player p){
        if(Utility.playerHasPermission(p, "rs.user.boost") && Config.enableBoost && p.isSprinting() && (p.getLocation().getDirection().getY()<-0.1 || !Config.ehmCompatibility)){
            p.setVelocity(p.getLocation().getDirection().multiply(Config.sprintSpeed));
        }
    }

    public void startStaminaSystem(Player p){
        if(!Utility.playerHasPermission(p, "rs.bypass.stamina") || !Config.permsReq){

            //Debug
            //p.sendMessage("Starting stamina system...");

            //****************************** Changes by DrkMatr1984 START ******************************
            BukkitRunnable stamina = new Stamina(plugin, p, this);
            stamina.runTaskTimer(plugin, 0, Config.staminaUpdateDelay);
            //****************************** Changes by DrkMatr1984 END ******************************
        }
    }

    public static boolean isInWaterElevator(Player p){

        if(!Config.disableSwimInWaterfall){
            return false;
        }

        //TODO make configurable
        int width = Config.maxWaterfallDiameter;

        if(p.getLocation().add(width, 0, 0).getBlock().getType() != Material.WATER
                && p.getLocation().add(-width, 0, 0).getBlock().getType() != Material.WATER
                && p.getLocation().add(0, 0, width).getBlock().getType() != Material.WATER
                && p.getLocation().add(0, 0, -width).getBlock().getType() != Material.WATER){
            return true;
        }else {
            return false;
        }
    }

    /* Not needed for 1.13
    @EventHandler
    public void onStatisticIncrement(PlayerStatisticIncrementEvent event){
        //Don't increment elytra statistic if the player is swimming.
        if(event.getStatistic() == Statistic.AVIATE_ONE_CM && event.getPlayer().hasMetadata("swimming")){
            event.setCancelled(true);
        }
    }
    */

    /* Not needed for 1.13
    //Block rocket-boost while swimming
    @EventHandler
    public void blockRocketBoost(PlayerInteractEvent event){
        if(event.hasItem() && event.getItem().getType() == Material.FIREWORK_ROCKET && event.getPlayer().hasMetadata("swimming")){
            event.setCancelled(true);
        }
    }
    */
}
