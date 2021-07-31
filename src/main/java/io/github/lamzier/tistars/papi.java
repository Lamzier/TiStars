package io.github.lamzier.tistars;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import spirit.start;
import start.reconfig;

import java.util.Map;

/**
 * 此类将自动注册为占位符扩展
 * 当包含该类的jar被添加到目录中时
 * {@code /plugins/PlaceholderAPI/expansions} 在您的服务器上.
 * <br>
 * <br>如果你在自己的插件中创建这样一个类，你必须
 * 在插件中手动注册它 {@code onEnable()} 通过使用
 * {@code new YourExpansionClass().register();}
 */
public class papi extends PlaceholderExpansion {
    /**
     * 除非我们
     * 我们需要确保依赖关系在服务器上
     * 让我们的占位符发挥作用！
     *
     * @return 总是正确的，因为我们没有任何依赖。
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * 创建这个扩展的人的名字应该在这里。
     *
     * @return 字符串形式的作者姓名。
     */
    @Override
    public @NotNull String getAuthor(){
        return "Lamzy";
    }

    /**
     * 占位符标识符应该在这里。
     * <br>这是告诉PlaceholderAPI调用我们的onRequest
     * 方法获取一个值，如果占位符以
     * 标识符。
     * <br>标识符必须是小写的，不能包含_或%
     *
     * @return 中的标识符 {@code %<identifier>_<value>%} 作为字符串.
     */
    @Override
    public @NotNull String getIdentifier(){
        return "tistars";
    }

    //%tistars.xxx%
    /**
     * 这就是这个资料片的版本。
     * <br>您不必使用数字，因为它被设置为字符串。
     *
     * @return 字符串形式的版本。
     */
    @Override
    public @NotNull String getVersion(){
        return "1.0.0";
    }

    /**
     * 这是当占位符带有我们的标识符时调用的方法
     * 找到并需要一个值。
     * <br>我们在这个方法中指定了值标识符。
     * <br>从2.9.1版本开始，你可以在你的请求中使用离线玩家。
     *
     * @param  player
     *         A {@link org.bukkit.OfflinePlayer OfflinePlayer}.
     * @param  identifier
     *         包含标识符/值的字符串。
     *
     * @return 请求的标识符可能为空字符串。
     */
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        //读取数据库
        String name = player.getName();
        Yaml yml = reconfig.getYml();
        switch (identifier) {
            case "spirit" :
                //获取精神值
                if ((boolean)reconfig.configAll[0].get("player_spirit")){
                    //开启了功能
                    Map<Object , Object> data2 = yml.load(start.data.get("data").toString()
                        .replace("=" , ": "));
                    //获取data
                    Map<Object , Object> play = yml.load(data2.get(name).toString()
                        .replace("=" , ": "));
                    //获取play
                    double p = (double)((int)play.get("spirit") * 10000 / (int)reconfig.configAll[2].get("spirit"));
                    return p/100 + " %";
                    //返回精神百分比
                }else {
                    //没开启
                    return "-1%";
                }
            case "digvigour" :
                //获取疲劳值
                break;
            default:
                break;
        }
        return null;
    }
}
