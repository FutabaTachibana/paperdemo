package top.tachibana.paperdemo;

import org.bukkit.plugin.java.JavaPlugin;

public final class Paperdemo extends JavaPlugin {
    private static Paperdemo instance;

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
        PaperdemoRecipe.getShapedRecipe().forEach(this.getServer()::addRecipe);
        PaperdemoRecipe.getShapelessRecipe().forEach(this.getServer()::addRecipe);
        PaperdemoRecipe.getBlastingRecipe().forEach(this.getServer()::addRecipe);
        // 插件配置
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Paperdemo getInstance(){
        return instance;
    }
}

