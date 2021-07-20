package config;

import io.github.lamzier.tistars.TiStars;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 读写配置文件
 */
public class readWrite{

    /**
     * 从默认文件库里提取配置文件，文件请用 utf-8 编码 ,此方法会检查文件是否存在和版本是够更新
     * @param path String 文件路径,如果在resources目录下则为空(不能为null)，如果在子目录则最右边需要 "/"
     * @param name String 文件名称,***.yml , 不是 zh_***.yml ,请确保resources至少拥有en语言的文件
     * @return boolean 成功与否
     */
    public static boolean saveConfiguration(String path , String name){
        //读取配置文件版本
        Yaml yml = new Yaml();
        try {
            BufferedReader jarBuffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(TiStars.getInstance().getClass().getClassLoader().getResourceAsStream(path + reconfig.language + "_" + name)), StandardCharsets.UTF_8));
            //读取jar包文件
            BufferedReader locaBuffer;
            try {
                locaBuffer = new BufferedReader(
                        new FileReader(
                                TiStars.getInstance().getDataFolder().toString()
                                        .replaceAll("\\\\","/")
                                        + "/" + path + name
                        )
                );
                Map<String , Double> locaMap = yml.load(locaBuffer);
                Map<String , Double> jarMap = yml.load(jarBuffer);
                if (jarMap.get("version") != null && locaMap.get("version") != null && jarMap.get("version").doubleValue() == locaMap.get("version").doubleValue()){
                    jarBuffer.close();
                    locaBuffer.close();
                    return true;
                }
            } catch (Exception e) {
                locaBuffer = null;
            }
            //读取本地配置文件,判断版本
            //版本不一样，替换或更新配置文件
            StringBuilder input = new StringBuilder();
            String line;
            //重读jar包文件
            jarBuffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(TiStars.getInstance().getClass().getClassLoader().getResourceAsStream(path + reconfig.language + "_" + name)), StandardCharsets.UTF_8));
            while (true){
                try {
                    if ((line = jarBuffer.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                input.append(line).append("\r\n");
            }
            //写入文件
            FileWriter writer = new FileWriter(
                    TiStars.getInstance().getDataFolder().toString()
                            .replaceAll("\\\\","/") + "/"
                            + path + name , false //不追加
            );
            //配置写入本地信息
            writer.write(String.valueOf(input));
            //写入配置文件
            writer.close();
            //关闭
            try { jarBuffer.close(); } catch (Exception ignored) { }
            try {
                assert locaBuffer != null;
                locaBuffer.close(); } catch (Exception ignored) { }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //读取本地配置文件
        return true;
    }

    /**
     * 读取 String类型配置文件
     * @param path String 文件路径，例如 config.yml 或 play/config.yml
     * @return Map<String,String> 返回价值 , *.get(键值)即可获得价值
     */
    public static Map<String, String> getString (String path){
        Yaml yml = new Yaml();
        Map<String , String> map;
        try {
            FileReader reader = new FileReader(TiStars.getInstance().getDataFolder() + "/" + path);
            BufferedReader buffer = new BufferedReader(reader);
            map = yml.load(buffer);
            buffer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    /**
     * 读取 Int类型配置文件，小数也可以
     * @param path String 文件路径，例如 config.yml 或 play/config.yml
     * @return Map<String,Integer> 返回价值 , *.get(键值)即可获得价值
     */
    public static Map <String , Integer> getInt (String path){
        Yaml yml = new Yaml();
        Map<String , Integer> map;
        try {
            FileReader reader = new FileReader(TiStars.getInstance().getDataFolder() + "/" + path);
            BufferedReader buffer = new BufferedReader(reader);
            map = yml.load(buffer);
            buffer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    /**
     * 读取 List<String> 类型配置文件
     * @param path String 文件路径，例如 config.yml 或 play/config.yml
     * @return Map<String,List<String>> 返回价值 , *.get(键值)即可获得价值
     */
    public static Map<String , List<String>> getStringList (String path){
        Yaml yml = new Yaml();
        Map<String , List<String>> map;
        try {
            FileReader reader = new FileReader(TiStars.getInstance().getDataFolder() + "/" + path);
            BufferedReader buffer = new BufferedReader(reader);
            map = yml.load(buffer);
            buffer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    /**
     * 读取 List<Integer> 类型配置文件，小数也可以
     * @param path String 文件路径，例如 config.yml 或 play/config.yml
     * @return Map<String,List<Integer>> 返回价值 , *.get(键值)即可获得价值
     */
    public static Map<String , List<Integer>> getIntList (String path){
        Yaml yml = new Yaml();
        Map<String , List<Integer>> map;
        try {
            FileReader reader = new FileReader(TiStars.getInstance().getDataFolder() + "/" + path);
            BufferedReader buffer = new BufferedReader(reader);
            map = yml.load(buffer);
            buffer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }



}
