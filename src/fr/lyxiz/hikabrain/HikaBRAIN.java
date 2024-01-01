package fr.lyxiz.hikabrain;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import fr.lyxiz.hikabrain.function.Round;
import fr.lyxiz.hikabrain.listeners.ListenerManager;
import fr.lyxiz.hikabrain.scoreboard.ScoreboardManager;
import fr.lyxiz.hikabrain.utils.Cuboid;
import fr.lyxiz.hikabrain.utils.TeamsUtils;
import fr.lyxiz.hikabrain.utils.WorldManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;


public class HikaBRAIN extends JavaPlugin implements PluginMessageListener {

    private static HikaBRAIN instance;

    public ArrayList<Location> ModifiedBlocks = new ArrayList<>();

    public static int timer = 0;

    public Round round;


    public Cuboid cuboid;


    public FileConfiguration config;

    public TeamsUtils blue;

    public TeamsUtils red;

    public int redPoints = 0;

    public int bluePoints = 0;

    public int maxbuild = 256;

    public Location redSpawn;

    public Location blueSpawn;

    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    public Location center;

    public Location c1;

    public Location c2;

    public int WinPoints;

    public String Version = Bukkit.getServer().getBukkitVersion();

    public String endCommand;


    public String sbtitle;

    public String sbIp;

    public boolean isLaunch = false;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        String[] var = this.Version.split("-");
        this.Version = var[0];
        this.config = getConfig();
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            getLogger().info("Config.yml");
            this.config.addDefault("points", Integer.valueOf(10));
            this.config.addDefault("scoreboard.title", "&6§lHikaBrain");
            this.config.addDefault("scoreboard.footer", "&6play.lyxiz.fr");
            this.config.addDefault("finish.command", "Commande sans / | Command without /");
            this.config.addDefault("load.center.world", "world");
            this.config.addDefault("load.center.x", Integer.valueOf(0));
            this.config.addDefault("load.center.y", Integer.valueOf(0));
            this.config.addDefault("load.center.z", Integer.valueOf(0));
            this.config.options().copyDefaults(true);
            Bukkit.getConsoleSender().sendMessage("le fichier de configuration à bien été créer !");
            saveDefaultConfig();
            saveConfig();
        }
        (new ListenerManager()).registers();
        this.round = new Round();
        this.round.setSpawns();
        Bukkit.getWorld("world").setGameRuleValue("keepInventory", "true");
        if (this.c1 != null && this.c2 != null) {
            this.cuboid = new Cuboid(this.c1, this.c2);
        } else {
            Bukkit.getConsoleSender()
                    .sendMessage("§c[Alert] La zone de réinitialisation a été mal définie !");
        }
        this.endCommand = this.config.getString("finish.command");
        this.WinPoints = this.config.getInt("points");
        this.sbtitle = this.config.getString("scoreboard.title");
        this.sbIp = this.config.getString("scoreboard.footer");
        blue = new TeamsUtils("blue", 1, "§b", true, "§b", true);
        red = new TeamsUtils("red", 1, "§c", true, "§c", true);
        this.red.build();
        this.blue.build();

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager(this);
    }

    public void onDisable() {
        this.red.team.unregister();
        this.blue.team.unregister();
        WorldManager.replaceWorld(true);
    }

    public static HikaBRAIN getInstance() {
        return instance;
    }


    public void cloneWorld(String sourceWorldName, String destinationWorldName) {
        // Unload the destination world if it's loaded
        World destinationWorld = Bukkit.getWorld(destinationWorldName);
        if (destinationWorld != null) {
            Bukkit.unloadWorld(destinationWorld, false);
        }

        // Copy world data from the source to the destination
        File sourceWorldFolder = new File(Bukkit.getWorldContainer(), sourceWorldName);
        File destinationWorldFolder = new File(Bukkit.getWorldContainer(), destinationWorldName);

        try {
            FileUtils.copyDirectory(sourceWorldFolder, destinationWorldFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load the destination world
        WorldCreator worldCreator = new WorldCreator(destinationWorldName);
        Bukkit.createWorld(worldCreator);
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

    }

    public static void deleteWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        File file = new File(worldName);
        if ((file == null) || (world == null)) {
            return;
        }
        Bukkit.unloadWorld(world, false);

        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

}


