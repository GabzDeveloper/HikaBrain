package fr.lyxiz.hikabrain.listeners.player;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if (event.getCause() == DamageCause.FALL) {
            event.setCancelled(true);
        }

    }
}
