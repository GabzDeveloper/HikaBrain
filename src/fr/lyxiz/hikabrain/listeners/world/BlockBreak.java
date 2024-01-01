package fr.lyxiz.hikabrain.listeners.world;

import fr.lyxiz.hikabrain.HikaBRAIN;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class BlockBreak implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){

        if(!HikaBRAIN.getInstance().isLaunch){
            event.setCancelled(true);
            return;
        }

        if(event.getBlock().getType() == Material.OBSIDIAN || event.getBlock().getType() == Material.STAINED_CLAY || event.getBlock().getType() == Material.STAINED_GLASS){
            event.setCancelled(true);
            return;
        }

        event.getBlock().getDrops().clear();

        HikaBRAIN.getInstance().ModifiedBlocks.add(event.getBlock().getLocation());

    }
}
