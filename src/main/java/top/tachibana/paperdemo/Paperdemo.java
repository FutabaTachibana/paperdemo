package top.tachibana.paperdemo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Paperdemo extends JavaPlugin {
    private static Paperdemo instance;
    private static FileConfiguration custom;
    private static Inventory menu;
    private ItemStack createItemStack(Material material, Component displayName){
        ItemStack itemStack = new ItemStack(material);
        itemStack.editMeta((meta) -> {
            meta.displayName(displayName);
        });
        return itemStack;
    }
    private void loadMenu(){
        Paperdemo.menu = instance.getServer().createInventory(
                null, InventoryType.CHEST, Component.text("插件菜单", NamedTextColor.DARK_PURPLE)
        );
        menu.addItem(
                createItemStack(Material.GRASS_BLOCK, Component.text("创造模式", NamedTextColor.GREEN)),
                createItemStack(Material.IRON_SWORD, Component.text("生存模式", NamedTextColor.WHITE)),
                createItemStack(Material.MAP, Component.text("冒险模式", NamedTextColor.DARK_RED)),
                createItemStack(Material.ENDER_EYE, Component.text("旁观模式", NamedTextColor.BLUE)),
                createItemStack(Material.BARRIER, Component.text("退出菜单", NamedTextColor.RED))
        );
    }

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
        this.saveDefaultConfig();
        this.saveResource("custom.yml", false);
        // 加载自定义配置
        File file = new File(this.getDataFolder(), "custom.yml");
        Paperdemo.custom = YamlConfiguration.loadConfiguration(file);
        // 加载插件菜单
        loadMenu();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Paperdemo getInstance(){
        return Paperdemo.instance;
        //也可以这么写
        //return (Paperdemo)Bukkit.getPluginManager().getPlugin("Paperdemo");
    }

    public static Inventory getMenu() {
        return menu;
    }

    public static FileConfiguration getCustom(){
        return Paperdemo.custom;
    }
    public static void saveCustom() throws IOException {
        File file = new File(Paperdemo.getInstance().getDataFolder(), "custom.yml");
        Paperdemo.custom.save(file);
    }
}

