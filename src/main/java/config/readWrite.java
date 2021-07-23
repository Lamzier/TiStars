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
     */
    public static void saveConfiguration(String path , String name){
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
                    return;
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
                    return;
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
        }
        //读取本地配置文件
    }

    /**
     * 读取 Object 类型配置文件
     * @param path String 文件路径，例如 config.yml 或 play/config.yml
     * @return Map<String,String> 返回价值 , *.get(键值)即可获得价值
     */
    public static Map<String , Object> getObject (String path){
        Yaml yml = new Yaml();
        Map<String , Object> map;
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
     * 检查所有玩家配置文件版本(checkData)
     * @param path String 路径名例如 playerData
     */
    public static void checkData(String path){
        BufferedReader jarBuffer = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(TiStars.getInstance().getClass().getClassLoader().getResourceAsStream(path + "/" + path + ".yml"))
                        , StandardCharsets.UTF_8)
        );
        //读取jar包 buffer
        Double version;
        {
            Yaml yml = new Yaml();
            Map<String, Object> linshi = yml.load(jarBuffer);
            version = (Double) linshi.get("version");
        }
        //获取当前最近版本号
        //获取文件夹下所有数据文件
        File[] tempList = new File( TiStars.getInstance().getDataFolder().toString()
                .replaceAll("\\\\","/") + "/" + path + "/").listFiles();
        //遍历所有文件
        Yaml yml = new Yaml();
        assert tempList != null;
        for (File fe: tempList){
            if (!fe.isFile()){
                //如果不是文件 ，是文件夹的话
                continue;
            }
            //读入文件
            try{
                BufferedReader locaBuffer = new BufferedReader(new FileReader(fe.toString()));
                //读入文件到 buffer
                Map<String , Object> map = null;
                try {
                    map = yml.load(locaBuffer);
                    locaBuffer.close();
                } catch (Exception e) {
                    //配置文件出错需要删掉
                    if(fe.delete())
                    continue;
                }
                //比较版本
                if (map == null || map.get("version") == null || !(map.get("version")).equals(version)){
                    //版本不一致，则删除
                    if(!fe.delete())
                        System.out.println("ERROR the yaml delete failled");
                }
            } catch (Exception ignored) { }
        }
    }

    /**
     * 设置玩家数据，会检查版本文件是否存在
     * @param path String 文件路径 例如 playerData
     * @param playername String 玩家名称 例如Lamzy
     * @param key String 键值 key
     * @param value Object 价值 value
     */
    public static void setObject(String path , String playername , String key , Object value){
        //检查文件是否存在
        Double version = null;
        StringBuilder jar = new StringBuilder();
        File file = new File(
                TiStars.getInstance().getDataFolder().toString()
                .replaceAll("\\\\" , "/") + "/" + path +
                        "/" + playername + ".yml"
        );
        //获取文件目录
        if (!file.exists()){
            //如果文件不存在 , 从jar包中获取
            BufferedReader jarReader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(TiStars.getInstance().getClass().getClassLoader().getResourceAsStream(path + "/" + path + ".yml"))
                    )
            );
            //获取jar包文件
            jar = new StringBuilder();
            {
                String line;
                while (true) {
                    try {
                        if ((line = jarReader.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    jar.append(line).append("\r\n");
                }
            }
            //关闭流
            try {
                jarReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            //获取文件版本
            Yaml yml = new Yaml();
            Map<String , Object> map = yml.load(jar.toString());
            version = (Double)map.get("version");
            //复制到文件
            try {
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(file.toString())
                );
                //获取写入文件
                writer.write(jar.toString());
                //写入文件
                writer.close();
                //关闭流
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }//之后文件存在
        //读取本地文件
        StringBuilder loca = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(file.toString())
            );
            //获取本地文件
            Yaml yml = new Yaml();
            {
                String line;
                while (true) {
                    try {
                        if ((line = reader.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    loca.append(line).append("\r\n");
                }
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                //关闭流
            }
            //读取文件
            Map<String , Object> map = yml.load(loca.toString());
            //读取配置
            //loca 文件内容，检查文件版本
            if (!map.get("version").equals(version)){
                //版本不一致,替换文件
                try {
                    BufferedWriter writer = new BufferedWriter(
                            new FileWriter(file.toString())
                    );
                     writer.write(jar.toString());
                     writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            map.put(key , value);
            //重新写入配置文件 , 先把配置文件写入loca
            loca = new StringBuilder();
            //初始化loca
            for (Map.Entry<String,Object> entry:map.entrySet()){
                //遍历数组
                loca.append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append("\r\n");
            }
            //写入loca
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        //写入本地文件
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file.toString())
            );
            //获取本地路径
            writer.write(loca.toString());
            //写入本地
            writer.close();
            //关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
