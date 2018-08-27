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
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import realisticSwimming.Config;
import realisticSwimming.Utility;
import realisticSwimming.events.PlayerStartFallingEvent;

public class RFallListener implements Listener{

	private Plugin plugin;

	public RFallListener(Plugin plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event){
		Player p = event.getPlayer();
		//p.sendMessage(""+p.getFallDistance());
		//p.sendMessage(""+p.getVelocity().getY());
		if(playerCanFall(p)){			
			//fix NCP false alarm
			//Utility.ncpFix(p);

			p.setGliding(true);
			FixedMetadataValue m = new FixedMetadataValue(plugin, null);
			p.setMetadata("falling", m);
		}else if(p.hasMetadata("falling")){
			p.removeMetadata("falling", plugin);
		}
	}
	
	@EventHandler
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent event){
		if(event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if(playerCanFall(p) && p.getLocation().subtract(0, 1, 0).getBlock().getType()!=Material.LEGACY_STATIONARY_WATER){
				
				//****************************** Changes by DrkMatr1984 START ******************************
				PlayerStartFallingEvent e = new PlayerStartFallingEvent(p);
				Bukkit.getServer().getPluginManager().callEvent(e);
				if(!e.isCancelled()){
					p.setVelocity(new Vector(p.getLocation().getDirection().getX()* Config.fallGlideSpeed, Config.fallDownwardSpeed*-1, p.getLocation().getDirection().getZ()*Config.fallGlideSpeed));
					event.setCancelled(true);
				}else{
					p.setGliding(false);
				}
				//****************************** Changes by DrkMatr1984 END ******************************
			}
		}
	}
	
	public boolean playerCanFall(Player p){
		if(!p.hasMetadata("fallingDisabled")&& Utility.playerHasPermission(p, "rs.user.fall") && p.getFallDistance()>Config.minFallDistance && Config.enableFall && p.getLocation().getBlock().getType()!=Material.LEGACY_STATIONARY_WATER && p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR){
			//****************************** Changes by DrkMatr1984 START ******************************
			if(isElytraDeploying(p)){
				return false;
			}
			//****************************** Changes by DrkMatr1984 END ******************************
			return true;
		}
		return false;
	}
	
	//****************************** Changes by DrkMatr1984 START ******************************
	public boolean isElytraDeploying(Player p){
		if(Bukkit.getPluginManager().isPluginEnabled("Elytra")){
			if(p.hasPermission("elytra.auto")){
				if(Utility.isElytraWeared(p)){
					return true;
				}else{
					if(p.hasPermission("elytra.auto-equip") && Utility.hasElytraStorage(p)){
						return true;
					}
				}
			}
		}	
		return false;
	}
	//****************************** Changes by DrkMatr1984 END ******************************
}
