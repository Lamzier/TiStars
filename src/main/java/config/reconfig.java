package config;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * 与配置相关
 * 以及配置文件存储变量
 */
public class reconfig {

    /**
     * 系统语言 zh / en
     */
    public static String language;
    public static Map<String , Object> objectLanguage = null;
    public static Map<String , Object> objectDataBase = null;
    public static Map<String , Object> objectConfig = null;


    public static String MysqlIp = null;
    public static String MysqlRoot = null;
    public static String MysqlUsername = null;
    public static String MysqlPassword = null;
    public static String MysqlPort = null;
    public static String MysqlTable = null;



    /**
     * 初次或重载配置文件
     */
    public static void reload(){
        Plugin plugin = TiStars.getInstance();
        List<String> langs = Arrays.asList("en" , "zh");
        //创建现有的语言库
        try{
            Yaml yml = new Yaml();
            FileReader reader = new FileReader(
                    TiStars.getInstance().getDataFolder() + "/config.yml");
            //读取配置文件
            BufferedReader buffer = new BufferedReader(reader);
            Map<String , String> map = yml.load(buffer);
            language = map.get("language");
            //获取配置语言文件
            buffer.close();
            reader.close();
        } catch (Exception ignored) { }
        if (language == null || !langs.contains(language)){
            //如果拥有语言库里不包含系统语言则尝试使用系统语言
            language = Locale.getDefault().getLanguage();
            //获取系统语言
            if (language == null || !langs.contains(language)){
                //如果还是没有，则使用英语
                language = "en";
                //设置配置文件语言
            }
        }
        TiStars.getInstance().getServer().getConsoleSender()
                .sendMessage(ChatColor.GREEN + "[" + plugin.getName() +
                        "]Current language: " + language);
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
                TiStars.getInstance().getServer().getConsoleSender()
                        .sendMessage(ChatColor.GREEN + "[" + plugin.getName() +
                                "]Discovery profile directory！" + eachFolder);
                //如果发现已有配置文件目录
            }
            //创建文件目录
        }
        /*
        如果需要添加新的配置文件或文件请看这里
         */
        readWrite.saveConfiguration("" , "config.yml");
        readWrite.saveConfiguration("" , "language.yml");
        readWrite.saveConfiguration("" , "dataBase.yml");
        //检查配置文件
        //读取String类配置文件
        objectConfig = readWrite.getObject("config.yml");
        //读取config配置
        objectLanguage = readWrite.getObject("language.yml");
        //读取language配置
        objectDataBase = readWrite.getObject("dataBase.yml");
        //读取dataBase配置








    }





}
