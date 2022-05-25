package dev.jesusmx.hooker.hub_hooker;

import dev.jesusmx.hooker.HCFHooker;
import dev.jesusmx.hooker.utils.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

public class HookReceiverThread extends Thread {

    public HookReceiverThread() {
        super.setName("Hooker - HooksReceiver");
    }
    private ConfigFile config = HCFHooker.getInstance().getMainHubHooker();

    @Override
    public void run() {
        while(true) {
            try {
                Socket socket = Hooker.server.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                String msg = dis.readUTF();
                if(msg.contains("hook") && msg.contains(Splitters.HOOK)) {
                    String[] entry = msg.split(Splitters.HOOK);
                    String server_name = entry[1];
                    dos.writeUTF("hook" + Splitters.HOOK +"successfully");
                    dos.flush();
                    if(!Hooker.getVerified().contains(server_name) && Hooker.getUnverified().contains(server_name)) {
                        Hooker.getUnverified().remove(server_name);
                        Hooker.getVerified().add(server_name);
                    }
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully hooked with " + server_name);
                }
                dis.close();
                dos.close();
                socket.close();
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error Receiving a hooker request!");
                if(config.getBoolean("hcf-hook.debug-mode")) e.printStackTrace();
            }
        }
    }
}
