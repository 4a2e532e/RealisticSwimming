package realisticSwimming;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import realisticSwimming.main.RSMain;

public class Placeholders extends PlaceholderExpansion {
	
	private static Plugin plugin;
	
	public Placeholders(Plugin minevolt) {
		plugin = minevolt;
	}
	
    @Override
    public boolean persist(){
        return true;
    }  

   @Override
   public boolean canRegister(){
       return true;
   }

   @Override
   public String getAuthor(){
       return plugin.getDescription().getAuthors().toString();
   }

	@Override
	public String getIdentifier(){
		return plugin.getDescription().getName();
	}

	@Override
	public String getVersion(){
		return plugin.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String identifier){
		if(identifier.equals("swim_toggled")){
			if(player.hasMetadata("swimmingDisabled"))
				return "false";
			else
				return "true";
		}
		if(identifier.equals("fall_toggled")){
			if(player.hasMetadata("fallingDisabled"))
				return "false";
			else
				return "true";
		}
		if(identifier.equals("is_swimming")){
			if(player.hasMetadata("swimming"))
				return "true";
			else
				return "false";
		}
		if(identifier.equals("is_falling")){
			if(player.hasMetadata("falling"))
				return "true";
			else
				return "false";
		}
		if(identifier.equals("current_stamina")){
			return Float.toString(RSMain.getMain().getPlayerStamina(player));
		}
		if(identifier.equals("max_stamina")){
			return Float.toString(1000);
		}
		return "";
	}
	
}
