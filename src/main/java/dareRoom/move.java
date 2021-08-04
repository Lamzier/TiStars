package dareRoom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import start.reconfig;
import java.util.ArrayList;
import java.util.List;

public class move implements Listener {

    /**
     * 拒绝移动的玩家
     */
    public static List<String> name = new ArrayList<>();

    /**
     * 玩家移动事件
     * @param ee 玩家采纳数
     */
    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent ee) {
        if(name.contains(ee.getPlayer().getName())){
            //如果在拒绝移动的名单上
            ee.setCancelled(true);
            //撤销移动
            ee.getPlayer().sendMessage(reconfig.configAll[1].get("dareroom_waitPlayer")
                    .toString().replace("/n" , "\n"));
            //发送提示语
        }
    }
}
