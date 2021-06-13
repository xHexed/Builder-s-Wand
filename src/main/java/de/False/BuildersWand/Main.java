package de.False.BuildersWand;

import de.False.BuildersWand.ConfigurationFiles.Config;
import de.False.BuildersWand.ConfigurationFiles.Locales;
import de.False.BuildersWand.NMS.NMS;
import de.False.BuildersWand.NMS.v_1_12.v_1_12_R1;
import de.False.BuildersWand.events.WandEvents;
import de.False.BuildersWand.events.WandStorageEvents;
import de.False.BuildersWand.manager.InventoryManager;
import de.False.BuildersWand.manager.WandManager;
import de.False.BuildersWand.utilities.ParticleUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private Locales locales = new Locales(this);
    private Config config;
    private ParticleUtil particleUtil;
    private NMS nms;
    private WandManager wandManager;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable()
    {
        setupNMS();
        wandManager = new WandManager(this, nms);
        inventoryManager = new InventoryManager(this, nms);

        loadConfigFiles();
        particleUtil = new ParticleUtil(nms, config);
        registerEvents();
        registerCommands();
    }

    private void registerCommands()
    {
        getCommand("builderswand").setExecutor(new Commands(config, wandManager, nms));
    }

    private void registerEvents()
    {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WandEvents(this, config, particleUtil, nms, wandManager, inventoryManager), this);
        pluginManager.registerEvents(new WandStorageEvents(this, config, nms, wandManager, inventoryManager), this);
    }

    private void loadConfigFiles()
    {
        config = new Config(this);
        locales.load();
        config.load();
        wandManager.load();
        inventoryManager.load();
    }

    private void setupNMS() {
        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            if ("v1_12_R1".equals(version)) {
                nms = new v_1_12_R1(this);
            }
        } catch (ArrayIndexOutOfBoundsException exn) {
            exn.printStackTrace();
        }
    }
}
