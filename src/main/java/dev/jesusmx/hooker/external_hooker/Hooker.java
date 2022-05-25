package dev.jesusmx.hooker.external_hooker;

import dev.jesusmx.hooker.external_hooker.cores.LazarusCore;
import dev.jesusmx.hooker.external_hooker.interfaces.HCFCore;
import dev.jesusmx.hooker.external_hooker.receiver.Receiver;
import dev.jesusmx.hooker.external_hooker.receiver.thread.HookThread;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

@Getter
public class Hooker extends JavaPlugin {

    public static Hooker INSTANCE;
    private HCFCore core;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Starting in 5s");
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
            INSTANCE = Hooker.getPlugin(Hooker.class);
            if(!new File(getDataFolder(), "config.yml").exists()) {getConfig().options().copyDefaults(true);saveConfig();}
            setupCore();
            try {
                String server_name = getConfig().getString("SERVER-NAME");
                int port = getConfig().getInt("API-PORT");
                new HookThread(server_name, getConfig().getConfigurationSection("HUBS").getKeys(false)).start();
                new Receiver(port, server_name);
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error initializing the hook API");
                if(Hooker.INSTANCE.getConfig().getBoolean("DEBUG")) e.printStackTrace();
                Bukkit.getPluginManager().disablePlugin(this);
            }
                }, 100L);
    }

    private void setupCore() {
        if(valid("Lazarus")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Detected core " + ChatColor.YELLOW + plugin("Lazarus"));
            core = new LazarusCore();
        } else if(valid("Void")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Detected core " + ChatColor.YELLOW + plugin("Void"));
        }
    }

    private String plugin(String plugin) {
        PluginDescriptionFile desc = Bukkit.getPluginManager().getPlugin(plugin).getDescription();
        return desc.getName() + " version " + desc.getVersion();
    }

    private boolean valid(String plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }
}
