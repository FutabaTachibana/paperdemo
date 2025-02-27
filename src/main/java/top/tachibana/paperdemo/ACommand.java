package top.tachibana.paperdemo;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

class ACommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack stack, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            stack.getSender().sendRichMessage("<rainbow>Fun activated!");
        }
    }
}
