/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming;
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class RSMain extends JavaPlugin{

	static int minWaterDepth;
	static boolean enabledInCreative;
	static boolean permsReq;
	static boolean enableSwimmingUp;
	static double sprintSpeed;
	static boolean enableSneak;
	static boolean enableFall;
	static boolean durabilityLoss;
	static int minFallDistance;
	static boolean ehmCompatibility;
	static int sprintStaminaUsage;
	static int swimStaminaUsage;
	static boolean enableStamina;
	static boolean enableDrowning;
	static String swimmingEnabled;
	static String swimmingDisabled;
	static String fallingEnabled;
	static String fallingDisabled;
	static String stamina;
	static double fallGlideSpeed;
	static double fallDownwardSpeed;
	static boolean enableBossBar;

	RSwimListener swimListener = new RSwimListener(this);
	RFallListener fallListener = new RFallListener();
	RSneakListener sneakListener = new RSneakListener(this);

	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(swimListener, this);
		getServer().getPluginManager().registerEvents(fallListener, this);
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

		config.addDefault("Minimal water depth", 1);
		minWaterDepth = config.getInt("Minimal water depth");

		config.addDefault("Enable swimming in creative mode", true);
		enabledInCreative = config.getBoolean("Enable swimming in creative mode");

		config.addDefault("Permissions required", false);
		permsReq = config.getBoolean("Permissions required");
		
		config.addDefault("Enable while swimming up", true);
		enableSwimmingUp = config.getBoolean("Enable while swimming up");
		
		config.addDefault("Sprint speed", 0.3);
		sprintSpeed = config.getDouble("Sprint speed");
		
		//config.addDefault("Enable sneak animation", false);
		//enableSneak = config.getBoolean("Enable sneak animation");
		
		config.addDefault("Enable fall animation", false);
		enableFall = config.getBoolean("Enable fall animation");
		
		config.addDefault("Minimal fall distance", 3);
		minFallDistance = config.getInt("Minimal fall distance");
		
		config.addDefault("Fall glide speed", 0.1);
		fallGlideSpeed = config.getDouble("Fall glide speed");
		
		config.addDefault("Fall downward speed", 1);
		fallDownwardSpeed = config.getDouble("Fall downward speed");
		
		config.addDefault("Elytra looses durability while swimming", false);
		durabilityLoss = config.getBoolean("Elytra looses durability while swimming");
		
		config.addDefault("EHM_compatibility mode (no speed boost when swimming up)", false);
		ehmCompatibility = config.getBoolean("EHM_compatibility mode (no speed boost when swimming up)");
		
		config.addDefault("Enable stamina", true);
		enableStamina = config.getBoolean("Enable stamina");
		
		config.addDefault("Enable drowning when out of stmina", true);
		enableDrowning = config.getBoolean("Enable drowning when out of stmina");
		
		config.addDefault("Sprint stamina usage", 50);
		sprintStaminaUsage = config.getInt("Sprint stamina usage");
		
		config.addDefault("Normal swimming stamina usage", 10);
		swimStaminaUsage = config.getInt("Normal swimming stamina usage");
		
		config.addDefault("Use BossBarApi to display stamina bar (requires BossBarApi to be installed)", false);
		enableBossBar = config.getBoolean("Use BossBarApi to display stamina bar (requires BossBarApi to be installed)");

		config.options().copyDefaults(true);
		saveConfig();
		
		//setup language file
		File language = new File(getDataFolder(), "language.yml");
		FileConfiguration lang = YamlConfiguration.loadConfiguration(language);
		
		lang.addDefault("Swimming enabled", "Swim animation enabled");
		swimmingEnabled = lang.getString("Swimming enabled");
		
		lang.addDefault("Swimming disabled", "Swim animation disabled");
		swimmingDisabled = lang.getString("Swimming disabled");
		
		lang.addDefault("Falling enabled", "Fall animation enabled");
		fallingEnabled = lang.getString("Falling enabled");
		
		lang.addDefault("Falling disabled", "Fall animation disabled");
		fallingDisabled = lang.getString("Falling disabled");
		
		lang.addDefault("Stamina", "Stamina");
		stamina = lang.getString("Stamina");
		
		lang.options().copyDefaults(true);
		
		try {
			lang.save(language);
		} catch (IOException e) {

		}
	}
}
