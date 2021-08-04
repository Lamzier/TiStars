package dareRoom;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import start.reconfig;

/**
 * dareRoom 指令控制
 */
public class commands {

    public static void command(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if (!start.getLoad()){ return; }
        //判断是否已经开启了该功能
        if (args.length == 1){
            sender.sendMessage(reconfig.configAll[1].get("dareRoom_help").toString()
                .replace("/n" , "\n"));
            return;
        }
        switch (args[1]){
            case "join":
                if (args.length <= 2){
                    //只有add
                    room.join(sender.getName() , sender);
                }else {
                    //还有更多参数
                    if (sender.hasPermission("tistars.admin")) {
                        room.join(args[2] , sender);
                    }else {
                        //权限不足
                        sender.sendMessage(reconfig.configAll[1].get("permissions_without").toString() +
                                "tistars.admin");
                    }
                }
                break;
            case "leave":
                if (args.length <= 2){
                    //只有add
                    room.leave(sender.getName() , sender);
                }else {
                    //还有更多参数
                    if (sender.hasPermission("tistars.admin")) {
                        room.leave(args[2] , sender);
                    }else {
                        //权限不足
                        sender.sendMessage(reconfig.configAll[1].get("permissions_without").toString() +
                                "tistars.admin");
                    }
                }
                break;
            default:
                break;

        }

    }

}
