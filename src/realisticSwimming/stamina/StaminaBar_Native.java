/*
Copyright (c) 2016-2017 4a2e532e

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package realisticSwimming.stamina;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import realisticSwimming.Language;

public class StaminaBar_Native extends StaminaBar {

    private BossBar staminaBar;

    StaminaBar_Native(Player player){
        super(player);
        if(ChatColor.translateAlternateColorCodes('&', Language.stamina).length()>64){
        	staminaBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', Language.stamina).substring(0, 63), BarColor.GREEN, BarStyle.SOLID);
        }
        staminaBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', Language.stamina), BarColor.GREEN, BarStyle.SOLID);
        staminaBar.addPlayer(p);

    }

    @Override
    public void updateBar(float staminaValue) {
        staminaBar.setProgress(staminaValue/1000);
        setColor(staminaValue);
    }

    @Override
    public void removeStaminaBar() {
        staminaBar.removePlayer(p);
    }

    @Override
    protected void setColor(float staminaValue) {
        //noinspection StatementWithEmptyBody
        if(staminaValue > 700){
            //Do nothing because the bar is already green
        }else if(staminaValue > 400){
            staminaBar.setColor(BarColor.YELLOW);
        }else if(staminaValue > 300){
            staminaBar.setColor(BarColor.RED);
        }else{
            alert();
        }
    }

    @Override
    protected void alert() {
        if(staminaBar.getColor() == BarColor.RED){
            staminaBar.setColor(BarColor.YELLOW);
        }else if(staminaBar.getColor() == BarColor.YELLOW){
            staminaBar.setColor(BarColor.RED);
        }
    }
}
