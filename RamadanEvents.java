package com.siam.ramadanplugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;

public class RamadanEvents {

    private final JavaPlugin plugin;
    private Economy economy;

    public RamadanEvents(JavaPlugin plugin) {
        this.plugin = plugin;
        if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
            economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        }
    }

    public void startEventScheduler() {
        new BukkitRunnable() {
            @Override
            public void run() {
                LocalTime now = LocalTime.now();
                String[] sehri = plugin.getConfig().getString("sehri_time").split(":");
                String[] iftar = plugin.getConfig().getString("iftar_time").split(":");

                if(now.getHour() == Integer.parseInt(sehri[0]) && now.getMinute() == Integer.parseInt(sehri[1])) {
                    triggerSehri();
                }

                if(now.getHour() == Integer.parseInt(iftar[0]) && now.getMinute() == Integer.parseInt(iftar[1])) {
                    triggerIftar();
                }
            }
        }.runTaskTimer(plugin, 0L, 1200L); // Check every minute
    }

    private void triggerSehri() {
        Bukkit.broadcastMessage("§6It's §cSehri §6time! Enjoy free food!");
        giveRewards("free_food_sehri");
    }

    private void triggerIftar() {
        Bukkit.broadcastMessage("§6It's §bIftar §6time! Enjoy free food!");
        giveRewards("free_food_iftar");
    }

    private void giveRewards(String configSection) {
        Material item = Material.valueOf(plugin.getConfig().getString(configSection + ".item"));
        int amount = plugin.getConfig().getInt(configSection + ".amount");
        int money = plugin.getConfig().getInt("money_reward");

        for(Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().addItem(new ItemStack(item, amount));
            if(economy != null) economy.depositPlayer(player, money);
        }
    }
}
