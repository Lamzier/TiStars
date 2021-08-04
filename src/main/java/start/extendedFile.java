package start;

import io.github.lamzier.tistars.TiStars;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * 此处存放扩展的配置存储文件
 */
public class extendedFile {

    /**
     * 扩展文件夹
     */
    public static void folder(){
        reconfig.checkFolder("");
        reconfig.checkFolder("playerData");
    }

    /**
     * 扩展配置文件,config.yml,language.yml默认存在，不用检查
     */
    public static void Yaml(){
        reconfig.checkYaml("dataBase.yml");
        reconfig.checkYaml("spirit.yml");
        reconfig.checkYaml("pro.yml");
        reconfig.checkYaml("dareRoom.yml");
    }

    /**
     * 读取扩展配置文件
     */
    public static void ymlRead(){
        try {
            reconfig.configAll[2] = reconfig.getYml().load(new FileReader(TiStars.getPlugin().getDataFolder() + //文件目录 plugins\TiStars
                    "/spirit.yml"));
            //读取spirit配置文件
            reconfig.configAll[3] = reconfig.getYml().load(new FileReader(TiStars.getPlugin().getDataFolder() + //文件目录 plugins\TiStars
                    "/pro.yml"));
            //读取pro配置文件
            reconfig.configAll[4] = reconfig.getYml().load(new FileReader(TiStars.getPlugin().getDataFolder() + //文件目录 plugins\TiStars
                    "/dareRoom.yml"));
            //读取pro配置文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
