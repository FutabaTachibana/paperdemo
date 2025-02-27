package top.tachibana.paperdemo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class PaperdemoCommand implements CommandExecutor, TabCompleter {
    // 实现一个指令，有print, suicide, spawn, msg四个子命令
    // /demo print 打印插件的名字
    // /demo suicide 杀死执行指令的玩家
    // /demo spawn 召唤一头猪
    // /demo msg join 输出"xxx加入了游戏"的消息
    // /demo msg leave 输出"xxx离开了游戏"的消息
    private List<String> sub_command = List.of("print", "suicide", "spawn", "msg");
    private List<String> sub_command_msg = List.of("join", "leave");
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args){
        if(args.length == 0) return false;
        switch (args[0]){
            case "print":
                sender.sendMessage(Component.text("插件名字: ").append(Component.text(Paperdemo.getInstance().toString(), NamedTextColor.YELLOW)));
                return true;
            case "suicide":
                if(sender instanceof Player player){
                    player.setHealth(0);
                    player.sendMessage(Component.text("已杀死", NamedTextColor.RED).append(Component.text(sender.getName(), NamedTextColor.YELLOW)));
                    return true;
                }
                else{
                    return false;
                }
            case "spawn":
                if(sender instanceof Player player){
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.PIG);
                    return true;
                }
                else{
                    return false;
                }
            case "msg":
                switch (args[1]){
                    case "join":
                        sender.sendMessage(Component.text(sender.getName() + "加入了游戏", NamedTextColor.YELLOW));
                        return true;
                    case "leave":
                        sender.sendMessage(Component.text(sender.getName() + "离开了游戏", NamedTextColor.YELLOW));
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args){
        switch (args.length) {
            case 1:
                if (args[0].isEmpty()) return sub_command;
                else return sub_command.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
            case 2:
                if (args[0].equals("msg")) {
                    if (args[1].isEmpty()) return sub_command_msg;
                    else
                        return sub_command_msg.stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
                }
                else return null;
            default:
                return null;
        }
    }
}
