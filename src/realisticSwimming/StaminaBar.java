package realisticSwimming;

import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import net.md_5.bungee.api.chat.TextComponent;

public class StaminaBar {
	
	private BossBar staminaBar;
	private Player p;
	
	StaminaBar(Player player){
		p = player;
		staminaBar = BossBarAPI.addBar(p, new TextComponent(RSMain.stamina), BossBarAPI.Color.GREEN, BossBarAPI.Style.PROGRESS, 1.0f);
	}
	
	public void updateBar(float staminaValue){
		staminaBar.setProgress(staminaValue/1000);
		setColor(staminaValue);
	}
	
	public void removeStaminaBar(){
		staminaBar.removePlayer(p);
	}
	
	private void setColor(float staminaValue){
		if(staminaValue > 700){
			//Do nothing because the bar is already green
		}else if(staminaValue > 400){
			staminaBar.setColor(BossBarAPI.Color.YELLOW);
		}else if(staminaValue > 300){
			staminaBar.setColor(BossBarAPI.Color.RED);
		}else{
			alert();
		}
	}
	
	private void alert(){
		if(staminaBar.getColor() == BossBarAPI.Color.RED){
			staminaBar.setColor(BossBarAPI.Color.YELLOW);
		}else if(staminaBar.getColor() == BossBarAPI.Color.YELLOW){
			staminaBar.setColor(BossBarAPI.Color.RED);
		}
	}

}
