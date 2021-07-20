package io.github.lamzier.tistars;

import config.reconfig;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TiStars extends JavaPlugin {

    private static TiStars plugin;

    @Override
    public void onEnable() {
        // 插件载入
        plugin = this;
        //实例化 plugin
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + plugin.getName() + "]插件加载！");
        //向后台发送消息
        reconfig.reload();
        //读入配置文件
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + plugin.getName() + "]插件加载完成！" +
                "\r\n 作者：Lamzy" +
                "\r\n 联系方式：QQ1255461704" +
                "\r\n 交流Q群：805410341");
    }

    /**
     * 获取实例plugin (getInstance)
     * 用于获取服务器 plugin 实例对象
     */
    public static TiStars getInstance(){ return plugin; }

    @Override
    public void onDisable() {
        // 插件卸载

    }





}
