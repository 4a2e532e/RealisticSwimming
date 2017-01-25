package realisticSwimming;

import org.bukkit.entity.Player;

import me.fromgate.elytra.Elytra;

public class ElytraPluginCompatibility
{
	private Player player;
	private Elytra elytra;
	private boolean hasAutoGlide = false;
	
	public ElytraPluginCompatibility(Player player)
	{
		this.player = player;
		this.elytra = (Elytra) this.player.getServer().getPluginManager().getPlugin("Elytra");
		this.hasAutoGlide = elytraWillActivate(this.player);
	}
	
	@SuppressWarnings("static-access")
	private boolean elytraWillActivate(Player p)
	{
		if(this.elytra.getCfg().autoElytra){
			if(p.hasPermission("elytra.auto") && me.fromgate.elytra.util.Util.isElytraWeared(p)){
				return true;
			}else if(this.elytra.getCfg().autoElytraEquip){
				if(p.hasPermission("elytra.auto-equip") && me.fromgate.elytra.util.Util.hasElytraStorage(p)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isElytraActivating()
	{
		return this.hasAutoGlide;
	}
}