package dareRoom;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;
import start.reconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 副本房间准备
 */
public class start {

    /**
     * 读取的可用副本世界
     */
    private static final List<World> worlds = new ArrayList<>();
    /**
     * 读取可用世界数据
     */
    private static Map<Object , Object> worldsMap;
    /**
     * 是否已经开启
     */
    private static boolean load = false;
    /**
     * 返回世界坐标
     */
    private static Map<Object , Object> spawnWorld = new HashMap<>();
    /**
     * 返回世界
     */
    private static World spawnWorldw;



    public static boolean star(){
        Plugin plugin = TiStars.getInstance();
        if (!pro.start.getPro()){
            //如果没有开启
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW
                    + "[" + plugin.getName() + "]" +
                    reconfig.configAll[1].get("dareRoom_failed").toString());
            return false;
        }
        //读取世界
        //获取开启时间
        Yaml yml = reconfig.getYml();
        worldsMap = yml.load(reconfig.configAll[4].get("world").toString()
            .replace("=" , ": "));
        //读取世界
        List<World> worldLinshi = plugin.getServer().getWorlds();
        //读取现在读取的世界
        spawnWorld = yml.load(reconfig.configAll[4].get("data").toString()
                .replace("=" , ": "));
        //读取世界
        for(Map.Entry<Object, Object> entry : worldsMap.entrySet()){
            //遍历map数组
            String name = entry.getKey().toString();
            //世界名称
            boolean ifExa = false;
            boolean ifExa2 = false;
            for (World w : worldLinshi){
                //遍历数组
                if (w.getName().equals(name)){
                    //如果存在有相同的
                    worlds.add(w);
                    //添加世界
                    ifExa = true;
                }
                if (w.getName().equals(spawnWorld.get("name"))){
                    //如果找到了这个世界
                    spawnWorldw = w;
                    ifExa2 = true;
                }
            }
            //判断是否存在
            if (!ifExa || !ifExa2){
                //如果不存在
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW
                        + "[" + plugin.getName() + "]" +
                        reconfig.configAll[1].get("dareRoom_notFindWorld").toString()
                    + entry.getKey());
                return false;
            }
        }
        //副本世界加载成功
        room.idleWorld = worlds;
        //写入空闲世界房间
        load = true;
        return true;
    }

    public static Map<Object , Object> getWorldsMap() { return worldsMap; }

    public static boolean getLoad(){ return load; }

    public static World getSpawnWorldw(){ return spawnWorldw; }

    public static Map<Object , Object> getSpawnWorld(){ return spawnWorld; }

}