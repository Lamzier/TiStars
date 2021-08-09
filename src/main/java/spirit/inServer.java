package spirit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.yaml.snakeyaml.Yaml;
import start.reconfig;
import java.util.LinkedHashMap;
import java.util.Map;

public class inServer implements Listener {

    /**
     * 玩家进入服务器
     * @param ee 玩家
     */
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent ee) {
        //检查是否含有该玩家的数据
        Yaml yml = reconfig.getYml();
        Map<Object , Object> data2 = yml.load(start.data.get("data").toString()
                .replace("=" , ": "));
        //读取data2
        String name = ee.getPlayer().getName();
        Map<Object , Object> player = new LinkedHashMap<>();
        if (data2.get(name) == null){
            //没有该人信息
            player.put("sleeping" , false);
            player.put("spirit" , reconfig.configAll[2].get("spirit"));
            //添加玩家的基本信息
        }else {
            //有这个人的信息
            player = yml.load(data2.get(name).toString()
                .replace("=" , ": "));
            //重新获取player
            player.put("sleeping" , false);
            //初始化睡眠状态
        }
        data2.put(name , player);
        start.data.put("data" , data2);
        //写入内存
    }

}
