package com.siam.ramadanplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreboardManager {

    private final JavaPlugin plugin;
    private BukkitRunnable task;
    private int frame = 0;

    public ScoreboardManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startScoreboard() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                String date = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                String[] frames = plugin.getConfig().getStringList("scoreboard_frames").toArray(new String[0]);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
                    Objective obj = sb.registerNewObjective("RamadanSB", "dummy", frames[frame]);
                    obj.setDisplaySlot(DisplaySlot.SIDEBAR);

                    obj.getScore("§7Date: §e" + date).setScore(3);
                    obj.getScore("§7Time: §e" + time).setScore(2);
                    obj.getScore("§aHave a blessed Ramadan!").setScore(1);

                    player.setScoreboard(sb);
                }

                frame = (frame + 1) % frames.length;
            }
        };
        task.runTaskTimer(plugin, 0L, 20L); // Update every second
    }

    public void stopScoreboard() {
        if(task != null) task.cancel();
    }
}
