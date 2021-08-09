package digVigour;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Map;
import start.reconfig;

public class blockBreak implements Listener {

    /**
     * 玩家破坏方块
     * @param ee player 玩家参数
     */
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent ee) {
        //获取方块是否属于管辖范围
        //不是玩家破坏的方块
        if (start.block.containsKey(ee.getBlock().getBlockData().getAsString())){
            //属于该方块
            int dig = (int) start.block.get(ee.getBlock().getBlockData().getAsString());
            //获取需要减的值
            String name = ee.getPlayer().getName();
            //获取玩家名字
            Map<Object , Object> player = (Map<Object, Object>) start.data2.get(name);
            //获取改名玩家的map
            int vigour = (int) player.get("vigour");
            //获取当前玩家的活力值
            vigour -= dig;
            if (vigour > start.maxVigour){
                vigour = start.maxVigour;
                //处理超越值
            }
            if (vigour < 0){
                vigour = 0;
                //处理最低值
            }
            player.put("vigour" , vigour);
            //写入到player
            start.data2.put(name , player);
            //写入到内存
            //下面执行判断负面buff机制
            int finalVigour = vigour;
            new BukkitRunnable(){
                @Override
                public void run() {
                    //如果低于 0.05 则有 100%概率 低于0.1 20% ， 0.25 10% ， 0.5 5%
                    //判断是否触发
                    int maxVigour = start.maxVigour;
                    boolean tired = false;
                    //默认不触发
                    if (finalVigour <= maxVigour * 0.05){
                        //如果低于0.05
                        tired = true;
                    }else if (finalVigour <= maxVigour * 0.1 && Math.random() <= 0.2){
                        //低于 0.1 且运气爆表
                        tired = true;
                    }else if (finalVigour <= maxVigour * 0.25 && Math.random() <= 0.1){
                        tired = true;
                    }else if (finalVigour <= maxVigour * 0.5 && Math.random() <= 0.5){
                        tired = true;
                    }
                    if (ee.getPlayer().hasPermission("tistars.admin")){
                        tired = false;
                        ee.getPlayer().sendMessage(reconfig.configAll[1].get("digVigour_admin")
                                .toString() + "tistars.admin");
                    }
                    if (!tired) return;
                    //如果不中，可以滚了
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW_DIGGING, start.duration * 20, 1, true, true, true);
                    //慢速挖掘属性，强度1
                    //给予玩家buff
                    Bukkit.getScheduler().runTask(TiStars.getPlugin() , ()-> {
                        //转同步操作
                        ee.getPlayer().addPotionEffect(potionEffect);
                    });
                    //随机发送语句
                    ee.getPlayer().sendMessage(start.msg.get((int)(Math.random() * start.msg.size())));
                    //发送提示语
                }
            }.runTaskAsynchronously(TiStars.getInstance());
        }
    }
}
