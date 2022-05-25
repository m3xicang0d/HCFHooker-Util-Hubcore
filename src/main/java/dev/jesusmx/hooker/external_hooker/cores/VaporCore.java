package dev.jesusmx.hooker.external_hooker.cores;

import dev.astro.net.Vapor;
import dev.astro.net.deathban.Deathban;
import dev.astro.net.utils.DurationFormatter;
import dev.jesusmx.hooker.external_hooker.interfaces.HCFCore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class VaporCore implements HCFCore {

    @Override
    public boolean isDeathban(UUID uuid) {
        if (Vapor.get().getConfiguration().isKitMap()) {
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return false;
        }
        return Vapor.get().getProfileManager().getByUuid(uuid).getDeathban().isActive();

    }

    @Override
    public String getDeathban(UUID uuid) {
        if (Vapor.get().getConfiguration().isKitMap()) {
            return "Loading";
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return "Loading";
        }
        Deathban deathban = Vapor.get().getProfileManager().getByUuid(uuid).getDeathban();
        if(deathban == null) {
            return "Loading";
        }
        if(!deathban.isActive()) {
            return "Loading";
        }


        return DurationFormatter.getRemaining(uuid).get
        deathban.getRemaining()
        return null;
    }

    @Override
    public int getLives(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return 0;
        }
        return Vapor.get().getProfileManager().getByUuid(uuid).getLives();
    }
}
