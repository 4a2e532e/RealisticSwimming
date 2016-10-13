/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import realisticSwimming.Config;
import realisticSwimming.Utility;

public class RFallListener implements Listener{

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event){
		Player p = event.getPlayer();
		//p.sendMessage(""+p.getFallDistance());
		//p.sendMessage(""+p.getVelocity().getY());
		if(playerCanFall(p)){

			//fix NCP false alarm
			Utility.ncpFix(p);

			p.setGliding(true);
		}
	}
	
	@EventHandler
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent event){
		if(event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if(playerCanFall(p) && p.getLocation().subtract(0, 1, 0).getBlock().getType()!=Material.STATIONARY_WATER){
				p.setVelocity(new Vector(p.getLocation().getDirection().getX()* Config.fallGlideSpeed, Config.fallDownwardSpeed*-1, p.getLocation().getDirection().getZ()*Config.fallGlideSpeed));
				event.setCancelled(true);
			}
		}
	}
	
	public boolean playerCanFall(Player p){
		if(!p.hasMetadata("fallingDisabled")&& Utility.playerHasPermission(p, "rs.user.fall") && p.getFallDistance()>Config.minFallDistance && Config.enableFall && p.getLocation().getBlock().getType()!=Material.STATIONARY_WATER){
			return true;
		}
		return false;
	}
}
