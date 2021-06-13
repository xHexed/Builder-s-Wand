package de.False.BuildersWand.ConfigurationFiles;

import de.False.BuildersWand.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config
{
    private File file;
    private FileConfiguration config;
    private long renderTime;
    private boolean renderForAllPlayers;
    private int maxBlockPlacePerTick;

    public Config(Main plugin)
    {
        this.file = new File(plugin.getDataFolder(), "config.yml");
    }

    private void save()
    {
        try
        {
            config.save(file);
            config.load(file);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void load()
    {
        config = YamlConfiguration.loadConfiguration(file);
        addDefaults();

        renderTime = config.getLong("wand.renderInterval");
        renderForAllPlayers = config.getBoolean("particle.renderForAllPlayers");
        maxBlockPlacePerTick = config.getInt("max-block-place-per-tick");
    }

    private void addDefaults()
    {
        config.options().copyDefaults(true);
        config.addDefault("wand.renderInterval", 5);
        config.addDefault("particle.renderForAllPlayers", false);
        config.addDefault("max-block-place-per-tick", 50);
        save();
    }

    public long getRenderTime()
    {
        return renderTime;
    }
    public boolean isRenderForAllPlayers()
    {
        return renderForAllPlayers;
    }
    public int getMaxBlockPlacePerTick() {
        return maxBlockPlacePerTick;
    }
}