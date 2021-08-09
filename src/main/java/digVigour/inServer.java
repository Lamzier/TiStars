package digVigour;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.yaml.snakeyaml.Yaml;
import start.reconfig;
import java.util.LinkedHashMap;
import java.util.Map;

public class inServer implements Listener{
    /**
     * 玩家进入服务器
     * @param ee 玩家
     */
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent ee) {
        //初始化玩家信息
        String name = ee.getPlayer().getName();
        //获取玩家名字
        if (start.data2.get(name) == null){
            //如果没有该玩家的信息
            Map<Object , Object> player = new LinkedHashMap<>();
            //新建玩家临时map
            player.put("vigour" , reconfig.configAll[5].get("digVigour"));
            //写入map
            start.data2.put(name , player);
            //写入内存
        }
    }
}
