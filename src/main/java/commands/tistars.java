package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import start.reconfig;

import java.util.Map;

/**
 * tistars 指令控制
 */
public class tistars implements CommandExecutor {

    /**
     * tistars 指令控制
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Map<Object , Object> map = reconfig.configAll[1];
        //读取语言文件
        if (args.length == 0){
            //只输入了 tistars
            sender.sendMessage(ChatColor.GREEN + map.get("help").toString());
        }else {
            //输入了 tistars ***
            switch (args[0]){
                case "reload":
                    if (sender.hasPermission("tistars.admin")){
                        //玩家有权限
                        reconfig.reconfig();
                        sender.sendMessage(ChatColor.GREEN + map.get("reload").toString());
                    }else {
                        sender.sendMessage(map.get("permissions_without").toString() +
                                "tistars.admin");
                    }
                    break;
                case "dareroom":
                    dareRoom.commands.command(sender, args);
                    //执行函数
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + map.get("unknownCommand").toString());
                    break;
            }

        }
        return false;
    }
}
