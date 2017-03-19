/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.stamina;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import realisticSwimming.Config;
import realisticSwimming.Language;

public class WeightManager {

    public static int diamond_Helmet_Weight = 0;
    public static int diamond_Chestplate_Weight = 0;
    public static int diamond_Leggings_Weight = 0;
    public static int diamond_Boots_Weight = 0;

    public static int iron_Helmet_Weight = 0;
    public static int iron_Chestplate_Weight = 0;
    public static int iron_Leggings_Weight = 0;
    public static int iron_Boots_Weight = 0;

    public static int chain_Helmet_Weight = 0;
    public static int chain_Chestplate_Weight = 0;
    public static int chain_Leggings_Weight = 0;
    public static int chain_Boots_Weight = 0;

    public static int gold_Helmet_Weight = 0;
    public static int gold_Chestplate_Weight = 0;
    public static int gold_Leggings_Weight = 0;
    public static int gold_Boots_Weight = 0;

    public static int leather_Helmet_Weight = 0;
    public static int leather_Chestplate_Weight = 0;
    public static int leather_Leggings_Weight = 0;
    public static int leather_Boots_Weight = 0;

    private Player p;

    WeightManager(Player player){
        p = player;
        //****************************** Changes by DrkMatr1984 START ******************************
        if(Config.enableArmorWeight){
        	weightWarning();
        } 
        //****************************** Changes by DrkMatr1984 END ******************************
    }

    private void weightWarning(){
        ChatColor color;
        if(getWeight() > Config.maxSprintingWeight && Config.enableHeavyArmorWarningTitle){
            color = ChatColor.GOLD;

            try{
                //****************************** Changes by DrkMatr1984 START ******************************
                //noinspection deprecation
                p.sendTitle(ChatColor.RED+"/"+ChatColor.GOLD+"!"+ChatColor.RED+"\\", ChatColor.GOLD+ChatColor.translateAlternateColorCodes('&', Language.heavyArmorWarning),15,70,20);
                //****************************** Changes by DrkMatr1984 END ******************************
            }catch(NoSuchMethodError e){
                //Automatically disable this feature if it is not supported by this Minecraft version.
                Config.enableHeavyArmorWarningTitle = false;
            }

        }else{
            color = ChatColor.GREEN;
        }

        if(Config.announceWeight){
            //****************************** Changes by DrkMatr1984 START ******************************
            p.sendMessage(ChatColor.AQUA+ChatColor.translateAlternateColorCodes('&', Language.currentArmorWeight)+" "+color+getWeight());
            //****************************** Changes by DrkMatr1984 END ******************************
        }
    }

    public int getWeight(){
        PlayerInventory inv = p.getInventory();
        int weight = getItemWeight(inv.getHelmet()) + getItemWeight(inv.getChestplate()) + getItemWeight(inv.getLeggings()) + getItemWeight(inv.getBoots());

        //debug
        //p.sendMessage(ChatColor.AQUA+"Weight: "+weight);
        //end debug

        return weight;
    }

    private int getCustomWeight(ItemStack item){

        //prevent NullpointerExceptions
        if(item.getItemMeta().getLore() == null){
            return -1;
        }

        for(String lore: item.getItemMeta().getLore()){
            if(lore.contains("Weight: ")){
                return Integer.parseInt(lore.substring(8));
            }
        }
        return -1;
    }

    private int getItemWeight(ItemStack item){

        //prevent NullpointerExceptions
        if(item == null){
            return 0;
        }

        int cw = getCustomWeight(item);
        if(cw > -1){
            return cw;
        }

        if(item.getType() == Material.DIAMOND_HELMET){
            return diamond_Helmet_Weight;
        }
        if(item.getType() == Material.DIAMOND_CHESTPLATE){
            return diamond_Chestplate_Weight;
        }
        if(item.getType() == Material.DIAMOND_LEGGINGS){
            return diamond_Leggings_Weight;
        }
        if(item.getType() == Material.DIAMOND_BOOTS){
            return diamond_Boots_Weight;
        }

        if(item.getType() == Material.IRON_HELMET){
            return iron_Helmet_Weight;
        }
        if(item.getType() == Material.IRON_CHESTPLATE){
            return iron_Chestplate_Weight;
        }
        if(item.getType() == Material.IRON_LEGGINGS){
            return iron_Leggings_Weight;
        }
        if(item.getType() == Material.IRON_BOOTS){
            return iron_Boots_Weight;
        }

        if(item.getType() == Material.CHAINMAIL_HELMET){
            return chain_Helmet_Weight;
        }
        if(item.getType() == Material.CHAINMAIL_CHESTPLATE){
            return chain_Chestplate_Weight;
        }
        if(item.getType() == Material.CHAINMAIL_LEGGINGS){
            return chain_Leggings_Weight;
        }
        if(item.getType() == Material.CHAINMAIL_BOOTS){
            return chain_Boots_Weight;
        }

        if(item.getType() == Material.GOLD_HELMET){
            return gold_Helmet_Weight;
        }
        if(item.getType() == Material.GOLD_CHESTPLATE){
            return gold_Chestplate_Weight;
        }
        if(item.getType() == Material.GOLD_LEGGINGS){
            return gold_Leggings_Weight;
        }
        if(item.getType() == Material.GOLD_BOOTS){
            return gold_Boots_Weight;
        }

        if(item.getType() == Material.LEATHER_HELMET){
            return leather_Helmet_Weight;
        }
        if(item.getType() == Material.LEATHER_CHESTPLATE){
            return leather_Chestplate_Weight;
        }
        if(item.getType() == Material.LEATHER_LEGGINGS){
            return leather_Leggings_Weight;
        }
        if(item.getType() == Material.LEATHER_BOOTS){
            return leather_Boots_Weight;
        }

        return 0;
    }
}
