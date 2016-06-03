/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public class Stamina extends BukkitRunnable {

	private Plugin plugin;
	private RSwimListener sl;

	private Player p;
	private int stamina = 1000;
	private int timer;
	private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	private Objective staminaObjective = scoreboard.registerNewObjective(RSMain.stamina, "dummy");
	private String oldObjectiveName = "";
	

	Stamina(Plugin pl, Player player, RSwimListener swimListener){
		plugin = pl;
		p = player;
		sl = swimListener;
		staminaObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		p.setScoreboard(scoreboard);

	}

	@Override
	public void run() {
		if(sl.playerCanSwim(p)){
			timer = 3;
			displayStamina(p);
			if(stamina>0){
				if(p.isSprinting()){
					stamina = stamina-RSMain.sprintStaminaUsage;
				}else{
					stamina = stamina-RSMain.swimStaminaUsage;
				}
			}else{
				drown(p);
			}
		}else if(timer==0){
			p.removeMetadata("swimming", plugin);
			scoreboard.resetScores(oldObjectiveName);
			//p.sendMessage("Stamina refreshed");
			this.cancel();
		}else{
			timer--;
		}

	}
	
	private void displayStamina(Player p){
		String part1 = "";
		String part2 = "";
		String staminaBar;
		for(int i=0; i<stamina/100; i++){
			part1 = part1+"#";
		}

		for(int i=0; i<10-part1.length(); i++){
			part2 = part2+"#";
		}

		staminaBar = part1+ChatColor.GRAY+part2;
		
		if(stamina>700){
			staminaBar = ChatColor.GREEN+staminaBar;
		}else if(stamina>400){
			staminaBar = ChatColor.GOLD+staminaBar;
		}else if(stamina>200){
			staminaBar = ChatColor.RED+staminaBar;
		}else{
			staminaBar = ChatColor.DARK_RED+staminaBar;
		}
		updateScoreboard(staminaBar);
	}

	private void drown(Player p){
		p.setSprinting(false);

		if(RSMain.enableDrowning){
			p.setVelocity(new Vector(0, -1, 0));
		}
	}
	
	private void updateScoreboard(String title){
		scoreboard.resetScores(oldObjectiveName);
		staminaObjective.getScore(title).setScore(stamina);
		oldObjectiveName = title;
	}
}
