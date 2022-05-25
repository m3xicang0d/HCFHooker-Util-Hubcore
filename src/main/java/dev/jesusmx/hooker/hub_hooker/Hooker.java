package dev.jesusmx.hooker.hub_hooker;

import dev.jesusmx.hooker.HCFHooker;
import dev.jesusmx.hooker.utils.CC;
import dev.jesusmx.hooker.utils.ConfigFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

public class Hooker {

    @Getter private static final Set<String> unverified = new HashSet<>();
    @Getter private static final Set<String> verified = new HashSet<>();
    protected static ServerSocket server;
    protected static int port;
    private ConfigFile config = HCFHooker.getInstance().getMainHubHooker();

    public Hooker() {
        try {
            unverified.addAll(config.getConfiguration().getConfigurationSection("hcf-hook.servers").getKeys(false));
            port = config.getInt("hcf-hook.hub-port");
            server = new ServerSocket(port);
            Bukkit.getScheduler().runTaskLaterAsynchronously(HCFHooker.getInstance(), () -> Bukkit.getConsoleSender().sendMessage(CC.translate(" &7• &fHCF-Hooks: &bChecking " + unverified.size() + " hooks")), 1L);
            List<String> toRemove = new ArrayList<>();
            List<String> toAdd = new ArrayList<>();
            for(String s : Hooker.getUnverified()) {
                String path = "hcf-hook.servers." + s;
                String host =  config.getString(path + ".host");
                int port = config.getInt(path + ".port");
                try {
                    Socket socket = new Socket(host, port);
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(Splitters.REQUEST + "ENABLED" + Splitters.REQUEST + s);
                    dos.flush();
                    String msg = dis.readUTF();
                    if(msg.contains("SUCCESSFULLY")) {
                        toAdd.add(s);
                        toRemove.add(s);
                    }
                    dis.close();
                    dos.close();
                    socket.close();

                } catch (IOException ignored) {}
            }
            toRemove.forEach(Hooker.getUnverified()::remove);
            Hooker.getVerified().addAll(toAdd);
            new HookReceiverThread().start();
            Bukkit.getScheduler().runTaskLaterAsynchronously(HCFHooker.getInstance(), () -> Bukkit.getConsoleSender().sendMessage(CC.translate(" &7• &fHCF-Hooks: &aVerified " + verified.size() + " hooks, You have " + ChatColor.GREEN + unverified.size() + " &aunverified hooks!")), 2L);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(" &7• &fHCF-Hooks: &cError initializing the hooker");
            if(config.getBoolean("hcf-hook.debug-mode")) e.printStackTrace();
        }
    }
}
