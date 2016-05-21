/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming;
import org.bukkit.configuration.file.FileConfiguration;
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

	RSwimListener swimListener = new RSwimListener();
	RFallListener fallListener = new RFallListener();
	RSneakListener sneakListener = new RSneakListener(this);

	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(swimListener, this);
		getServer().getPluginManager().registerEvents(fallListener, this);
		getServer().getPluginManager().registerEvents(sneakListener, this);
		this.getCommand("rs").setExecutor(new Reload(this));

		loadConfig();
	}

	@Override
	public void onDisable(){

	}

	public void loadConfig(){
		
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
		
		config.addDefault("Enable sneak animation", false);
		enableSneak = config.getBoolean("Enable sneak animation");
		
		config.addDefault("Enable fall animation", false);
		enableFall = config.getBoolean("Enable fall animation");
		
		config.addDefault("Elytra looses durability while swimming", false);
		durabilityLoss = config.getBoolean("Elytra looses durability while swimming");

		config.options().copyDefaults(true);
		saveConfig();
	}
}
