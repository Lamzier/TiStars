package io.github.lamzier.tistars;

import commands.tistars;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * 指令添加控制模块
 */
public class addCommand {

    /**
     * 指令添加函数
     */
    public static boolean addCommand(){
        JavaPlugin plugin = TiStars.getPlugin();
        //获取主类参数
        Objects.requireNonNull(plugin.getCommand("tistars")).setExecutor(new tistars());
        //添加 tistars指令 调用函数 tistars
        return true;
    }



}
