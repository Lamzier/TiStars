package digVigour;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.Yaml;
import start.reconfig;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class start {

    /**
     * 玩家数据
     */
    public static Map<Object , Object> data;
    /**
     * data下的玩家数据
     */
    public static Map<Object , Object> data2;
    /**
     * 破坏特殊方块
     */
    public static Map<Object , Object> block;
    /**
     * 最大活力值
     */
    public static int maxVigour;
    /**
     * 恢复活力值
     */
    public static int recovery;
    /**
     * 负面buff持续时间
     */
    public static int duration;
    public static List<String> msg;
    /**
     * 启动检查函数
     * @return boolean 检查成功与否
     */
    public static boolean star(){
        Plugin plugin = TiStars.getInstance();
        //获取主类
        reconfig.checkYaml("playerData/digVigourData.yml");
        //检查配置文件
        Yaml yml = reconfig.getYml();
        //获取yml
        try {
            data = yml.load(new FileReader(plugin.getDataFolder() +
                    "/playerData/digVigourData.yml"));
            //读取配置文件
            data2 = yml.load(data.get("data").toString()
                    .replace("=" , ": "));
            //读取data2
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        //新建临时map
        Map<Object , Object> blockxx = new HashMap<>();
        {
            //读取配置文件
            Map<Object, Object> blockx = yml.load(reconfig.configAll[5].get("block").toString()
                    .replace("=", ": "));
            //处理转义符号
            for (Map.Entry<Object, Object> xx : blockx.entrySet()) {
                //遍历数组
                blockxx.put(xx.getKey().toString().replace("<", "[")
                        .replace(">", "]").replace("||", "=")
                        .replace("|", ":"), xx.getValue());
                //写入新map

            }
        }
        block = blockxx;
        //写入内存
        msg = (List<String>) reconfig.configAll[1].get("digvigour_msg");
        //获取挖掘疲劳提示语
        duration = (int) reconfig.configAll[5].get("duration");
        //获取负面buff持续时间
        maxVigour = (int)reconfig.configAll[5].get("digVigour");
        //获取最大活力值
        recovery = (int)reconfig.configAll[5].get("recovery");
        //获取恢复值
        new BukkitRunnable(){
            @Override
            public void run() {
                //一分钟执行一次 , 增加劳动值
                for (Map.Entry<Object, Object> xx : data2.entrySet()){
                    //遍历数组
                    Map<Object , Object> player = (Map<Object, Object>) xx.getValue();
                    //新建玩家临时map
                    int vigour = (int) player.get("vigour");
                    //获取玩家当前活力值
                    vigour += recovery;
                    //恢复
                    if (vigour > maxVigour){
                        vigour = maxVigour;
                        //处理超越值
                    }
                    if (vigour < 0){
                        vigour = 0;
                        //处理低于值
                    }
                    player.put("vigour" , vigour);
                    xx.setValue(player);
                    //写入内存
                }
            }
        }.runTaskTimerAsynchronously(plugin , 1200 , 1200);
        new BukkitRunnable(){
            //一小时执行一次
            @Override
            public void run() {
                //一小时执行一次
                writeFile();
                //写入本地文件
            }
        }.runTaskTimerAsynchronously(TiStars.getPlugin() , 72000 , 72000);
        return true;
    }

    /**
     * 写入本地文件
     */
    public static void writeFile(){
        data.put("data" , data2);
        //更新data
        try {
            reconfig.getYml().dump(data , new FileWriter(
                    TiStars.getInstance().getDataFolder()
                    + "/playerData/digVigourData.yml"
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
