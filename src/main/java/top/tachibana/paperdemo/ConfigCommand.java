package top.tachibana.paperdemo;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigCommand implements CommandExecutor, TabCompleter {
    private final List<String> ARGS = List.of("reload", "edit", "save", "num", "str1", "str2");
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args){
        if(args.length == 0){
            sender.sendMessage(Component.text("不是这样用的"));
            return true;
        }
        switch (args[0]){
            case "reload":
                Config.reload();
                sender.sendMessage(Component.text("已成功重载插件"));
                break;
            case "edit":
                if(args.length == 1 || args.length == 2){
                    sender.sendMessage(Component.text("不是这样用的"));
                    return true;
                }
                else{
                    Paperdemo.getCustom().set(args[1], args[2]);
                    sender.sendMessage(Component.text("已将 " + args[1] + " 设置为 " + args[2]));
                }
                break;
            case "save":
                try {
                    Paperdemo.saveCustom();
                    sender.sendMessage(Component.text("已保存"));
                } catch (IOException e) {
                    sender.sendMessage(Component.text("保存失败"));
                }
                break;
            case "num":
                sender.sendMessage(Component.text("num的值是" + Config.getNum()));
                break;
            case "str1":
                sender.sendMessage(Component.text("str1的值是" + Config.getStr1()));
                break;
            case "str2":
                sender.sendMessage(Component.text("str2的值是" + Config.getStr2()));
                break;
            default:
                sender.sendMessage(Component.text("不是这样用的"));
                break;
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args){
        if(args.length == 1) {
            if (args[0].isEmpty()) return ARGS;
            else return ARGS.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        }
        return null;
    }
}
