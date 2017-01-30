//****************************** Changes by DrkMatr1984 START ******************************

package realisticSwimming.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStartFallingEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private Player p;
    private boolean cancelled;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    public PlayerStartFallingEvent(Player player){
        this.p = player;
    }

    public Player getPlayer(){
        return this.p;
    }

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
}

//****************************** Changes by DrkMatr1984 END ******************************
