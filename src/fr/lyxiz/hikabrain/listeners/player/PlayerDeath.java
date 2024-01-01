package fr.lyxiz.hikabrain.listeners.player;

import fr.lyxiz.hikabrain.HikaBRAIN;
import fr.lyxiz.hikabrain.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.spigot().respawn();
        event.setDeathMessage(null);
        event.getDrops().clear();
        Player killer = (Player) player.getLastDamageCause().getEntity();
        HikaBRAIN.getInstance().round.setPartyInventory(player);

        if (player.getLastDamageCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                Title.sendActionBar(players, "§6§l» §c" + player.getName() + " §fest mort par §c" + killer.getName() + " §6§l«");
            }
            player.spigot().respawn();
            event.setDeathMessage(null);
            event.getDrops().clear();
            HikaBRAIN.getInstance().round.setPartyInventory(player);


        } else if (player.getLastDamageCause().equals(EntityDamageEvent.DamageCause.SUICIDE)) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                Title.sendActionBar(players, "§6§l» §c" + player.getName() + " §fest mort. §6§l«");
            }
            player.spigot().respawn();
            event.setDeathMessage(null);
            event.getDrops().clear();
            HikaBRAIN.getInstance().round.setPartyInventory(player);
        }
    }
}
