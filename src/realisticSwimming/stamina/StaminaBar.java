/*
Copyright (c) 2016 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.stamina;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;
import realisticSwimming.Language;

public class StaminaBar {
	
	private BossBar staminaBar;
	private Player p;
	
	StaminaBar(Player player){
		p = player;
		staminaBar = BossBarAPI.addBar(p, new TextComponent(Language.stamina), BossBarAPI.Color.GREEN, BossBarAPI.Style.PROGRESS, 1.0f);
	}
	
	public void updateBar(float staminaValue){
		staminaBar.setProgress(staminaValue/1000);
		setColor(staminaValue);

		//TODO debug
		//Bukkit.broadcastMessage("Stamina: "+staminaValue);
	}
	
	public void removeStaminaBar(){
		staminaBar.removePlayer(p);
	}
	
	private void setColor(float staminaValue){
		//noinspection StatementWithEmptyBody
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
