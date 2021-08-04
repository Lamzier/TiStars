package dareRoom;

import io.github.lamzier.tistars.TiStars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.scheduler.BukkitRunnable;
import start.reconfig;

/**
 * 副本房间控制
 */
public class room {


    /**
     * 等待正在玩家
     */
    public static List<String> players = new ArrayList<>();
    /**
     * 正在游戏中的所有玩家
     */
    public static List<String> playersPlaying = new ArrayList<>();
    /**
     * 读取空闲世界房间
     */
    public static List<World> idleWorld = new ArrayList<>();
    /**
     * 读取占用世界房展
     */
    public static List<World> occupyWorld = new ArrayList<>();
    /**
     * 玩家加入房间控制
     */
    public static void join(String name , CommandSender sender){
        //添加玩家
        Plugin plugin = TiStars.getInstance();
        //检查是否为合法玩家
        if (playersPlaying.contains(name)){
            //正在游戏中的玩家
            sender.sendMessage(reconfig.configAll[1].get("dareRoom_playerPlaying").toString()
                    .replace("/n" , "\n"));
            return;
        }
        //检查房间人数， 检查是否有空余房间
        if (idleWorld.size() == 0){
            //没有空闲房间
            sender.sendMessage(reconfig.configAll[1].get("dareRoom_notRoom").toString()
                .replace("/n" , "\n"));
            return;
        }else if (players.contains(name)){
            //如果已经有这名玩家
            sender.sendMessage(reconfig.configAll[1].get("dareRoom_inRoom").toString()
                    .replace("/n" , "\n"));
            return;
        }
        players.add(name);
        //加入房间，判断房间人数
        int minPlayers = (int) reconfig.configAll[4].get("playerMin");
        //最低开启数量
        int maxPlayers = (int) reconfig.configAll[4].get("playerMax");
        //房间最大人数
        new BukkitRunnable(){
            @Override
            public void run() {
                //提醒玩家
                for (String sendname : players){
                    Player player =  plugin.getServer().getPlayerExact(sendname);
                    if (player != null){
                        player.sendMessage(reconfig
                                .configAll[1].get("dareRoom_nowPlayer").toString()
                                .replace("/n" , "\n")
                                .replace("$now" , players.size() + "")
                                .replace("$min" , minPlayers + "")
                                .replace("$max" , maxPlayers + "")
                                .replace("$player" , name));
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
        if (players.size() >= minPlayers){
            //人数充足
            preparGame();
        }
    }

    /**
     * 剩余时间
     * 房间是否准备开启状态
     */
    private static int nowtime = -1;
    private static boolean open = false;
    /**
     * 预备房间
     */
    public static void preparGame(){
        if (open){ return; } //房间已经开启，则不执行
        open = true;
        //设置房间为开启
        nowtime = (int) reconfig.configAll[4].get("waitTime");
        if (nowtime <= 0){
            //如果 -1 则立即开启游戏
            startGame();
            return;
        }
        Plugin plugin = TiStars.getInstance();
        new BukkitRunnable(){
            @Override
            public void run() {
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        for (String name : players){
                            //检查玩家存在
                            Player player = plugin.getServer().getPlayerExact(name);
                            if (player != null){
                                player.sendMessage(
                                        reconfig.configAll[1].get("dareRoom_waitRoom").toString()
                                                .replace("/n" , "\n")
                                                .replace("$time" , nowtime + "")
                                );
                            }
                        }
                    }
                }.runTaskAsynchronously(plugin);
                //发送提示到每个玩家
                nowtime -= 1;
                //执行循环，时间-1
                if (nowtime <= 0 || players.size() == (int)reconfig.configAll[4].get("playerMax")){
                    //开始游戏 或者满人
                    startGame();
                    cancel();
                }else if (players.size() < (int)reconfig.configAll[4].get("playerMin")){
                    //玩家人数不够 ， 可能是中途退出
                    cancel();
                    open = false;
                    //关闭房间
                }
            }
        }.runTaskTimerAsynchronously(plugin , 20 , 20);
    }

    /**
     * 玩家请求离开房间
     * @param name 离开的玩家名
     * @param sender 输入指令的人
     */
    public static void leave(String name , CommandSender sender){
        if (playersPlaying.contains(name)){
            //这人正在游戏中
            sender.sendMessage(reconfig.configAll[1].get("dareRoom_playerPlaying")
                    .toString().replace("/n" , "\n"));
            return;
        }
        if (players.contains(name)){
            //包括该人
            new BukkitRunnable(){
                @Override
                public void run() {
                    for (String sendname : players){
                        Player player = TiStars.getInstance().getServer().getPlayerExact(sendname);
                        if (player != null){
                            //安全检查
                            player.sendMessage(reconfig.configAll[1].get("dareRoom_leaveRoom_success")
                                    .toString().replace("/n" , "\n")
                                    .replace("$player" , name));
                        }
                    }
                }
            }.runTaskAsynchronously(TiStars.getInstance());
            players.remove(name);
            //删除这个玩家 , 并且发送语句
        }else {
            //不包括这个人
            sender.sendMessage(reconfig.configAll[1].get("dareRoom_leaveRoom_failed")
                    .toString().replace("/n" , "\n")
                    .replace("$player" , name));
            //告诉这个人失败了
        }
    }

    /**
     * 开始游戏
     */
    public static void startGame(){
        List<String> playerss = players;
        //定义临时玩家
        playersPlaying.addAll(playerss);
        //添加游戏中玩家
        players = new ArrayList<>();
        //初始化玩家等待列表
        World world = idleWorld.get((int)(Math.random() * idleWorld.size()));
        //获取随机可用世界
        idleWorld.remove(world);
        //移除空闲世界
        occupyWorld.add(world);
        //添加占用世界
        List<Map<Object, Object>> xyz = (List<Map<Object, Object>>) start.getWorldsMap().get(world.getName());
        //获取世界坐标
        open = false;
        //关闭等待大厅
        Plugin plugin = TiStars.getPlugin();
        new BukkitRunnable(){
            @Override
            public void run() {
                for (String name : playerss){
                    Player player = plugin.getServer().getPlayerExact(name);
                    if (player != null){
                        //安全性
                        player.sendMessage(reconfig.configAll[1].get("dareRoom_starGame")
                                .toString().replace("/n" , "\n"));
                        //发送提示语
                        Map<Object , Object> linshixyz = xyz.get((int)(Math.random() * xyz.size()));
                        //获取随机坐标
                        Location l = new Location(world , (int) linshixyz.get("x"), (int) linshixyz.get("y") , (int) linshixyz.get("z"));
                        Bukkit.getScheduler().runTask(plugin , ()-> {
                            //转同步执行
                            player.teleport(l);
                        });
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
        //判断是否允许玩家移动
        int move = (int)reconfig.configAll[4].get("starWaitTime");
        if (move > -1){
            //需要控制玩家移动
            new BukkitRunnable(){
                @Override
                public void run() {
                    //添加玩家到禁止移动列表
                    dareRoom.move.name.addAll(playerss);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            //时间结束时
                            dareRoom.move.name.removeAll(playerss);
                            //删除玩家到禁止移动列表
                            for (String xx : playerss){
                                Player xxx = plugin.getServer().getPlayerExact(xx);
                                if (xxx != null){
                                    //安全性
                                    xxx.sendMessage(reconfig.configAll[1].get("dareroom_endWaitPlayer")
                                            .toString().replace("/n" , "\n"));
                                }
                            }
                            //通知所有玩家
                        }
                    }.runTaskLaterAsynchronously(plugin , move * 20L);
                }
            }.runTaskAsynchronously(plugin);
        }
        //判断是否等待房间关闭
        int time = (int)reconfig.configAll[4].get("time");
        if (time > -1){
            //定时关闭房间关闭房间
            new BukkitRunnable(){
                @Override
                public void run() {
                    List<Player> player = world.getPlayers();
                    //获取世界所有在线玩家
                    Map<Object , Object> linshi = start.getSpawnWorld();
                    Location l = new Location (start.getSpawnWorldw() , (int)linshi.get("x")
                            , (int)linshi.get("y") , (int)linshi.get("z"));
                    for (Player p : player){
                        Bukkit.getScheduler().runTask(plugin , ()-> {
                            p.teleport(l);
                            //使玩家返回重生点
                        });
                        p.sendMessage(reconfig.configAll[1].get("dareRoom_timeOut").toString()
                                .replace("/n" , "\n"));
                        //发送提醒消息
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                playersPlaying.remove(p.getName());
                                //3秒后删除玩家游玩状态
                            }
                        }.runTaskLaterAsynchronously(plugin , 60);
                    }
                }
            }.runTaskLaterAsynchronously(plugin , time * 20L);
            //time 秒后执行
        }
        checkWorld(world);
        //循环检查世界人数
    }

    /**
     * 检查世界玩家，随时关闭世界
     * @param w 检查的世界
     */
    public static void checkWorld(World w){
        new BukkitRunnable(){
            @Override
            public void run() {
                List<Player> player = w.getPlayers();
                if (player.size() == 0){
                    //这个世界已经没有玩家了,关闭！
                    occupyWorld.remove(w);
                    //删除这个世界的占用
                    idleWorld.add(w);
                    //添加到空闲列表
                    TiStars.getInstance().getServer().broadcastMessage(reconfig.configAll[1]
                            .get("dareRoom_spare").toString().replace("/n" , "\n")
                            .replace("$world" , w.getName()));
                    //公告众人
                    cancel();
                    //结束此次循环
                }
            }
        }.runTaskTimerAsynchronously(TiStars.getPlugin() , 1200 , 200 );
        //10秒检查一次
    }
}
