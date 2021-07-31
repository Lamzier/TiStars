package spirit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.yaml.snakeyaml.Yaml;
import start.reconfig;
import java.util.Map;

//玩家睡觉
public class sleepOn implements Listener {

    /**
     * 玩家躺上床
     * @param ee 玩家
     */
    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent ee) {
        String name = ee.getPlayer().getName();
        //玩家名字 name
        Yaml yml = reconfig.getYml();
        Map<Object , Object> data2 = yml.load(start.data.get("data")
            .toString().replace("=" , ": "));
        //获取data2
        Map<Object , Object> player = yml.load(data2.get(name)
                .toString().replace("=" , ": "));
        player.put("sleeping" , true);
        //躺床上了
        data2.put(name , player);
        //定义input
        start.data.put("data" , data2);
        //写入内存
    }




}
