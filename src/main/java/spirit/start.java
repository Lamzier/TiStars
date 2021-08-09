package spirit;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.Yaml;
import start.reconfig;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 玩家精神值启动程序
 */
public class start {

    /**
     * 玩家数据
     */
    public static Map<Object , Object> data;


    /**
     * 启动程序
     */
    public static boolean star(){
        Plugin plugin = TiStars.getInstance();
        //获取主类
        reconfig.checkYaml("playerData/spiritData.yml");
        //检查玩家文件是否正常
        Yaml yml = reconfig.getYml();
        //获取yml
        try {
            data = yml.load(new FileReader(plugin.getDataFolder()
                    + "/playerData/spiritData.yml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        //读取变量文件

        new BukkitRunnable(){
            //多线程执行

            @Override
            public void run() {
                //一分钟执行一次，玩家在线 -1 ， 玩家不在线 + recoverySpirit
                Map<Object , Object> data2 = yml.load(data.get("data")
                        .toString().replace("=" , ": "));
                //获取data数据
                for (Map.Entry<Object, Object> entry : data2.entrySet()){
                    //遍历数据
                    String name = entry.getKey().toString();
                    // name 玩家名称
                    Map<Object , Object> playerdata = yml.load(entry.getValue()
                    .toString().replace("=" , ": "));
                    //获取玩家信息
                    int spirit = (int)playerdata.get("spirit");
                    //读取玩家原来的精神
                    int spiritAll = (int)reconfig.configAll[2].get("spirit");
                    if (plugin.getServer().getPlayerExact(name) == null || (boolean)playerdata.get("sleeping")){
                        //玩家不在线 或者 玩家在睡觉, 精神 ++
                        spirit += (int)reconfig.configAll[2].get("recoverySpirit");
                        if (spirit > spiritAll){
                            //如果超出了范围
                            spirit = spiritAll;
                        }//否则不操作
                        //增加精神
                    }else {
                        //玩家在线 , 且没在睡觉 -1
                        spirit -= 1;
                        if (spirit < 0){
                            //如果已经到负数了
                            spirit = 0;
                        }
                        //异步负面效果 , spirit 为当前精神值
                        //(int)reconfig.configAll[2].get("spirit") 为最大精神值
                        //name 为玩家名称
                        PotionEffect PotionEffect = new PotionEffect(PotionEffectType.SLOW, 1200, 1, true, true, true);
                        //缓慢一分钟，强度1
                        PotionEffect PotionEffect2 = new PotionEffect(PotionEffectType.BLINDNESS, 1200, 1, true, true, true);
                        //失明一分钟，强度1
                        int finalSpirit = spirit;
                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                //负面效果
                                if (finalSpirit == spiritAll * 0.5 || finalSpirit == spiritAll * 0.25 || finalSpirit <= spiritAll * 0.05 ){
                                    //等于 0.5 0.25 小于0.05 触发负面buff
                                    Player ee = plugin.getServer().getPlayerExact(name);
                                    //获取玩家类
                                    assert ee != null;
                                    if (ee.hasPermission("tistars.admin")){
                                        //检查是否有管理员权限
                                        Objects.requireNonNull(ee.getPlayer()).sendMessage(reconfig.configAll[1].get("spirit_admin")
                                                .toString() + "tistars.admin");
                                    }else {
                                        //没有管理员权限
                                        Bukkit.getScheduler().runTask(TiStars.getPlugin(), () -> {
                                            //转同步操作
                                            ee.addPotionEffect(PotionEffect);
                                            ee.addPotionEffect(PotionEffect2);
                                        });
                                        //给予效果
                                        //告诉玩家
                                        List<String> msg = (List<String>) reconfig.configAll[1].get("interest_spirit");
                                        int index = (int) (Math.random() * msg.size());
                                        ee.sendMessage(msg.get(index));
                                        //随机发送语句
                                    }
                                }
                            }
                        }.runTaskAsynchronously(TiStars.getPlugin());
                    }
                    playerdata.put("spirit" , spirit);
                    //写入playerdata
                    data2.put(name , playerdata);
                    //写入data2
                }
                data.put("data" , data2);
                //写入data
            }
        }.runTaskTimerAsynchronously(TiStars.getPlugin() ,1200,1200 );
        //默认delay 为1200 默认period 为1200
        //以上玩家更新精神值
        new BukkitRunnable(){
            //一小时执行一次

            @Override
            public void run() {
                writeFile();
            }
        }.runTaskTimerAsynchronously(TiStars.getPlugin() , 72000, 72000);
        //执行写入本地文件 72000 72000
        return true;
    }

    /**
     * 写入本地文件
     */
    public static void writeFile(){
        try {
            reconfig.getYml().dump(data , new FileWriter(
                    TiStars.getInstance().getDataFolder()
                    + "/playerData/spiritData.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
