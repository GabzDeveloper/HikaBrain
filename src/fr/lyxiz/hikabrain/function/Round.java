package fr.lyxiz.hikabrain.function;

import fr.lyxiz.hikabrain.HikaBRAIN;
import fr.lyxiz.hikabrain.utils.BlockRadius;
import fr.lyxiz.hikabrain.utils.BungeeUtils;
import fr.lyxiz.hikabrain.utils.Title;
import net.minecraft.server.v1_8_R3.Enchantment;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Round {

    @SuppressWarnings("deprecation")
    public void setSpawns() {
        Location center = new Location(Bukkit.getWorld(HikaBRAIN.getInstance().getConfig().getString("load.center.world")),
                HikaBRAIN.getInstance().getConfig().getInt("load.center.x"),
                HikaBRAIN.getInstance().getConfig().getInt("load.center.y"),
                HikaBRAIN.getInstance().getConfig().getInt("load.center.z"));
        int amount = 0;
        int slime = 0;
        int iron = 0;

        for (Block blocks : new BlockRadius().sphereblock(center, 30)) {
            if (blocks.getType() == Material.BEACON) {
                Location under = new Location(blocks.getWorld(), blocks.getLocation().getX(),
                        blocks.getLocation().getY() - 1,
                        blocks.getLocation().getZ());
                if (under.getBlock().getType() == Material.STAINED_CLAY) {
                    if (under.getBlock().getData() == 3) {
                        HikaBRAIN.getInstance().blueSpawn = blocks.getLocation();
                        blocks.setType(Material.AIR);
                        amount++;
                    }
                    if (under.getBlock().getData() == 14) {
                        HikaBRAIN.getInstance().redSpawn = blocks.getLocation();
                        blocks.setType(Material.AIR);
                        amount++;
                        HikaBRAIN.getInstance().maxbuild = (int) under.getY() + 1;
                    }

                }
            }
            if (blocks.getType() == Material.SLIME_BLOCK) {
                slime++;
                if (slime == 1) {
                    HikaBRAIN.getInstance().c1 = blocks.getLocation();
                    blocks.setType(Material.OBSIDIAN);
                }
                if (slime == 2) {
                    HikaBRAIN.getInstance().c2 = blocks.getLocation();
                    blocks.setType(Material.OBSIDIAN);
                }

            }
            if (blocks.getType() == Material.IRON_BLOCK) {
                HikaBRAIN.getInstance().center = blocks.getLocation();
                blocks.setType(Material.SANDSTONE);

                iron++;
            }
        }

        if (amount < 2) {
            Bukkit.getConsoleSender().sendMessage("§c[Alert] §aVous avez oublié de définir au moins 1 des 2 spawns");
        }
        if (amount > 2) {
            Bukkit.getConsoleSender().sendMessage("§c[Alert] §aVous avez définies trop de spawns §c(Max 2)");
        }
        if (slime < 2) {
            Bukkit.getConsoleSender().sendMessage("§c[Alert] §aVous n'avez pas définie la zone du pont (Slime)");
        }
        if (slime > 2) {
            Bukkit.getConsoleSender().sendMessage("§c[Alert] §aNous avons trouver " + (slime - 2) + " blocks de slime en trop !");
        }
        if (iron > 1) {
            Bukkit.getConsoleSender().sendMessage("§c[Alert] §aVous pouvez définir un seul Millieu (Block en Fer)");
        }

    }

    private void clear() {

        for (Location blocks : HikaBRAIN.getInstance().ModifiedBlocks) {
            if (!HikaBRAIN.getInstance().cuboid.contains(blocks)) {
                blocks.getBlock().setType(Material.AIR);
            }
            if (HikaBRAIN.getInstance().cuboid.contains(blocks)) {
                if (blocks.getBlock().getType() == Material.AIR) {
                    blocks.getBlock().setType(Material.SANDSTONE);
                }
            }
        }

    }

    private ItemStack item(Material material, org.bukkit.enchantments.Enchantment enchantement) {

        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.addEnchant(enchantement, 3, true);
        itemmeta.spigot().setUnbreakable(true);
        itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemmeta);

        return item;
    }

    private ItemStack item(Material material, org.bukkit.enchantments.Enchantment enchantement, org.bukkit.enchantments.Enchantment ench2) {

        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.addEnchant(enchantement, 2, true);
        itemmeta.addEnchant(ench2, 1, true);
        itemmeta.spigot().setUnbreakable(true);
        itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemmeta);

        return item;
    }

    private ItemStack armor(Material material, Color color, int strenght) {

        ItemStack itemstack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) itemstack.getItemMeta();
        meta.setColor(color);
        meta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, strenght, true);
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemstack.setItemMeta(meta);
        return itemstack;

    }

    public void setPartyInventory(Player player) {
        player.getInventory().setItem(0, new ItemStack(Material.SANDSTONE, 64, (short) 2));

        if (player.getGameMode() != GameMode.SPECTATOR) {
            player.getInventory().clear();
            player.setHealth(20.0D);
            player.getInventory()
                    .addItem(new ItemStack[]{item(Material.IRON_SWORD, org.bukkit.enchantments.Enchantment.DAMAGE_ALL, org.bukkit.enchantments.Enchantment.KNOCKBACK)});
            player.getInventory().addItem(new ItemStack[]{item(Material.DIAMOND_PICKAXE, org.bukkit.enchantments.Enchantment.DIG_SPEED)});
            player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.GOLDEN_APPLE, 32)});
            while (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.SANDSTONE, 64, (short) 2)});
            }
        }
        if ((HikaBRAIN.getInstance()).red.contain(player)) {
            player.getInventory().setHelmet(armor(Material.LEATHER_HELMET, Color.RED, 1));
            player.getInventory().setChestplate(armor(Material.LEATHER_CHESTPLATE, Color.RED, 3));
            player.getInventory().setLeggings(armor(Material.LEATHER_LEGGINGS, Color.RED, 1));
            player.getInventory().setBoots(armor(Material.LEATHER_BOOTS, Color.RED, 1));
        }
        if ((HikaBRAIN.getInstance()).blue.contain(player)) {
            player.getInventory().setHelmet(armor(Material.LEATHER_HELMET, Color.BLUE, 1));
            player.getInventory().setChestplate(armor(Material.LEATHER_CHESTPLATE, Color.BLUE, 3));
            player.getInventory().setLeggings(armor(Material.LEATHER_LEGGINGS, Color.BLUE, 1));
            player.getInventory().setBoots(armor(Material.LEATHER_BOOTS, Color.BLUE, 1));
        }
        player.getEyeLocation().setDirection(new Vector((HikaBRAIN.getInstance()).center.getX(), (HikaBRAIN.getInstance()).center.getY(), (HikaBRAIN.getInstance()).center.getZ()));
    }

    private void hasWon() {
        if ((HikaBRAIN.getInstance()).bluePoints == (HikaBRAIN.getInstance()).WinPoints) {
            Bukkit.broadcastMessage("§6§lHIKABRAIN §f┃ §fL'équipe §9Bleu §fa de gagner la partie !");
            (HikaBRAIN.getInstance()).isLaunch = false;
            for (Player player : Bukkit.getOnlinePlayers()) {
                Title.sendTitle(player, 20, 40, 20, "§cRedémarrage", "§cRetour au Lobby dans 5 secondes !");
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        BungeeUtils.sendServer(players, "lobby");
                        Bukkit.shutdown();
                    }
                }
            }.runTaskLater(HikaBRAIN.getInstance(), 7 * 20);


        } else if ((HikaBRAIN.getInstance()).redPoints == (HikaBRAIN.getInstance()).WinPoints) {
            Bukkit.broadcastMessage("§6§lHIKABRAIN §f┃ §fL'équipe §cRouge §fa de gagner la partie !");
            (HikaBRAIN.getInstance()).isLaunch = false;
            for (Player player : Bukkit.getOnlinePlayers()) {
                Title.sendTitle(player, 20, 40, 20, "§cRedémarrage", "§cRetour au Lobby dans 5 secondes !");
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        BungeeUtils.sendServer(players, "lobby");
                        Bukkit.shutdown();
                    }
                }
            }.runTaskLater(HikaBRAIN.getInstance(), 7 * 20);
        } else {
            for (Player players : Bukkit.getOnlinePlayers()) {
                sendAtSpawn(players);
                setPartyInventory(players);
            }
        }
    }

    public void sendAtSpawn(Player players) {
        if ((HikaBRAIN.getInstance()).red.contain(players))
            players.teleport((HikaBRAIN.getInstance()).redSpawn);
        if ((HikaBRAIN.getInstance()).blue.contain(players))
            players.teleport((HikaBRAIN.getInstance()).blueSpawn);
    }

    public void start() {
        Bukkit.broadcastMessage("§f§l» §6§lBONNE CHANCE !");
        for (Player players : Bukkit.getOnlinePlayers()) {
            sendAtSpawn(players);
            setPartyInventory(players);
            players.setGameMode(GameMode.SURVIVAL);
        }
    }

    public void winPoint(Player marker) {
        if ((HikaBRAIN.getInstance()).blue.contain(marker)) {
            (HikaBRAIN.getInstance()).bluePoints++;
            Bukkit.broadcastMessage("§6§lHIKABRAIN §f┃ §9" + marker.getName() + " §fvient de marquer un point !");
        }
        if ((HikaBRAIN.getInstance()).red.contain(marker)) {
            (HikaBRAIN.getInstance()).redPoints++;
            Bukkit.broadcastMessage("§6§lHIKABRAIN §f┃ §c" + marker.getName() + " §fvient de marquer un point !");
        }
        clear();
        hasWon();
    }
}
