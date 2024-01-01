package fr.lyxiz.hikabrain.listeners.server;

import fr.lyxiz.hikabrain.HikaBRAIN;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class RainManager implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onWeatherChange(WeatherChangeEvent e) {
        final World w = e.getWorld();
        if (!w.hasStorm()) {
            e.setCancelled(true);
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HikaBRAIN.getInstance(), new Runnable() {
            public void run() {
                try {
                    if (w.hasStorm()) {
                        w.setStorm(false);
                        rainCheck();
                    }
                } catch (Exception localException) {
                }
            }
        }, 5L);
    }

    private void rainCheck() {
        for (World w : HikaBRAIN.getInstance().getServer().getWorlds()) {
            if (w.hasStorm()) {
                w.setStorm(false);
            }
        }
    }

}
