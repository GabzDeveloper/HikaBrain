package fr.lyxiz.hikabrain.listeners;

import fr.lyxiz.hikabrain.HikaBRAIN;
import fr.lyxiz.hikabrain.listeners.player.*;
import fr.lyxiz.hikabrain.listeners.server.RainManager;
import fr.lyxiz.hikabrain.listeners.world.BlockBreak;
import fr.lyxiz.hikabrain.listeners.world.BlockPlace;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;


public class ListenerManager {


    private HikaBRAIN instance = HikaBRAIN.getInstance();

    public void registers() {

        PluginManager plugin = Bukkit.getPluginManager();

        plugin.registerEvents(new BlockPlace(), instance);
        plugin.registerEvents(new BlockBreak(), instance);
        plugin.registerEvents(new PlayerMove(), instance);
        plugin.registerEvents(new PlayerJoin(), instance);
        plugin.registerEvents(new PlayerDamage(), instance);
        plugin.registerEvents(new PlayerDeath(), instance);
        plugin.registerEvents(new RainManager(), instance);

    }

}
