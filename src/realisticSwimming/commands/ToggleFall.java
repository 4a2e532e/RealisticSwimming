/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import realisticSwimming.Language;
import realisticSwimming.events.PlayerDisableFallingEvent;
import realisticSwimming.events.PlayerEnableFallingEvent;

public class ToggleFall extends RSCommand {
	
	private Plugin plugin;
	private FixedMetadataValue meta;
	
	public ToggleFall(Plugin pl){
		plugin = pl;
		meta = new FixedMetadataValue(plugin, null);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(sender instanceof Player && arg3.length>0){
			Player p = (Player) sender;
			if(arg3[0].equalsIgnoreCase("on")){
				p.removeMetadata("fallingDisabled", plugin);
				sendMessage(p, ChatColor.translateAlternateColorCodes('&', Language.fallingEnabled));

				//Fire PlayerEnableFallingEvent
				PlayerEnableFallingEvent event = new PlayerEnableFallingEvent(p);
				Bukkit.getServer().getPluginManager().callEvent(event);

				return true;

			}else if(arg3[0].equalsIgnoreCase("off")){
				p.setMetadata("fallingDisabled", meta);
				sendMessage(p, ChatColor.translateAlternateColorCodes('&', Language.fallingDisabled));

				//Fire PlayerDisableFallingEvent
				PlayerDisableFallingEvent event = new PlayerDisableFallingEvent(p);
				Bukkit.getServer().getPluginManager().callEvent(event);

				return true;
			}
		}
		return false;
	}
}
