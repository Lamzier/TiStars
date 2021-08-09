package start;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 此处存储配置文件信息
 */
public class reconfig {

    public static String language = null;
    //插件语言
    /**
     * 配置文件存放地址
     * 0 --- config.yml
     * 1 --- language.yml
     * 2 --- spirit.yml
     * 3 --- pro.yml
     * 4 --- dareRoom.yml
     * 5 --- digVigour.yml
     */
    public static Map[] configAll = new Map[6];
    //读取到的配置文件
    private static Yaml yml = new Yaml();

    /**
     * 读入配置文件
     */
    public static boolean reconfig(){
        Plugin plugin = TiStars.getInstance();
        //获取主类
        extendedFile.folder();
        //检查目录
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        //无标点符号
        //dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.SINGLE_QUOTED);
        //单引号
        //dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
        //双引号
        dumperOptions.setPrettyFlow(false);
        yml = new Yaml(dumperOptions);
        //定义yml格式
        try {
            Map<Object , Object> jarMap = yml.load(new InputStreamReader(
                    Objects.requireNonNull(TiStars.getInstance().getClass().getClassLoader()
                            .getResourceAsStream("config.yml")), StandardCharsets.UTF_8
            ));
            //读取jar config文件
            Double version = (Double)jarMap.get("version");
            File file = new File(TiStars.getPlugin().getDataFolder() + //文件目录 plugins\TiStars
                    "/config.yml");
            Map<Object , Object> map = null;
            if (file.exists()){
                //如果文件存在
                map = yml.load(new FileReader(file));
            }
            //读取config文件
            if (map == null || map.get("version") == null || !map.get("version").equals(version) || map.get("language") == null){
                //版本不对或者语言文件不存在
                language = Locale.getDefault().getLanguage();
                //获取系统语言，并且写入文件
                try {
                    yml.dump(jarMap , new FileWriter(file , false));
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                //写入文件
            }else {
                //语言文件存在
                language = map.get("language").toString();
            }
            //完成获取语言
            List<String> langs = Arrays.asList("en" , "zh");
            //定义现有语言库
            if (language == null || !langs.contains(language)){
                language = langs.get(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //定义语言
        try {
            configAll[0] = yml.load(new FileReader(
                    TiStars.getPlugin().getDataFolder() + //文件目录 plugins\TiStars
                            "/config.yml"
            ));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        //读取config文件
        try {
            Map<Object , Object> jarMap = yml.load(new InputStreamReader(
                    Objects.requireNonNull(TiStars.getInstance().getClass().getClassLoader()
                            .getResourceAsStream("language.yml")), StandardCharsets.UTF_8
            ));
            //获取jarMap
            Double version = (Double)jarMap.get("al_version");
            //获取官方版本
            File file = new File (TiStars.getPlugin().getDataFolder() + //文件目录 plugins\TiStars
                    "/language.yml");
            Map<Object , Object> locMap = null;
            if (file.exists()){
                //如果文件存在
                locMap = yml.load(new FileReader(
                        TiStars.getPlugin().getDataFolder() + //文件目录 plugins\TiStars
                                "/language.yml"
                ));
            }
            //读取本地 language
            if (locMap == null || locMap.get("version") == null || !locMap.get("version").equals(version) || !locMap.get("language").equals(language)){
                //版本不对或者语言不对,重写
                Map<Object , Object> map = new LinkedHashMap<>();
                for (Map.Entry<Object, Object> entry: jarMap.entrySet()){
                    //遍历数组
                    String key = entry.getKey().toString();
                    if (key.startsWith(language + "_") || key.startsWith("al_")){
                        //添加map
                        map.put(key.substring(3) , entry.getValue());
                    }
                }
                map.put("language" , language);
                try {
                    yml.dump(map , new FileWriter(TiStars.getPlugin().getDataFolder() +
                            "/language.yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                //写入文件
            }//完成language检查
            configAll[1] = yml.load(new FileReader(TiStars.getPlugin().getDataFolder() +
                    "/language.yml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //读取language文件
        plugin.getServer().getConsoleSender().sendMessage(
                ChatColor.GREEN + "[" + plugin.getName() + "]" +
                        configAll[1].get("onEnable_language")
        );
        //提示语
        extendedFile.Yaml();
        //检查配置文件
        extendedFile.ymlRead();
        //读取 扩展 配置文件



        //完成检查配置文件
        return true;
    }

    /**
     * 检查文件夹
     * @param path 路径 例如 playerData 或 playerData/Spirit
     */
    public static void checkFolder(String path){
        File file = new File(TiStars.getPlugin().getDataFolder() + "/" + path);
        if (!file.exists()){
            //如果不存在则新建
            if (file.mkdirs()){
                //建立文件成功
                Plugin plugin = TiStars.getInstance();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN
                        + "[" + plugin.getName() + "]" +
                        "Folder is not existent " + file
                );
                //向后台发送提示语
            }
        }
    }

    /**
     * 检查配置文件
     * @param path 路径例如 dataBase.yml 或者 playerData/Spirit.yml
     */
    public static void checkYaml(String path){
        Plugin plugin = TiStars.getPlugin();
        //获得主类
        Map<Object , Object> jarMap = yml.load(new InputStreamReader(
                Objects.requireNonNull(TiStars.getInstance().getClass().getClassLoader()
                        .getResourceAsStream(path)), StandardCharsets.UTF_8
        ));
        //读取jar文件
        Double version = (Double)jarMap.get("version");
        //获得官方版本
        try {
            File file = new File(plugin.getDataFolder() + "/" + path);
            Map<Object ,Object> locMap = null;
            if (file.exists()){
                //如果文件存在
                locMap = yml.load(new FileReader(file));
            }
            //读取本地文件
            if (locMap == null || locMap.get("version") == null || !locMap.get("version").equals(version)){
                //如果文件不存在，没有版本号,版本号不对
                //重写文件
                try {
                    yml.dump(jarMap , new FileWriter(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //完成检查
    }

    /**
     * 获取yml
     * @return 返回yml
     */
    public static Yaml getYml(){
        //获取yml
        return yml;
    }


}
