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
        // register listener
        this.getServer().getPluginManager().registerEvents(new PaperdemoListener(), this);
        this.getCommand("demo").setExecutor(new PaperdemoCommand());
        PaperdemoRecipe.getShapedRecipe().forEach(this.getServer()::addRecipe);
        PaperdemoRecipe.getShapelessRecipe().forEach(this.getServer()::addRecipe);
        PaperdemoRecipe.getBlastingRecipe().forEach(this.getServer()::addRecipe);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Paperdemo getInstance(){
        return instance;
    }
}

