/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Reload implements CommandExecutor {

	private RSMain main;

	Reload(RSMain rsMain){
		main = rsMain;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] arg3) {
		if((sender instanceof Player || sender instanceof ConsoleCommandSender) && arg3.length>0 && arg3[0].equalsIgnoreCase("reload")){
			main.reloadConfig();
			main.loadConfig();
			sender.sendMessage(ChatColor.AQUA+"[Realistic Swimming] "+ChatColor.GREEN+"Configuration reloaded!");
			return true;
		}else{
			sender.sendMessage("Commands:");
			sender.sendMessage("/rs reload - reloads the config");
			return true;
		}
	}

}
