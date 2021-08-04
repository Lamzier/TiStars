package dareRoom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
public class outServer implements Listener {

    /**
     * 玩家离开服务器
     * @param ee 玩家参数
     */
    @EventHandler
    public void PlayerLeaveEvent(PlayerQuitEvent ee) {
        String name = ee.getPlayer().getName();
        room.playersPlaying.remove(name);
        room.players.remove(name);
        //删除等待列表
    }
}
