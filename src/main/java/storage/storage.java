package storage;

import config.readWrite;
import config.reconfig;
import mysql.mysql;

/**
 * 存储系统管理
 */
public class storage {

    /**
     * 检查所有玩家是否有基础数据
     * @return boolean 成功与否
     */
    public static boolean playerInServerFirst(){
        if ((boolean) reconfig.objectConfig.get("enable_MYSQL")){
            //使用MYSQL数据库
            mysql.starMysql();
            //检查数据库
        }else{
            //使用本地存储，检查玩家数据
            readWrite.checkData("playerData");
            //检查所有玩家数据
        }
        return true;
    }

}
