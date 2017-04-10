/*
Copyright (c) 2016-2017 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import realisticSwimming.Config;
import realisticSwimming.Language;
import realisticSwimming.NoCheatPlusListener;
import realisticSwimming.commands.Reload;
import realisticSwimming.commands.ToggleFall;
import realisticSwimming.commands.ToggleSwim;
import realisticSwimming.debug.DebugListener;
import realisticSwimming.stamina.WeightManager;

import java.io.File;
import java.io.IOException;

public class RSMain extends JavaPlugin{

	RSwimListener swimListener = new RSwimListener(this);
	RFallListener fallListener = new RFallListener(this);
	RSneakListener sneakListener = new RSneakListener(this);
	DebugListener debugListener = new DebugListener();

	static RSMain main;

	public static RSMain getMain(){
		return main;
	}

	@Override
	public void onEnable(){
		main = this;

		try{
			if(getServer().getPluginManager().isPluginEnabled("NoCheatPlus")){
				Bukkit.broadcastMessage("Found NCP");
				new NoCheatPlusListener();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		getServer().getPluginManager().registerEvents(swimListener, this);
		getServer().getPluginManager().registerEvents(fallListener, this);

		//Debug---------------------------------------------------------------------------------------------------------
		//getServer().getPluginManager().registerEvents(debugListener, this);
		//End debug-----------------------------------------------------------------------------------------------------

		//getServer().getPluginManager().registerEvents(sneakListener, this);
		this.getCommand("rs").setExecutor(new Reload(this));
		this.getCommand("swim").setExecutor(new ToggleSwim(this));
		this.getCommand("fall").setExecutor(new ToggleFall(this));

		loadConfig();
	}

	@Override
	public void onDisable(){

	}

	public void loadConfig(){
		
		//setup config
		FileConfiguration config = this.getConfig();

		config.addDefault("NoCheatPlus compatibility mode", true);
		Config.noCheatPlusCompatibilityMode = config.getBoolean("NoCheatPlus compatibility mode");

		config.addDefault("Minimal water depth", 1);
		Config.minWaterDepth = config.getInt("Minimal water depth");

		config.addDefault("Enable swimming in creative mode", true);
		Config.enabledInCreative = config.getBoolean("Enable swimming in creative mode");

		config.addDefault("Permissions required", false);
		Config.permsReq = config.getBoolean("Permissions required");
		
		config.addDefault("Enable while swimming up", true);
		Config.enableSwimmingUp = config.getBoolean("Enable while swimming up");

		config.addDefault("Enable speed boost/sprinting", true);
		Config.enableBoost = config.getBoolean("Enable speed boost/sprinting");
		
		config.addDefault("Sprint speed", 0.3);
		Config.sprintSpeed = config.getDouble("Sprint speed");
		
		//config.addDefault("Enable sneak animation", false);
		//enableSneak = config.getBoolean("Enable sneak animation");
		
		config.addDefault("Enable fall animation", false);
		Config.enableFall = config.getBoolean("Enable fall animation");
		
		config.addDefault("Minimal fall distance", 3);
		Config.minFallDistance = config.getInt("Minimal fall distance");
		
		config.addDefault("Fall glide speed", 0.1);
		Config.fallGlideSpeed = config.getDouble("Fall glide speed");
		
		config.addDefault("Fall downward speed", 1);
		Config.fallDownwardSpeed = config.getDouble("Fall downward speed");
		
		config.addDefault("Elytra looses durability while swimming", false);
		Config.durabilityLoss = config.getBoolean("Elytra looses durability while swimming");
		
		config.addDefault("EHM_compatibility mode (no speed boost when swimming up)", false);
		Config.ehmCompatibility = config.getBoolean("EHM_compatibility mode (no speed boost when swimming up)");
		
		config.addDefault("Enable stamina", true);
		Config.enableStamina = config.getBoolean("Enable stamina");
		
		config.addDefault("Enable drowning when out of stmina", true);
		Config.enableDrowning = config.getBoolean("Enable drowning when out of stmina");
		
		config.addDefault("Sprint stamina usage", 50);
		Config.sprintStaminaUsage = config.getInt("Sprint stamina usage");
		
		config.addDefault("Normal swimming stamina usage", 10);
		Config.swimStaminaUsage = config.getInt("Normal swimming stamina usage");

		config.addDefault("Normal stamina reset time in seconds", 3);
		Config.normalStaminaResetTimeSeconds = config.getInt("Normal stamina reset time in seconds");

		config.addDefault("Stamina reset time when drowned in seconds", 20);
		Config.drownedStaminaResetTimeSeconds = config.getInt("Stamina reset time when drowned in seconds");

		config.addDefault("Blink stamina bar when stamina is low", true);
		Config.blinkStaminaBarWhenStaminaIsLow = config.getBoolean("Blink stamina bar when stamina is low");
		
		config.addDefault("Stamina update delay in ticks", 20);
		Config.staminaUpdateDelay = config.getInt("Stamina update delay in ticks");
		
		config.addDefault("Use BossBar to display stamina bar", true);
		Config.enableBossBar = config.getBoolean("Use BossBar to display stamina bar");

		config.addDefault("Disable swimming animation in waterfalls", true);
		Config.disableSwimInWaterfall = config.getBoolean("Disable swimming animation in waterfalls");

		config.addDefault("Maximum waterfall diameter", 3);
		Config.maxWaterfallDiameter = config.getInt("Maximum waterfall diameter");

		config.addDefault("Enable armor-weight", true);
		Config.enableArmorWeight = config.getBoolean("Enable armor-weight");

		config.addDefault("Max sprinting weight", 20);
		Config.maxSprintingWeight = config.getInt("Max sprinting weight");

		config.addDefault("Warn player when trying to sprint but to heavy", true);
		Config.enableToHeavyToSprintWarning = config.getBoolean("Warn player when trying to sprint but to heavy");

		config.addDefault("Show fullscreen warning when swimming in heavy armor", true);
		Config.enableHeavyArmorWarningTitle = config.getBoolean("Show fullscreen warning when swimming in heavy armor");

		config.addDefault("Announce armor weight when starting to swim", true);
		Config.announceWeight = config.getBoolean("Announce armor weight when starting to swim");


		//config for the armor-weight-system
		config.addDefault("Diamond Helmet Weight", 10);
		WeightManager.diamond_Helmet_Weight = config.getInt("Diamond Helmet Weight");

		config.addDefault("Diamond Chestplate Weight", 10);
		WeightManager.diamond_Chestplate_Weight = config.getInt("Diamond Chestplate Weight");

		config.addDefault("Diamond Leggings Weight", 10);
		WeightManager.diamond_Leggings_Weight = config.getInt("Diamond Leggings Weight");

		config.addDefault("Diamond Boots Weight", 10);
		WeightManager.diamond_Boots_Weight = config.getInt("Diamond Boots Weight");


		config.addDefault("Iron Helmet Weight", 7);
		WeightManager.iron_Helmet_Weight = config.getInt("Iron Helmet Weight");

		config.addDefault("Iron Chestplate Weight", 7);
		WeightManager.iron_Chestplate_Weight = config.getInt("Iron Chestplate Weight");

		config.addDefault("Iron Leggings Weight", 7);
		WeightManager.iron_Leggings_Weight = config.getInt("Iron Leggings Weight");

		config.addDefault("Iron Boots Weight", 7);
		WeightManager.iron_Boots_Weight = config.getInt("Iron Boots Weight");


		config.addDefault("Chain Helmet Weight", 5);
		WeightManager.chain_Helmet_Weight = config.getInt("Chain Helmet Weight");

		config.addDefault("Chain Chestplate Weight", 5);
		WeightManager.chain_Chestplate_Weight = config.getInt("Chain Chestplate Weight");

		config.addDefault("Chain Leggings Weight", 5);
		WeightManager.chain_Leggings_Weight = config.getInt("Chain Leggings Weight");

		config.addDefault("Chain Boots Weight", 5);
		WeightManager.chain_Boots_Weight = config.getInt("Chain Boots Weight");


		config.addDefault("Gold Helmet Weight", 6);
		WeightManager.gold_Helmet_Weight = config.getInt("Gold Helmet Weight");

		config.addDefault("Gold Chestplate Weight", 6);
		WeightManager.gold_Chestplate_Weight = config.getInt("Gold Chestplate Weight");

		config.addDefault("Gold Leggings Weight", 6);
		WeightManager.gold_Leggings_Weight = config.getInt("Gold Leggings Weight");

		config.addDefault("Gold Boots Weight", 6);
		WeightManager.gold_Boots_Weight = config.getInt("Gold Boots Weight");


		config.addDefault("Leather Helmet Weight", 2);
		WeightManager.leather_Helmet_Weight = config.getInt("Leather Helmet Weight");

		config.addDefault("Leather Chestplate Weight", 2);
		WeightManager.leather_Chestplate_Weight = config.getInt("Leather Chestplate Weight");

		config.addDefault("Leather Leggings Weight", 2);
		WeightManager.leather_Leggings_Weight = config.getInt("Leather Leggings Weight");

		config.addDefault("Leather Boots Weight", 2);
		WeightManager.leather_Boots_Weight = config.getInt("Leather Boots Weight");


		config.options().copyDefaults(true);
		saveConfig();
		
		//setup language file
		File language = new File(getDataFolder(), "language.yml");
		FileConfiguration lang = YamlConfiguration.loadConfiguration(language);
		
		lang.addDefault("Swimming enabled", "Swim animation enabled");
		Language.swimmingEnabled = lang.getString("Swimming enabled");
		
		lang.addDefault("Swimming disabled", "Swim animation disabled");
		Language.swimmingDisabled = lang.getString("Swimming disabled");
		
		lang.addDefault("Falling enabled", "Fall animation enabled");
		Language.fallingEnabled = lang.getString("Falling enabled");
		
		lang.addDefault("Falling disabled", "Fall animation disabled");
		Language.fallingDisabled = lang.getString("Falling disabled");
		
		lang.addDefault("Stamina", "Stamina");
		Language.stamina = lang.getString("Stamina");

		lang.addDefault("Too heavy to sprint", "You are too heavy to sprint!");
		Language.tooHeavyToSprint = lang.getString("Too heavy to sprint");

		lang.addDefault("Current armor weight is", "Your current armor weight is:");
		Language.currentArmorWeight = lang.getString("Current armor weight is");

		lang.addDefault("Heavy armor warning", "Swimming in heavy armor is a bad idea!");
		Language.heavyArmorWarning = lang.getString("Heavy armor warning");

		lang.addDefault("Maximum sprinting weight is", "Maximum sprinting weight is:");
		Language.maximumSprintingWeightIs = lang.getString("Maximum sprinting weight is");
		
		lang.options().copyDefaults(true);
		
		try {
			lang.save(language);
		} catch (IOException ignored) {

		}
	}
}
