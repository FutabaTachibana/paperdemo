package top.tachibana.paperdemo;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private static FileConfiguration config = Paperdemo.getInstance().getConfig();
    private static int num = config.getInt("num");
    private static String str1 = config.getString("words.word1");
    private static String str2 = config.getString("words.word2");
    public static void reload(){
        File file = new File(Paperdemo.getInstance().getDataFolder(), "config.yml");
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        Config.num = config.getInt("num");
        Config.str1 = config.getString("words.word1");
        Config.str2 = config.getString("words.word2");
    }
    public static int getNum() {
        return num;
    }
    public static String getStr1() {
        return str1;
    }
    public static String getStr2() {
        return str2;
    }
}
