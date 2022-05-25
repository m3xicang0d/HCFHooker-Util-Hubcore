# HCFHooker
This utility was created for "TacoHub" buyers to have an exact replica of ViperMC

The useful one was made with "SOCKET", a solution that we found very convenient for those people who do not usually use MongoDB or Redis.

**The code is a bit unstable, but it can help you!**

**This code was done in 8 hours (overnight) so if you find a better way to optimize it, DO IT and don't bother**

# Support
You can add support with any HCF-Core, you only need to add the API or in any other case the .JAR of it.


## Implementation in Tablist
The TabAPI where it is implemented is: https://github.com/NoSequel/TabAPI
```java
    @Override
    public TabElement getElement(Player player) {
        TabElement element = new TabElement();
        element.setHeader(CC.translate(PlaceholderAPI.setPlaceholders(player, config.getString("tablist.header").replace("<line>", "\n"))));
        element.setFooter(CC.translate(PlaceholderAPI.setPlaceholders(player, config.getString("tablist.footer").replace("<line>", "\n"))));

        List<String> list = Arrays.asList("left", "middle", "right", "far-right");

        for (int i = 0; i < 4; ++i) {
            String s = list.get(i);
            for (int l = 0; l < 20; ++l) {

                String str = PlaceholderAPI.setPlaceholders(player, config.getString("tablist." + s + "." + (l + 1))
                        .replace("%player%", player.getDisplayName())
                        .replace("%rank%", SharkHub.getInstance().getPermissionCore().getRank(player)))
                        .replace("%players%", String.valueOf(BungeeUtils.getGlobalPlayers()))
                        .replace("%rank-color%", SharkHub.getInstance().getPermissionCore().getRankColor(player));
                
                if(settings.getBoolean("system.hcf-hook")) {
                    if(!Hooker.getVerified().isEmpty()) {
                        for (String sk : Hooker.getVerified()) {
                            String path = "hcf-hook.servers." + sk;
                            String host = hcf.getString(path + ".host");
                            int port = hcf.getInt(path + ".port");
                            try {
                                Socket socket = new Socket(host, port);
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                DataInputStream dis = new DataInputStream(socket.getInputStream());
                                dos.writeUTF(sk + Splitters.REQUEST + player.getUniqueId());
                                dos.flush();
                                String response = dis.readUTF();
                                String[] rs = response.split(Splitters.REQUEST);
                                str = str.replace("%" + sk + "_lives%", rs[1]);
                                str = str.replace("%" + sk + "_deathban%", rs[2]);
                                dos.close();
                                dis.close();
                                socket.close();
                            } catch (IOException e) {
                                str = str.replace("%" + sk + "_lives%", "0");
                                str = str.replace("%" + sk + "_deathban%", "Loading");
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "The connection with the hook " + sk + " has been lost");
                                Hooker.getVerified().remove(sk);
                                Hooker.getUnverified().add(sk);
                            }
                        }
                    }
                    for(String sk : Hooker.getUnverified()) {
                        str = str.replace("%" + sk + "_LIVES%", "0");
                        str = str.replace("%" + sk + "_DEATHBAN%", "Loading");
                    }
                }
                
                element.add(i, l, str, 0);
            }
        }
        return element;
    }
```


# Credits
JesusMX#2122

FxMxGRAGFX#0025


# Selling & Using
You're free to use this product for anything, including selling and running on your own server. However, if you're going to sell a plugin using this, please leave credits and/or a link to the repository.
