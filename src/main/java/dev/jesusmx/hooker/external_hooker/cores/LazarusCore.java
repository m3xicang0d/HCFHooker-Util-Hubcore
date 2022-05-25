package dev.jesusmx.hooker.external_hooker.cores;

import dev.jesusmx.hooker.external_hooker.interfaces.HCFCore;
import dev.mexican.hooks.hcf.interfaces.HCFCore;
import dev.mexican.hooks.hcf.utils.TimeFormater;
import me.qiooip.lazarus.Lazarus;
import me.qiooip.lazarus.config.Config;
import me.qiooip.lazarus.deathban.Deathban;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

public class LazarusCore implements HCFCore {
    @Override
    public boolean isDeathban(UUID uuid) {
        if(!Config.DEATHBAN_ENABLED) {
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return false;
        }
        return Lazarus.getInstance().getDeathbanManager().isDeathBanned(uuid);
    }

    @Override
    public String getDeathban(UUID uuid) {
        if(!Config.DEATHBAN_ENABLED) {
            return "Loading";
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return "Loading";
        }
        Deathban deathban = Lazarus.getInstance().getDeathbanManager().getDeathban(uuid);
        if(deathban == null) {
            return "Loading";
        }
        if(!isDeathban(uuid)) {
            return "Loading";
        }
        long reaming = deathban.getBannedUntil() - System.currentTimeMillis();
        if(reaming < 0) {
            return "Loading";
        }
        return TimeFormater.format(reaming);
    }

    @Override
    public int getLives(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return 0;
        }
        return Lazarus.getInstance().getDeathbanManager().getLives(Bukkit.getOfflinePlayer(uuid));
    }
}
