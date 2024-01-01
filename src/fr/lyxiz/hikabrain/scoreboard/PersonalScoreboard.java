package fr.lyxiz.hikabrain.scoreboard;

import fr.lyxiz.hikabrain.HikaBRAIN;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PersonalScoreboard {

    private Player player;
    private static ObjectiveSign objectiveSign;

    PersonalScoreboard(Player player) {
        this.player = player;
        objectiveSign = new ObjectiveSign("sidebar", "§6§lLyxiz");
        reloadData();
        objectiveSign.addReceiver(player);

    }

    public void reloadData() {
    }

    public void setLines(String ip) {


        objectiveSign.setDisplayName("§6§lHikaBrain");
        objectiveSign.setLine(0, "§e");
        objectiveSign.setLine(1, "§8┃ §cRouge: §c" + HikaBRAIN.getInstance().redPoints + "§c/" + HikaBRAIN.getInstance().WinPoints);
        objectiveSign.setLine(2, "§8┃ §bBleu: §b" + HikaBRAIN.getInstance().bluePoints + "§b/" + HikaBRAIN.getInstance().WinPoints);
        objectiveSign.setLine(3, "§a");
        objectiveSign.setLine(4, "§8┃ §7Timer: §f00:00");
        objectiveSign.setLine(5, "§8┃ §7Carte: §aNormal");
        objectiveSign.setLine(6, "§6");
        objectiveSign.setLine(7, ip);

        objectiveSign.update();
        objectiveSign.updateLines();
    }

    public static void updateRed(Player player) {
        objectiveSign.setLine(2, "§8┃ §cRouge: §c" + HikaBRAIN.getInstance().redPoints + "§c/" + HikaBRAIN.getInstance().WinPoints);
        objectiveSign.update();
        objectiveSign.updateLines();

    }

    public static void updateBlue(Player player) {
        objectiveSign.setLine(3, "§8┃ §bBleu: §b" + HikaBRAIN.getInstance().bluePoints + "§b/" + HikaBRAIN.getInstance().WinPoints);
        objectiveSign.update();
        objectiveSign.updateLines();

    }


    public void onLogout() {
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(player.getUniqueId()));
    }


}
