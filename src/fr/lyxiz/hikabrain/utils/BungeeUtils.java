package fr.lyxiz.hikabrain.utils;

import fr.lyxiz.hikabrain.HikaBRAIN;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeUtils {

    public static void sendServer(Player player, String server){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(HikaBRAIN.getInstance(), "BungeeCord", byteArrayOutputStream.toByteArray());
    }
}
