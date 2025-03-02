package top.tachibana.paperdemo;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MsgCommand implements CommandExecutor/*, TabCompleter*/{
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args){
        // 使用BungeeCord chat API
        TextComponent text1 = new TextComponent("这是");
        TextComponent text2 = new TextComponent("一条消息\n");
        TextComponent text3 = new TextComponent("这是");
        TextComponent text4 = new TextComponent("作者的博客链接");

        text2.setColor(ChatColor.DARK_RED);
        text3.setBold(true);
        text3.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://syju.org/"));

        sender.sendMessage(text1, text2, text3, text4);
        return true;
    }
//    @Override
//    public @Nullable List<String> onTabComplete(
//            @NotNull CommandSender sender,
//            @NotNull Command command,
//            @NotNull String label,
//            @NotNull String[] args){
//        return null;
//    }
}
