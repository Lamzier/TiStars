package dareRoom;

import start.reconfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * 玩家传送事件
 */
public class teleport implements Listener {

    @EventHandler
    public void	PlayerTeleportEvent(PlayerTeleportEvent ee) {
        if (room.playersPlaying.contains(ee.getPlayer().getName())){
            //该玩家正在副本之争
            if (!room.occupyWorld.contains(ee.getTo().getWorld())){
                //如果传送地点不是本地图
                if (ee.getPlayer().hasPermission("tistars.admin")){
                    ee.getPlayer().sendMessage(reconfig.configAll[1].get("dareRoom_adminTp").toString()
                            .replace("/n" , "\n"));
                    return;
                }
                ee.getPlayer().sendMessage(reconfig.configAll[1].get("dareRoom_errOutWorld")
                        .toString().replace("/n" , "\n"));
                ee.setCancelled(true);
            }
        }else {
            //不在之争
            if (room.occupyWorld.contains(ee.getTo().getWorld())){
                if (ee.getPlayer().hasPermission("tistars.admin")){
                    ee.getPlayer().sendMessage(reconfig.configAll[1].get("dareRoom_adminTp").toString()
                            .replace("/n" , "\n"));
                    return;
                }
                ee.getPlayer().sendMessage(reconfig.configAll[1].get("dareRoom_errInWorld")
                        .toString().replace("/n" , "\n"));
                ee.setCancelled(true);
            }
        }
    }
}
