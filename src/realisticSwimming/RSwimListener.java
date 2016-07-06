/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class RSwimListener implements Listener {
	
	private Plugin plugin;
	
	RSwimListener(Plugin p){
		plugin = p;
	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event){

		Player p = event.getPlayer();
		ItemStack elytra = p.getInventory().getChestplate();
		
		if(playerCanSwim(p)){
			if(event.getTo().getY()<=event.getFrom().getY() || RSMain.enableSwimmingUp){
				
				//Only start swimming animation if the user did not disable it
				if(!p.hasMetadata("swimmingDisabled") && playerHasPermission(p, "rs.user.swim")){
					p.setGliding(true);
				}
				
				//EXPERMIMENTAL fix to prevent elytra from loosing durability while swimming
				if(RSMain.durabilityLoss==false && elytra!=null && elytra.getType()==Material.ELYTRA && !elytra.getEnchantments().containsKey(Enchantment.DURABILITY)){
					ItemMeta meta = elytra.getItemMeta();
					meta.addEnchant(Enchantment.DURABILITY, 100, true);
					elytra.setItemMeta(meta);
				}

			}else if(event.getTo().getY()<=62){
				p.setGliding(false);
			}
		}else{

			//EXPERMIMENTAL fix to prevent elytra from loosing durability while swimming
			if(RSMain.durabilityLoss==false && elytra!=null && elytra.getType()==Material.ELYTRA && elytra.getEnchantmentLevel(Enchantment.DURABILITY)==100){
				ItemMeta meta = elytra.getItemMeta();
				meta.removeEnchant(Enchantment.DURABILITY);
				elytra.setItemMeta(meta);
			}

		}
	}

	@EventHandler
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent event){
		if(event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if(playerCanSwim(p) && !p.hasMetadata("swimmingDisabled")){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event){
		try{
			if(event.getCurrentItem().getType()==Material.ELYTRA && event.getInventory().getHolder() instanceof Player){
				ItemStack elytra = event.getCurrentItem();
				if(RSMain.durabilityLoss==false && elytra!=null && elytra.getType()==Material.ELYTRA && elytra.getEnchantmentLevel(Enchantment.DURABILITY)==100){
					ItemMeta meta = elytra.getItemMeta();
					meta.removeEnchant(Enchantment.DURABILITY);
					elytra.setItemMeta(meta);
				}
			}
		}catch(NullPointerException e){
			
		}
	}

	//checks if the swim animation should be started or not
	public boolean playerCanSwim(Player p){
		if(p.getLocation().getBlock().getType()==Material.STATIONARY_WATER && p.getLocation().subtract(0, RSMain.minWaterDepth, 0).getBlock().getType()==Material.STATIONARY_WATER && p.getVehicle()==null && !playerIsInCreativeMode(p) && !p.isFlying()){
			
			//start the stamina system
			startStaminaSystem(p);
			
			if(playerHasPermission(p, "rs.user.boost") && RSMain.enableBoost){
				boost(p);
			}
			return true;
		}else{
			return false;
		}
	}

	public boolean playerIsInCreativeMode(Player p){
		if(RSMain.enabledInCreative){
			return false;
		}else if(p.getGameMode()==GameMode.CREATIVE){
			return true;
		}else{
			return false;
		}
	}

	public static boolean playerHasPermission(Player p, String perm){
		if(!RSMain.permsReq){
			return true;
		}else if(p.hasPermission(perm)){
			return true;
		}else{
			return false;
		}
	}

	public void boost(Player p){
		if(p.isSprinting() && (p.getLocation().getDirection().getY()<-0.1 || !RSMain.ehmCompatibility)){
			p.setVelocity(p.getLocation().getDirection().multiply(RSMain.sprintSpeed));
		}
	}
	
	public void startStaminaSystem(Player p){
		if(!p.hasMetadata("swimming") && RSMain.enableStamina && (!playerHasPermission(p, "rs.bypass.stamina") || RSMain.permsReq==false)){
			FixedMetadataValue m = new FixedMetadataValue(plugin, null);
			p.setMetadata("swimming", m);
			
			//start stamina system
			@SuppressWarnings("unused")
			BukkitTask stamina = new Stamina(plugin, p, this).runTaskTimer(plugin, 0, RSMain.refreshDelay);
		}
	}
}
