package realisticSwimming;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class RFallListener implements Listener{

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event){
		Player p = event.getPlayer();
		//p.sendMessage(""+p.getFallDistance());
		//p.sendMessage(""+p.getVelocity().getY());
		if(p.getFallDistance()>3 && RSMain.enableFall){
			p.setGliding(true);
		}
	}
	
	@EventHandler
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent event){
		if(event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if(event.getEntity().getFallDistance()>0 && RSMain.enableFall){
				p.setVelocity(new Vector(0, -1, 0));
				event.setCancelled(true);
			}
		}
	}
}
