package fr.lyxiz.hikabrain.listeners.player;

import fr.lyxiz.hikabrain.HikaBRAIN;
import fr.lyxiz.hikabrain.scoreboard.PersonalScoreboard;
import fr.lyxiz.hikabrain.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (HikaBRAIN.getInstance().isLaunch) {

            Location location = new Location(player.getWorld(), player.getLocation().getX(),
                    player.getLocation().getY() - 1, player.getLocation().getZ());
            if (location.getY() < HikaBRAIN.getInstance().maxbuild - 1) {
                if (HikaBRAIN.getInstance().blue.contain(player)) {
                    if (location.getBlock().getType() == Material.STAINED_CLAY) {
                        if (location.getBlock().getData() == 14) {
                            HikaBRAIN.getInstance().round.winPoint(player);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                Title.sendActionBar(players, "§b" + player.getName() + " §fvient de marquer un point !");
                            }
                        }
                    }
                } else if (HikaBRAIN.getInstance().red.contain(player)) {
                    if (location.getBlock().getType() == Material.STAINED_CLAY) {
                        if (location.getBlock().getData() == 11) {
                            HikaBRAIN.getInstance().round.winPoint(player);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                Title.sendActionBar(players, "§c" + player.getName() + " §fvient de marquer un point !");
                            }
                        }
                    }
                }
            }
        }
        if (player.getLocation().getY() < 10) {
            if (HikaBRAIN.getInstance().isLaunch) {
                if (HikaBRAIN.getInstance().red.contain(player)) {
                    player.teleport(HikaBRAIN.getInstance().redSpawn);
                    HikaBRAIN.getInstance().round.setPartyInventory(player);
                }
                if (HikaBRAIN.getInstance().blue.contain(player)) {
                    player.teleport(HikaBRAIN.getInstance().blueSpawn);
                    HikaBRAIN.getInstance().round.setPartyInventory(player);
                }
            } else {
                player.teleport(HikaBRAIN.getInstance().center);
            }

        }

    }

}
