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
	
	RSListener listener = new RSListener();
	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(listener, this);
		
		FileConfiguration config = this.getConfig();
		
		config.addDefault("Minimal water depth", 1);
		minWaterDepth = config.getInt("Minimal water depth");
		
		config.addDefault("Enable swimming in creative mode", true);
		enabledInCreative = config.getBoolean("Enable swimming in creative mode");
		
		config.addDefault("Permissions required", false);
		permsReq = config.getBoolean("Permissions required");
		
		config.options().copyDefaults(true);
		saveConfig();
		
	}
	
	@Override
	public void onDisable(){
		
	}
}
