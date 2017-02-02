/*
Copyright (c) 2016-2017 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.stamina;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import realisticSwimming.Config;
import realisticSwimming.Language;
import realisticSwimming.events.PlayerOutOfStaminaEvent;
import realisticSwimming.events.PlayerStaminaRefreshEvent;
import realisticSwimming.events.PlayerStopSwimmingEvent;
import realisticSwimming.main.RSwimListener;

public class Stamina extends BukkitRunnable {

	private Plugin plugin;
	private RSwimListener sl;

	private Player p;
	private float stamina = 1000;
	private int staminaResetTimer;
	private Scoreboard scoreboard;
	private Objective staminaObjective;
	private String oldObjectiveName = "";
	private StaminaBar staminaBar;
	private WeightManager weightManager;


	public Stamina(Plugin pl, Player player, RSwimListener swimListener){
		plugin = pl;
		p = player;
		sl = swimListener;
		weightManager = new WeightManager(p);
	}

	@Override
	public void run() {
		if(sl.playerCanSwim(p) && p.isOnline()){
			staminaResetTimer = 3*20/ Config.staminaUpdateDelay;

			if(Config.enableStamina){
				displayStamina();
			}

			adjustSpeedToWeight();

			if(stamina>0){
				//reduce stamina by base amount without respecting armor weight
				if(p.isSprinting()){
					stamina = stamina-Config.sprintStaminaUsage/20* Config.staminaUpdateDelay;
				}else{
					stamina = stamina-Config.swimStaminaUsage/20*Config.staminaUpdateDelay;
				}

				//reduce stamina by armor weight if enabled in the config
				if(Config.enableArmorWeight){
					stamina = stamina - weightManager.getWeight();
				}
			}else{

				if(Config.enableStamina){
					drown(p);

					//Fire PlayerOutOfStaminaEvent
					PlayerOutOfStaminaEvent event = new PlayerOutOfStaminaEvent(p);
					Bukkit.getServer().getPluginManager().callEvent(event);
				}
			}
		}else if(staminaResetTimer ==0 || !p.isOnline()){

			if(Config.enableStamina){
				hideStaminaBar();

				//Fire PlayerStaminaRefreshEvent
				PlayerStaminaRefreshEvent event = new PlayerStaminaRefreshEvent(p);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}

			if(p.hasMetadata("swimming")){

				//Fire PlayerStopSwimmingEvent
				PlayerStopSwimmingEvent event = new PlayerStopSwimmingEvent(p);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
			p.removeMetadata("swimming", plugin);

			//Debug
			//p.sendMessage("Stopping stamina system...");

			this.cancel();
		}else{
			staminaResetTimer--;
		}
	}

	private void adjustSpeedToWeight(){
		if(weightManager.getWeight() > Config.maxSprintingWeight && Config.enableArmorWeight){
			//warn player when trying to sprint but to heavy
			if(p.isSprinting() && Config.enableToHeavyToSprintWarning){
				//****************************** Changes by DrkMatr1984 START ******************************
				p.sendMessage(ChatColor.RED+ChatColor.translateAlternateColorCodes('&', Language.tooHeavyToSprint ));
				p.sendMessage(ChatColor.GOLD+ChatColor.translateAlternateColorCodes('&', Language.currentArmorWeight)+" "+ChatColor.RED+weightManager.getWeight()+" "+ChatColor.GOLD+ChatColor.translateAlternateColorCodes('&', Language.maximumSprintingWeightIs)+" "+ChatColor.AQUA+Config.maxSprintingWeight);
				//****************************** Changes by DrkMatr1984 END ******************************
			}
			//no sprinting when to heavy
			p.setSprinting(false);
		}
	}

	private void displayStamina(){
		String part1 = "";
		String part2 = "";
		String staminaBar;
		for(int i=0; i<stamina/100; i++){
			part1 = part1+"#";
		}

		for(int i=0; i<10-part1.length(); i++){
			part2 = part2+"#";
		}
		//****************************** Changes by DrkMatr1984 START ******************************
		staminaBar = part1+ChatColor.GRAY+part2;
		//****************************** Changes by DrkMatr1984 END ******************************

		if(stamina>700){
			staminaBar = ChatColor.GREEN+staminaBar;
		}else if(stamina>400){
			staminaBar = ChatColor.GOLD+staminaBar;
		}else if(stamina>200){
			staminaBar = ChatColor.RED+staminaBar;
		}else{
			staminaBar = ChatColor.DARK_RED+staminaBar;
		}
		//****************************** Changes by DrkMatr1984 START ******************************
		staminaBar = ChatColor.translateAlternateColorCodes('&', Language.stamina)+ChatColor.RESET+": "+staminaBar;
		//****************************** Changes by DrkMatr1984 END ******************************
		
		updateStaminaBar(staminaBar);
	}

	private void drown(Player p){
		p.setSprinting(false);

		if(Config.enableDrowning){
			//drag the player down
			p.setVelocity(new Vector(0, -1, 0));
			//prevent the staminaResetTimer from running out, so the stamina does NOT reset and the player drowns
			staminaResetTimer = 20*20/ Config.staminaUpdateDelay;
		}
	}
	
	private void updateStaminaBar(String title){
		if(Config.enableBossBar){
			if(staminaBar == null){
				initializeBossBar();
			}else{
				staminaBar.updateBar(stamina);
			}
		}else{
			updateScoreboard(title);
		}
	}

	private void updateScoreboard(String title){
		try{
			scoreboard = p.getScoreboard();
			staminaObjective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
			scoreboard.resetScores(oldObjectiveName);
			staminaObjective.getScore(title).setScore((int) stamina);
			oldObjectiveName = title;
		}catch(NullPointerException e){
			initializeScoreboard();
		}
	}

	private void initializeScoreboard(){
			scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			p.setScoreboard(scoreboard);
			//****************************** Changes by DrkMatr1984 START ******************************
			if(ChatColor.translateAlternateColorCodes('&', Language.stamina).length()>16)
				staminaObjective = scoreboard.registerNewObjective(ChatColor.translateAlternateColorCodes('&', Language.stamina).substring(0, 15), "dummy");
			else
				staminaObjective = scoreboard.registerNewObjective(ChatColor.translateAlternateColorCodes('&', Language.stamina), "dummy");
			//****************************** Changes by DrkMatr1984 END ******************************
			staminaObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	private void initializeBossBar(){
		staminaBar = StaminaBar.getNewStaminaBar(p);
	}
	
	private void hideStaminaBar(){
		// debug
		//p.sendMessage(ChatColor.DARK_RED + "[DEBUG] "+ChatColor.RED + "Hiding stamina bar...");

		try{
			if(Config.enableBossBar){
				staminaBar.removeStaminaBar();
			}else{
				scoreboard.resetScores(oldObjectiveName);
			}
		}catch(NullPointerException ignored){
			
		}
	}

}
