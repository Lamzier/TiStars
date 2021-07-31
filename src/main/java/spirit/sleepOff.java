package spirit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.yaml.snakeyaml.Yaml;
import start.reconfig;
import java.util.Map;

public class sleepOff implements Listener {
    /**
     * 玩家躺下床
     * @param ee 玩家
     */
    @EventHandler
    public void PlayerBedLeaveEvent(PlayerBedLeaveEvent ee) {
        String name = ee.getPlayer().getName();
        //玩家名字 name
        Yaml yml = reconfig.getYml();
        Map<Object , Object> data2 = yml.load(start.data.get("data")
                .toString().replace("=" , ": "));
        //获取data2
        Map<Object , Object> player = yml.load(data2.get(name)
                .toString().replace("=" , ": "));
        player.put("sleeping" , false);
        //躺床上了
        data2.put(name , player);
        //定义input
        start.data.put("data" , data2);
        //写入内存
        System.out.println(start.data);
    }
}
