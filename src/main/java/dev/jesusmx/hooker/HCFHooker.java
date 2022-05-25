package dev.jesusmx.hooker;

import dev.jesusmx.hooker.utils.ConfigFile;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

@Getter
public final class HCFHooker extends JavaPlugin {

    @Getter private static HCFHooker instance;
    private ConfigFile mainHubHooker, mainExternalHooker;

    @Override
    public void onEnable() {
        HCFHooker.instance = this;
        this.mainHubHooker = new ConfigFile(this, "Hub-Hooker");
        this.mainExternalHooker = new ConfigFile(this, "External-Hooker");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
