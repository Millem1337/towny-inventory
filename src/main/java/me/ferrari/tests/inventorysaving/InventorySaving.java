package me.ferrari.tests.inventorysaving;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class InventorySaving extends JavaPlugin {
    private static InventorySaving instance;

    public static InventorySaving getInstance() {
        return instance;
    }

    private File savesFile;
    private FileConfiguration saves;

    public FileConfiguration getSaves() {
        return this.saves;
    }
    public boolean saveSaves(){
        try {
            saves.save(savesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void createCustomConfig() {
        savesFile = new File(getDataFolder(), "saves.yml");
        if (!savesFile.exists()) {
            savesFile.getParentFile().mkdirs();
            try {
                savesFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        saves = new YamlConfiguration();
        try {
            saves.load(savesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        /* User Edit:
            Instead of the above Try/Catch, you can also use
            YamlConfiguration.loadConfiguration(customConfigFile)
        */
    }

    public void loadSaves(){
        try {
            saves.load(savesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        instance = this;

        if(!new File(this.getDataFolder(), "config.yml").exists()){
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
        createCustomConfig();

        getServer().getPluginManager().registerEvents(new InventoryEvents(), instance);
        TownyCommandAddonAPI.addSubCommand(TownyCommandAddonAPI.CommandType.TOWN, "inventory", new OpenInventoryCommand());
    }

    @Override
    public void onDisable() {
        saveSaves();
    }
}
