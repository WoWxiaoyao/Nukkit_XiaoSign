package zbv5.cn.XiaoSign;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import zbv5.cn.XiaoSign.gui.SignGui;
import zbv5.cn.XiaoSign.lang.Lang;
import zbv5.cn.XiaoSign.listener.PlayerListener;
import zbv5.cn.XiaoSign.util.DataUtil;
import zbv5.cn.XiaoSign.util.DateUtil;
import zbv5.cn.XiaoSign.util.FileUtil;
import zbv5.cn.XiaoSign.util.PrintUtil;

public class Main extends PluginBase implements Listener
{
    private static Main instance;
    public static boolean PlaceholderAPI = false;
    public static Main getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        PrintUtil.PrintConsole("&e======== &bXiaoSign &e> &d开始加载 &e========");
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            PlaceholderAPI = true;
            PrintUtil.PrintConsole("&a检测到PlaceholderAPI插件.");
        }else {
            PrintUtil.PrintConsole("&c未检测到PlaceholderAPI插件,大量变量失效.");
        }
        FileUtil.LoadFile();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        //注入计时器 用于00:00缓存清除

        this.getServer().getScheduler().scheduleRepeatingTask(this, new Runnable()
        {
            public void run()
            {
                if(DateUtil.getDate("HH:mm:ss").equals("00:00:00"))
                {
                    for(Player p:getServer().getOnlinePlayers().values())
                    {
                        DataUtil.removeCache(p);
                        PrintUtil.PrintConsole("Remove "+p.getName());
                    }
                }
            }
        }, 20, true);

        PrintUtil.PrintConsole("&e======== &bXiaoSign &e> &a加载成功 &e========");
    }

    @Override
    public void onDisable()
    {
        PrintUtil.PrintConsole("&c插件卸载");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getLabel().equalsIgnoreCase("XiaoSign"))
        {
            if (args.length == 0)
            {
                if(sender instanceof Player)
                {
                    if(!sender.hasPermission("XiaoSign.commands.open"))
                    {
                        PrintUtil.PrintCommandSender(sender,true, Lang.NoPermission);
                        return false;
                    }
                    Player p = (Player)sender;
                    SignGui.openGui(p);
                } else {
                    PrintUtil.PrintCommandSender(sender,false,"&6========= [&bXiaoSign&6] =========");
                    PrintUtil.PrintCommandSender(sender,false,"&6/XiaoSign &7- &b打开签到页面&7(仅支持玩家)");
                    PrintUtil.PrintCommandSender(sender,false,"&6/XiaoSign &areload &7- &c重载配置文件");
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("reload"))
            {
                if(!sender.hasPermission("XiaoSign.commands.reload"))
                {
                    PrintUtil.PrintCommandSender(sender,true, Lang.NoPermission);
                    return false;
                }
                try
                {
                    FileUtil.LoadFile();
                    PrintUtil.PrintCommandSender(sender,true,Lang.SuccessReload);
                    return true;
                } catch (Exception e)
                {
                    PrintUtil.PrintCommandSender(sender,true,Lang.FailReload);
                    e.printStackTrace();
                }
                return false;
            }
            PrintUtil.PrintCommandSender(sender,true, Lang.NullCommand);
            return false;
        }
        return false;
    }
}
