package config;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 与配置相关
 * 以及配置文件存储变量
 */
public class reconfig {

    /**
     * 系统语言 zh / en
     */
    public static String language;


    /**
     * 初次或重载配置文件
     */
    public static void reload(){
        Plugin plugin = TiStars.getInstance();
        language = Locale.getDefault().getLanguage();
        //获取系统语言
        List<String> langs = Arrays.asList("en" , "zh");
        //创建现有的语言库
        if (!langs.contains(language)){
            //如果拥有语言库里不包含系统语言则默认英文
            language = "en";
            //设置配置文件语言
        }
        //添加文件夹
        List<File> folders = new ArrayList<>();
        File file = plugin.getDataFolder();
        //file 当前插件配置文件目录
        /*
         如果需要添加配置文件夹请看这里
         */
        folders.add(new File (file.getPath()));
        folders.add(new File (file.getPath() + "/playerData"));
        for (File eachFolder : folders){
            if (!eachFolder.mkdirs()){
               //如果失败
                TiStars.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + plugin.getName() + "]发现配置文件目录！" + eachFolder);
            }
            //创建文件目录
        }
        /*
        如果需要添加新的配置文件或文件请看这里
         */
        readWrite.saveConfiguration("" , "config.yml");
        //检查配置文件




    }





}
