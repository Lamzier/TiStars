package mysql;

import config.reconfig;

import java.sql.*;

/**
 * 数据库控制
 */
public class mysql {

    public static Connection con = null;

    /**
     * 数据库前准备
     * @return boolean 成功与否
     */
    public static boolean starMysql (){
        //启动mysql , 对环境进行检查
        try {
            con.close();
            //断开当前数据库连接
        } catch (Exception ignored) { }
        //读取数据库信息
        reconfig.MysqlIp = reconfig.objectDataBase.get("MysqlIp").toString();
        reconfig.MysqlRoot = reconfig.objectDataBase.get("MysqlRoot").toString();
        reconfig.MysqlUsername = reconfig.objectDataBase.get("MysqlUsername").toString();
        reconfig.MysqlPassword = reconfig.objectDataBase.get("MysqlPassword").toString();
        reconfig.MysqlPort = reconfig.objectDataBase.get("MysqlPort").toString();
        reconfig.MysqlTable = reconfig.objectDataBase.get("MysqlTable").toString();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + reconfig.MysqlIp + ":" +
                            reconfig.MysqlPort +"/" + reconfig.MysqlUsername,
                    reconfig.MysqlRoot,
                    reconfig.MysqlPassword);
            //链接本地MYSQL
            Statement stmt = con.createStatement();
            //实例化stat
            try {
                ResultSet rs = stmt.executeQuery("SELECT * FROM `" + reconfig.MysqlTable + "`;");
                //查看读取表格
                if (rs.getMetaData().getColumnCount() != 6){
                    //数据库表格已经更新了
                    String sql = "DROP TABLE IF EXISTS `" + reconfig.MysqlTable + "`";
                    stmt.executeUpdate(sql);
                    //删除表格
                }
            } catch (Exception ignored) {
                //表格不存在
                String sql = "DROP TABLE IF EXISTS `" + reconfig.MysqlTable + "`";
                stmt.executeUpdate(sql);
                //删除表格
            }
            String sql = "CREATE TABLE IF NOT EXISTS `" + reconfig.MysqlTable + "`(\r\n" +
                    "   `id` INT UNSIGNED AUTO_INCREMENT, #id排列符索引\r\n" +
                    "   `username` VARCHAR(64) NOT NULL, #用户名\r\n" +
                    "   `playerOnline` BOOLEAN NOT NULL, #玩家是否在线\r\n" +
                    "   `playerSleeping` BOOLEAN NOT NULL, #玩家是否在睡觉\r\n" +
                    "   `digVigour` INT NOT NULL, #活力值\r\n" +
                    "   `playerSpirit` INT NOT NULL, #精神值\r\n" +
                    "   PRIMARY KEY (`id`)\r\n" +
                    ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt.executeUpdate(sql);
            //创建表
            //不关闭数据库，提供全局使用
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }





}
