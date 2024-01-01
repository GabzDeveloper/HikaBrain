package fr.lyxiz.hikabrain.listeners.world;

import fr.lyxiz.hikabrain.HikaBRAIN;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event){

        if(!HikaBRAIN.getInstance().isLaunch){
            event.setCancelled(true);
            return;
        }
        if(event.getBlock().getLocation().getY() >= HikaBRAIN.getInstance().maxbuild){
            event.setCancelled(true);
            event.getPlayer().sendMessage("§6§lHIKABRAIN §f┃ §cVous ne pouvez pas poser plus haut !");
            return;
        }


        HikaBRAIN.getInstance().ModifiedBlocks.add(event.getBlock().getLocation());


    }
}
