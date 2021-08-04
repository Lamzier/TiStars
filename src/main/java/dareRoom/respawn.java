package dareRoom;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import start.reconfig;
import java.util.Map;

public class respawn implements Listener {

    /**
     * 玩家重生事件
     * @param ee 玩家参数
     */
    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent ee) {
        String name = ee.getPlayer().getName();
        if (room.playersPlaying.contains(name)){
            //如果存在该名玩家，传送到设定点
            Map<Object , Object> linshi = start.getSpawnWorld();
            Location l = new Location(start.getSpawnWorldw() , (int)linshi.get("x")
                    , (int)linshi.get("y") , (int)linshi.get("z"));
            ee.getPlayer().teleport(l);
            //强制传送
            ee.getPlayer().sendMessage(reconfig.configAll[1].get("dareRoom_endGame")
                    .toString().replace("/n" , "\n"));
            new BukkitRunnable(){
                @Override
                public void run() {
                    room.playersPlaying.remove(name);
                    //3秒后删除
                }
            }.runTaskLaterAsynchronously(TiStars.getPlugin() , 60);
            //删除玩家列表
        }
    }
}
