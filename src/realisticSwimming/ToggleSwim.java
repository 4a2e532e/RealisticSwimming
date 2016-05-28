package realisticSwimming;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class ToggleSwim implements CommandExecutor {
	
	private Plugin plugin;
	private FixedMetadataValue meta;
	
	ToggleSwim(Plugin pl){
		plugin = pl;
		meta = new FixedMetadataValue(plugin, null);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(sender instanceof Player && arg3.length>0){
			Player p = (Player) sender;
			if(arg3[0].equalsIgnoreCase("on")){
				p.removeMetadata("swimmingDisabled", plugin);
				p.sendMessage(RSMain.swimmingEnabled);
				return true;
			}else if(arg3[0].equalsIgnoreCase("off")){
				p.setMetadata("swimmingDisabled", meta);
				p.sendMessage(RSMain.swimmingDisabled);
				return true;
			}
		}
		return false;
	}

}
