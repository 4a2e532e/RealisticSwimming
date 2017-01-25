package realisticSwimming;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.konsolas.aac.api.AACAPI;
import me.konsolas.aac.api.AACAPIProvider;
import me.konsolas.aac.api.HackType;
import me.konsolas.aac.api.PlayerViolationEvent;

public class AACCompatibilityFix implements Listener
{
	private AACAPI api;
	
	public AACCompatibilityFix(){
		this.api = AACAPIProvider.getAPI();
	}
	
	@EventHandler
    public void onPlayerViolation(PlayerViolationEvent e) {
        Player p = e.getPlayer();
        if(!p.hasMetadata("swimmingDisabled")){
        	if(api==null){
        		this.api = AACAPIProvider.getAPI();
        	}
        	if(api.isEnabled(HackType.FLY))
        	{
        		if(p.hasMetadata("swimming")){
        			e.setCancelled(true);
        		}
        	}
        }     
    }
}