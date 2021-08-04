package io.github.lamzier.tistars;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import start.reconfig;

public final class TiStars extends JavaPlugin {

    private static TiStars plugin;



    @Override
    public void onEnable() {
        // 插件载入
        plugin = this;
        //实例化plugin
        getServer().getConsoleSender().sendMessage(
                ChatColor.GREEN + "[" + plugin.getName() + "]" +
                        "Plug-in starts loading!"
        );
        //向后台发送提示语
        reconfig.reconfig();
        //执行读取配置文件
        addCommand.add();
        //调用添加指令函数
        if ((boolean)reconfig.configAll[0].get("pro")){
            //开启了pro版本验证
            pro.start.star();
            //进行pro版本验证
        }else {
            //没开启pro版本验证
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN
                    + "[" + plugin.getName() + "]" +
                    reconfig.configAll[1].get("offPro").toString());
        }
        //注册事件↓↓↓
        //getServer().getPluginManager().registerEvents(new spirit.sleepOn() , this);
        //getServer().getPluginManager().registerEvents(new spirit.sleepOff() , this);
        //getServer().getPluginManager().registerEvents(new spirit.inServer() , this);
        //注册事件↑↑↑
        if ((boolean)reconfig.configAll[0].get("player_spirit")){
            //如果开启玩家精神值
            spirit.start.star();
            //启动开启函数
            getServer().getPluginManager().registerEvents(new spirit.sleepOn() , this);
            getServer().getPluginManager().registerEvents(new spirit.sleepOff() , this);
            getServer().getPluginManager().registerEvents(new spirit.inServer() , this);
            //注册事件
        }
        if ((boolean)reconfig.configAll[0].get("dareRoom")){
            //如果开启副本房间
            if (dareRoom.start.star()){
                //如果开启成功
                getServer().getPluginManager().registerEvents(new dareRoom.teleport() , this);
                getServer().getPluginManager().registerEvents(new dareRoom.outServer() , this);
                getServer().getPluginManager().registerEvents(new dareRoom.respawn() , this);
                getServer().getPluginManager().registerEvents(new dareRoom.move() , this);
                //注册事件
            }
        }
        new papi().register();
        //注册papi
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN
                + "[" + plugin.getName() + "]" +
                reconfig.configAll[1].get("onEnable_finish").toString()
                .replace("/r" , "\r").replace("/n" , "\n")
        );
        //向后台发送提示语
    }

    /**
     * 获取实例plugin (getInstance)
     * 用于获取服务器 plugin 实例对象
     */
    public static TiStars getInstance(){ return plugin; }

    /**
     * 获取实例plugin (getPlugin)
     * 用于获取服务器 plugin 实例对象
     */
    public static JavaPlugin getPlugin() { return plugin; }

    @Override
    public void onDisable() {
        // 插件卸载
        if ((boolean)reconfig.configAll[0].get("player_spirit")){
            //如果开启了精神值操作，保存文件
            spirit.start.writeFile();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN
                + "[" + plugin.getName() + "]" +
                reconfig.configAll[1].get("offEnable_finish").toString()
                        .replace("/r" , "\r").replace("/n" , "\n")
        );
        //通知后台
    }





}
