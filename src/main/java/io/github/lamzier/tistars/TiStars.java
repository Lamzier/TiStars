package io.github.lamzier.tistars;

import config.readWrite;
import config.reconfig;
import mysql.mysql;
import storage.storage;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public final class TiStars extends JavaPlugin {

    private static TiStars plugin;



    @Override
    public void onEnable() {
        // 插件载入
        plugin = this;
        //实例化 plugin
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN
                + "[" + plugin.getName() + "]" + "Plug-in starts loading!");
        //向后台发送消息
        reconfig.reload();
        //读入配置文件
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN
                + "[" + plugin.getName() + "]" +
                reconfig.objectLanguage.get("onEnable_finish").toString()
                        .replaceAll("/n","\n")
                        .replaceAll("/r" , "\r")
        );
        //向后台发送完成插件语句
        addCommand.addCommand();
        //调用添加指令函数
        readWrite.checkData("playerData");
        //玩家数据检查






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

    }





}
