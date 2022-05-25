package dev.jesusmx.hooker.external_hooker.cores;

import dev.hely.hcf.configuration.Configuration;
import dev.hely.hcf.user.implement.deathban.DeathbanManager;
import dev.hely.hcf.user.implement.deathban.lives.LivesManager;
import dev.jesusmx.hooker.external_hooker.interfaces.HCFCore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class VoidCore implements HCFCore {

    @Override
    public boolean isDeathban(UUID uuid) {
        if (Configuration.KITMAP_MODE)  {
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return false;
        }

        return DeathbanManager.INSTANCE.getDeathban(uuid).getRemaining();
    }

    @Override
    public String getDeathban(UUID uuid) {
        if (Configuration.KITMAP_MODE)  {
            return "Loading";
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return "Loading";
        }
        if(deathban == null) {
            return "Loading";
        }
        if(!deathban.isActive()) {
            return "Loading";
        }
    }

    @Override
    public int getLives(UUID uuid) {

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return 0;
        }

        return LivesManager.INSTANCE.getLives(uuid);
    }
}
