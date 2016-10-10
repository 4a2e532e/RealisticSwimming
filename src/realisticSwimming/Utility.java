package realisticSwimming;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Utility {

    public static boolean playerHasPermission(Player p, String perm){
        if(!Config.permsReq){
            return true;
        }else if(p.hasPermission(perm)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean playerIsInCreativeMode(Player p){
        if(Config.enabledInCreative){
            return false;
        }else if(p.getGameMode()== GameMode.CREATIVE){
            return true;
        }else{
            return false;
        }
    }
}
