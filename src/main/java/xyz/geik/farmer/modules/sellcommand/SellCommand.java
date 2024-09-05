package xyz.geik.farmer.modules.sellcommand;

import lombok.Getter;
import org.bukkit.Bukkit;
import xyz.geik.farmer.Main;
import xyz.geik.farmer.modules.FarmerModule;
import xyz.geik.farmer.modules.sellcommand.commands.MainCommand;
import xyz.geik.farmer.modules.sellcommand.configuration.ConfigFile;
import xyz.geik.farmer.shades.storage.Config;
import xyz.geik.glib.GLib;
import xyz.geik.glib.chat.ChatUtils;
import xyz.geik.glib.shades.okaeri.configs.ConfigManager;
import xyz.geik.glib.shades.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SellCommand module main class
 */
@Getter
public class SellCommand extends FarmerModule {

    /**
     * Constructor of class
     */
    public SellCommand() {}

    @Getter
    private static SellCommand instance;

    private Config langFile;

    @Getter
    private static final HashMap<String,String> nameReplacer  = new HashMap<>();
    @Getter
    private static List<String> sellAllCommands  = new ArrayList<>();

    private ConfigFile configFile;

    /**
     * onEnable method of module
     */
    public void onEnable() {
        instance = this;
        this.setLang(Main.getConfigFile().getSettings().getLang(), Main.getInstance());
        setupFile();

        if (configFile.isStatus()) {
            Main.getCommandManager().registerCommand(new MainCommand());
            // Adds all the replaces to the replacer
            if (!configFile.getSellAllCommands().isEmpty())
                sellAllCommands.addAll(configFile.getSellAllCommands());
            try {
                SellCommand.getInstance().getLang().getTextList("sellReplace").forEach(element -> {
                    String[] parts = element.split(":");
                    nameReplacer.put(parts[0], parts[1]);
                });
            }
            catch (Exception ignored) {}
        }
        else {
            this.setEnabled(false);
            String messagex = "&3[" + GLib.getInstance().getName() + "] &c" + getName() + " is not loaded.";
            ChatUtils.sendMessage(Bukkit.getConsoleSender(), messagex);
        }
    }

    /**
     * onDisable method of module
     */
    @Override
    public void onDisable() {}

    public void setupFile() {
        configFile = ConfigManager.create(ConfigFile.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(new File(Main.getInstance().getDataFolder(), String.format("/modules/%s/config.yml", getName())));
            it.saveDefaults();
            it.load(true);
        });
    }

}