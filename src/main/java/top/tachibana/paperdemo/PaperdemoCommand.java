package top.tachibana.paperdemo;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
    private final List<String> sub_command = List.of("print", "suicide", "spawn", "msg");
    private final List<String> sub_command_msg = List.of("join", "leave");
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
                if(args.length == 1){
                    // 使用Adventure API
                    final Component text1 = Component
                            .text("这是一条消息，", NamedTextColor.GREEN, TextDecoration.ITALIC)
                            .append(Component.text("这是第二句话", TextColor.color(0x9966FF), TextDecoration.OBFUSCATED))
                            .append(Component.text("点我复制链接", NamedTextColor.YELLOW, TextDecoration.BOLD)
                                    .clickEvent(ClickEvent.copyToClipboard("https://syju.org/posts/mcplugin")))
                            .append(Component.text("点我打开链接", NamedTextColor.AQUA, TextDecoration.UNDERLINED)
                                    .clickEvent(ClickEvent.openUrl("https://github.com/FutabaTachibana/paperdemo")))
                            .append(Component.text("指针拖动到我上面试试", NamedTextColor.GREEN, TextDecoration.STRIKETHROUGH)
                                    .hoverEvent(HoverEvent.showText(Component.text("你真拖啊", NamedTextColor.DARK_RED, TextDecoration.STRIKETHROUGH))));
                    final Component text2 = MiniMessage.miniMessage().deserialize(
                            "使用<blue><click:open_url:\"https://jd.advntr.dev/text-minimessage/4.19.0/\">MiniMessage</click></blue>可以<bold>更加方便</bold>地格式化输出。\n" +
                                    "MiniMessage的语法格式有点像HTML，具体请查看官方文档。下面我简单演示了MiniMessage的用法。\n" +
                                    "<rainbow>Rainbow是彩虹，彩虹是Rainbow</rainbow>"
                    );

                    sender.sendMessage(text1);
                    sender.sendMessage(text2);
                    sender.sendRichMessage(
                            "使用<blue><click:open_url:\"https://jd.advntr.dev/text-minimessage/4.19.0/\">MiniMessage</click></blue>可以<bold>更加方便</bold>地格式化输出。\n" +
                                    "MiniMessage的语法格式有点像HTML，具体请查看官方文档。下面我简单演示了MiniMessage的用法。\n" +
                                    "<rainbow>Rainbow是彩虹，彩虹是Rainbow</rainbow>"
                    );
                    return true;
                }
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
