package realisticSwimming;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

public class RSneakListener implements Listener {
	
	private Plugin plugin;
	
	RSneakListener(Plugin p){
		plugin = p;
	}

	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event){
		Player p = event.getPlayer();
		if(!p.isSneaking()){
			p.setGliding(true);
		}
	}
	
	@EventHandler
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent event){
		if(event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if(p.isSneaking()){
				event.setCancelled(true);
			}
		}
	}
}
