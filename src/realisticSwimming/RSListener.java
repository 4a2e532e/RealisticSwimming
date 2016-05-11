/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class RSListener implements Listener {

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event){
		Player p = event.getPlayer();
		if(playerCanSwim(p)){
			p.setGliding(true);
		}
	}
	
	@EventHandler
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent event){
		if(event.getEntity() instanceof Player){
			if(playerCanSwim((Player)event.getEntity())){
				event.setCancelled(true);
			}
		}
	}
	
	//checks if the swim animation should be started or not
	public boolean playerCanSwim(Player p){
		if(playerHasPermission(p) && p.getLocation().getBlock().getType()==Material.STATIONARY_WATER && p.getLocation().subtract(0, RSMain.minWaterDepth, 0).getBlock().getType()==Material.STATIONARY_WATER && p.getVehicle()==null && !playerIsInCreativeMode(p)){
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
	
	public boolean playerHasPermission(Player p){
		if(!RSMain.permsReq){
			return true;
		}else if(p.hasPermission("rs.user.swim")){
			return true;
		}else{
			return false;
		}
	}
}
