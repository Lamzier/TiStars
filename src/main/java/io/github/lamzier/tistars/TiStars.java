package io.github.lamzier.tistars;

import org.bukkit.plugin.java.JavaPlugin;

public final class TiStars extends JavaPlugin {

    private static TiStars plugin;

    @Override
    public void onEnable() {
        // 插件载入
        plugin = this;
        //实例化 plugin




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
