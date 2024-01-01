package fr.lyxiz.hikabrain.listeners.player;
import fr.lyxiz.hikabrain.HikaBRAIN;
import fr.lyxiz.hikabrain.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerJoin implements Listener {

    int task;
    int timer = 6;
    boolean started = false;


    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        Title.sendTitle(player, 20, 40, 20, "§6§lHikaBrain", "§fBienvenue dans la partie !");
        event.setJoinMessage("§6§lHIKABRAIN §f┃ §6" + player.getDisplayName() + " §7a rejoint la partie !");
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));

        HikaBRAIN.getInstance().getScoreboardManager().onLogin(player);

        if(Bukkit.getOnlinePlayers().size() == 1){
            HikaBRAIN.getInstance().blue.addPlayer(player);
        }else if(Bukkit.getOnlinePlayers().size() == 2){
            HikaBRAIN.getInstance().red.addPlayer(player);
        }else{
            player.sendMessage("§6§lHIKABRAIN §f┃ §cLa partie vient de commencer donc vous avez été mit en spectateur.");
            player.setGameMode(GameMode.SPECTATOR);
        }

        if (Bukkit.getOnlinePlayers().size() == 2) {
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(HikaBRAIN.getInstance(), new Runnable() {

                @Override
                public void run() {

                    started = true;
                    timer--;
                    Bukkit.broadcastMessage("§6§lHIKABRAIN §f┃ §aLa partie commence dans " + timer + "s");
                    if (timer == 0) {
                        Bukkit.getScheduler().cancelTask(task);
                        HikaBRAIN.getInstance().isLaunch = true;
                        HikaBRAIN.getInstance().round.start();

                    }

                }
            }, 20, 20);
        }


    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        if(Bukkit.getOnlinePlayers().size() < 2){
            Bukkit.broadcastMessage("§6§lHIKABRAIN §f┃ §6Lancement annulé !");
            Bukkit.getScheduler().cancelTask(task);
            timer = 6;
            started = false;
            if(HikaBRAIN.getInstance().blue.contain(event.getPlayer())){
                HikaBRAIN.getInstance().blue.removePlayer(event.getPlayer());
            }
            if(HikaBRAIN.getInstance().red.contain(event.getPlayer())){
                HikaBRAIN.getInstance().red.removePlayer(event.getPlayer());
            }
            HikaBRAIN.getInstance().getScoreboardManager().onLogout(event.getPlayer());
        }

    }

}
