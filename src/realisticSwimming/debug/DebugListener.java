/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.debug;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import realisticSwimming.events.*;

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
