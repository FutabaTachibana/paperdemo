package top.tachibana.paperdemo;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Paperdemo extends JavaPlugin {
    private static Paperdemo instance;
    private static FileConfiguration custom;

    @Override
    public void onLoad(){
        // Plugin load logic
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        // 注册事件监听器
        this.getServer().getPluginManager().registerEvents(new PaperdemoListener(), this);
        this.getCommand("demo").setExecutor(new PaperdemoCommand());
        this.getCommand("msg").setExecutor(new MsgCommand());
        this.getCommand("config").setExecutor(new ConfigCommand());
        // 注册配方
        PaperdemoRecipe.getShapedRecipe().forEach(this.getServer()::addRecipe);
        PaperdemoRecipe.getShapelessRecipe().forEach(this.getServer()::addRecipe);
        PaperdemoRecipe.getBlastingRecipe().forEach(this.getServer()::addRecipe);
        // 插件配置
        saveResource("custom.yml", false);
        saveDefaultConfig();
        // 加载自定义配置
        File file = new File(Paperdemo.getInstance().getDataFolder(), "custom.yml");
        Paperdemo.custom = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Paperdemo getInstance(){
        return instance;
    }

    public static FileConfiguration getCustom(){
        return Paperdemo.custom;
    }
    public static void saveCustom() throws IOException {
        File file = new File(Paperdemo.getInstance().getDataFolder(), "custom.yml");
        custom.save(file);
    }
}

