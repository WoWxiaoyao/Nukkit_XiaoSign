package zbv5.cn.XiaoSign.util;

import cn.nukkit.command.CommandSender;
import zbv5.cn.XiaoSign.Main;
import zbv5.cn.XiaoSign.command.SignCommand;
import zbv5.cn.XiaoSign.lang.Lang;
import zbv5.cn.XiaoSign.listener.PlayerListener;
import zbv5.cn.XiaoSign.store.Mysql;
import zbv5.cn.XiaoSign.store.Yml;

public class PluginUtil
{
    public static void LoadPlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoSign &e> &d开始加载 &e========");
        FileUtil.LoadFile();

        if(Main.getInstance().getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            Main.PlaceholderAPI = true;
            PrintUtil.PrintConsole("&a检测到PlaceholderAPI插件.");
            if(FileUtil.config.getBoolean("PlaceholderAPI"))
            {
                PrintUtil.PrintConsole("&a启用 PlaceholderAPI 变量.");
            } else {
                PrintUtil.PrintConsole("&c禁用 PlaceholderAPI 变量.");
            }
        }else {
            PrintUtil.PrintConsole("&c未检测到PlaceholderAPI插件,变量失效.");
        }

        if(FileUtil.config.getBoolean("Mysql.Use"))
        {
            DataUtil.useSql = true;
            if(!Mysql.createTable())
            {
                Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
                return;
            }
        } else {
            Yml.createData();
        }
        TaskUtil.run();
        Main.getInstance().getServer().getCommandMap().register("XiaoSign", new SignCommand(Main.getInstance()));
        Main.getInstance().getServer().getPluginManager().registerEvents(new PlayerListener(), Main.getInstance());
        PrintUtil.PrintConsole("&e======== &bXiaoSign &e> &a加载成功 &e========");
    }

    public static void DisablePlugin()
    {
        Main.getInstance().getServer().getScheduler().cancelTask(Main.getInstance());
        PrintUtil.PrintConsole("{prefix}&c插件卸载");
    }

    public static void reloadLoadPlugin(CommandSender sender)
    {
        try
        {
            FileUtil.LoadFile();
            PrintUtil.PrintCommandSender(sender,Lang.SuccessReload);
        }
        catch (Exception e)
        {
            PrintUtil.PrintCommandSender(sender, Lang.FailReload);
            e.printStackTrace();
        }
    }
}
