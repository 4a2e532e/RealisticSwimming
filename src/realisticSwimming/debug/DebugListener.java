package realisticSwimming.debug;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import realisticSwimming.events.*;

/**
 * Created by Janne on 01.08.2016.
 */
public class DebugListener implements Listener {

    @EventHandler
    public void onPlayerDisableFallingEvent(PlayerDisableFallingEvent event){
        Bukkit.broadcastMessage(event+" (PlayerDisableFallingEvent) called for player "+event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerDisableSwimmingEvent(PlayerDisableSwimmingEvent event){
        Bukkit.broadcastMessage(event+" (PlayerDisableSwimmingEvent) called for player "+event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerEnableFallingEvent(PlayerEnableFallingEvent event){
        Bukkit.broadcastMessage(event+" (PlayerEnableFallingEvent) called for player "+event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerEnableSwimmingEvent(PlayerEnableSwimmingEvent event){
        Bukkit.broadcastMessage(event+" (PlayerEnableSwimmingEvent) called for player "+event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerOutOfStaminaEvent(PlayerOutOfStaminaEvent event){
        Bukkit.broadcastMessage(event+" (PlayerOutOfStaminaEvent) called for player "+event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerStaminaRefreshEvent(PlayerStaminaRefreshEvent event){
        Bukkit.broadcastMessage(event+" (PlayerStaminaRefreshEvent) called for player "+event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerStartSwimmingEvent(PlayerStartSwimmingEvent event){
        Bukkit.broadcastMessage(event+" (PlayerStartSwimmingEvent) called for player "+event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerStopSwimmingEvent(PlayerStopSwimmingEvent event){
        Bukkit.broadcastMessage(event+" (PlayerStopSwimmingEvent) called for player "+event.getPlayer().getName());
    }
}
