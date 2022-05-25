package dev.jesusmx.hooker.external_hooker.receiver.thread;

import dev.jesusmx.hooker.external_hooker.Hooker;
import dev.jesusmx.hooker.hub_hooker.Splitters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Set;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

public class HookThread extends Thread {
    private final String server_name;
    private final Set<String> hubs;

    public HookThread(String server_name, Set<String> hubs) {
        super.setName("Hooker - Hook");
        this.server_name = server_name;
        this.hubs = hubs;
    }

    @Override
    public void run() {
        for(String hub : hubs) {
            String path = "HUBS." + hub;
            String host = Hooker.INSTANCE.getConfig().getString(path + ".HOST");
            int hPort = Hooker.INSTANCE.getConfig().getInt(path + ".PORT");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Trying to connect to the hub: " + host + ":" + hPort);
            try {
                Socket socket = new Socket(host, hPort);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("hook" + Splitters.HOOK + server_name);
                dos.flush();
                if (!dis.readUTF().equalsIgnoreCase("hook" + Splitters.HOOK + "successfully")) throw new IOException("No reception in the hub!");
                dis.close();
                dos.close();
                socket.close();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully hooked with the hub " + hub);
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error connecting with the hub " + hub + "!");
                if(Hooker.INSTANCE.getConfig().getBoolean("DEBUG")) e.printStackTrace();
            }
        }
    }
}
