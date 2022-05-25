package dev.jesusmx.hooker.external_hooker.receiver;

import dev.jesusmx.hooker.external_hooker.receiver.thread.ReceiverThread;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

@Getter
public class Receiver {
    private final int port;
    private final String server_name;
    @Getter(AccessLevel.NONE)
    private final ServerSocket server;

    public Receiver(int port, String server_name) throws IOException {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Started Hooker in the port " + port);
        this.port = port;
        this.server_name = server_name;
        this.server = new ServerSocket(port);
        new ReceiverThread(server, server_name).start();
    }
}
