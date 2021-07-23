package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * tistars 指令控制
 */
public class tistars implements CommandExecutor {

    /**
     * tistars 指令控制
     * @param sender
     * @param command
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //玩家输入了 tistars指令
        sender.sendMessage(ChatColor.GREEN + "欢迎使用[Tistars]插件！");
        return false;
    }
}
