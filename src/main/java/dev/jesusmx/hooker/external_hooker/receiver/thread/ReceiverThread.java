package dev.jesusmx.hooker.external_hooker.receiver.thread;

import dev.jesusmx.hooker.external_hooker.Hooker;
import dev.jesusmx.hooker.hub_hooker.Splitters;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

public class ReceiverThread extends Thread {

    private final ServerSocket server;
    private final String server_name;

    public ReceiverThread(ServerSocket server, String server_name) {
        this.server = server;
        this.server_name = server_name;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket socket = server.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                String msg = dis.readUTF();

                if(msg.contains(Splitters.REQUEST + "ENABLED" + Splitters.REQUEST) && msg.contains(server_name)) {
                    dos.writeUTF("SUCCESSFULLY");
                    dos.flush();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully hooked with one hub!");
                } else if(msg.contains(server_name) && msg.contains(Splitters.REQUEST)) {
                    String[] entry = msg.split(Splitters.REQUEST);
                    UUID uuid = UUID.fromString(entry[1]);
                    String response;
                    boolean isDeathban = Hooker.INSTANCE.getCore().isDeathban(uuid);
                    int lives = Hooker.INSTANCE.getCore().getLives(uuid);
                    response = isDeathban + Splitters.REQUEST + lives + Splitters.REQUEST + Hooker.INSTANCE.getCore().getDeathban(uuid);
                    dos.writeUTF(response);
                    dos.flush();
                }
                dis.close();
                dos.close();
                socket.close();
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error receiving one socket!");
                if(Hooker.INSTANCE.getConfig().getBoolean("DEBUG")) e.printStackTrace();
            }
        }
    }
}
