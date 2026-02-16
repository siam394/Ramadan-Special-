package com.siam.ramadanplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class RamadanPlugin extends JavaPlugin {

    private ScoreboardManager scoreboardManager;
    private RamadanEvents ramadanEvents;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        scoreboardManager = new ScoreboardManager(this);
        ramadanEvents = new RamadanEvents(this);

        scoreboardManager.startScoreboard();
        ramadanEvents.startEventScheduler();

        getLogger().info("Ramadan Plugin Enabled");
    }

    @Override
    public void onDisable() {
        scoreboardManager.stopScoreboard();
        getLogger().info("Ramadan Plugin Disabled");
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public RamadanEvents getRamadanEvents() {
        return ramadanEvents;
    }
}
