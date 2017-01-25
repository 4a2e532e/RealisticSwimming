/*
Copyright (c) 2016-2017 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.stamina;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;
import org.inventivetalent.bossbar.BossBarAPI.Color;

import realisticSwimming.Language;

public class StaminaBar_BossBarApi extends StaminaBar{
	
	private BossBar staminaBar;
	private Color currentColor;
	private BaseComponent[] component;

	StaminaBar_BossBarApi(Player player){
		super(player);
		this.component = TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',Language.stamina));
		this.staminaBar = BossBarAPI.addBar(p, new TextComponent(component), BossBarAPI.Color.GREEN, BossBarAPI.Style.PROGRESS, 1.0f);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateBar(float staminaValue){
		float progress = staminaValue/1000;
		if(BossBarAPI.hasBar(p)){
			currentColor = BossBarAPI.getBossBar(p).getColor();
			BossBarAPI.removeAllBars(p);
		}		
		staminaBar = BossBarAPI.addBar(p, new TextComponent(component), getColor(staminaValue), BossBarAPI.Style.PROGRESS, progress);
		//TODO debug
		//Bukkit.broadcastMessage("Stamina: "+staminaValue);
	}

	@Override
	public void removeStaminaBar(){
		staminaBar.removePlayer(p);
	}

	private Color getColor(float staminaValue){
		//noinspection StatementWithEmptyBody
		if(staminaValue > 700){
			return BossBarAPI.Color.GREEN;
		}else if(staminaValue > 450){
			return BossBarAPI.Color.YELLOW;
		}else if(staminaValue > 200){
			return BossBarAPI.Color.RED;
		}else{
			if(currentColor!=null){
				if(currentColor == BossBarAPI.Color.RED){
					return BossBarAPI.Color.YELLOW;
				}else if(currentColor == BossBarAPI.Color.YELLOW){
					return BossBarAPI.Color.RED;
				}
			}			
		}
		return BossBarAPI.Color.RED;
	}

	@Override
	protected void alert(){
		//Not used, but must inherit
	}

	@Override
	protected void setColor(float staminaValue) {
		//Not used, but must inherit
	}

}
