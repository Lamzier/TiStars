package pro;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import start.reconfig;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class start {

    /**
     * pro是否成功
     */
    private static boolean pro = false;

    /**
     * pro版本验证开启
     */
    public static void star(){
        String username = reconfig.configAll[3].get("username").toString();
        String password = reconfig.configAll[3].get("password").toString();
        Plugin plugin = TiStars.getInstance();
        if (username == null || password == null || username.length() <= 5 || password.length() <= 5){
            //过于简单
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW
                    + "[" + plugin.getName() + "]" +
                    reconfig.configAll[1].get("onPro_failed").toString());
            pro = false;
            return;
        }
        //发送请求
        if(sendTcp("username=" +
                username + "&password=" + password).substring(0, 7)
                .equals("success")){
            //成功了
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN
                    + "[" + plugin.getName() + "]" +
                    reconfig.configAll[1].get("onPro_success").toString());
            pro = true;
        }else{
            //失败了
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW
                    + "[" + plugin.getName() + "]" +
                    reconfig.configAll[1].get("onPro_failed").toString());
            pro = false;
        }
    }

    /**
     * 发送tcp数据
     * @return 返回数据
     */
    public static StringBuffer sendTcp(String data){
        StringBuffer text = new StringBuffer();
        try {
            Socket socket = new Socket("82.156.81.89" , 19731);
            //创建socket实例 , 创建连接
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            out.write(data.getBytes(StandardCharsets.UTF_8));
            //发送数据
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            //读取
            {
                String line = null;
                while (true){
                    try{
                        if ((line = reader.readLine()) == null) break;
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    assert false;
                    text.append(line).append("\r\n");
                }
            }
            //读取数据
        } catch (IOException e) {
            e.printStackTrace();
            return new StringBuffer();
        }
        return text;
    }

    /**
     * 获取pro
     * @return 是否有pro
     */
    public static boolean getPro(){ return pro; }

}
