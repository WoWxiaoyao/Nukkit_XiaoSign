package zbv5.cn.XiaoSign.util;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import zbv5.cn.XiaoSign.Main;
import zbv5.cn.XiaoSign.lang.Lang;

import java.util.List;

public class PrintUtil
{
    public static void PrintConsole(String s)
    {
        Main.getInstance().getServer().getConsoleSender().sendMessage(cc(s));
    }

    public static void PrintPlayer(Player p, String s)
    {
        p.sendMessage(cc(s));
    }
    public static void PrintBroadcast(String s)
    {
        Main.getInstance().getServer().broadcastMessage(cc(s));
    }

    public static String cc(String s)
    {
        s = s.replace("{prefix}", Lang.Prefix);
        s = TextFormat.colorize('&', s);
        return s;
    }

    public static void PrintCommandSender(CommandSender sender, String s)
    {
        if(!s.equalsIgnoreCase("none"))
        {
            sender.sendMessage(cc(s));
        }
    }
    public static String HookVariable(Player p,String s)
    {
        s = s.replace("{player}",p.getName());
        if((Main.PlaceholderAPI) && (FileUtil.config.getBoolean("PlaceholderAPI")))
        {
            s = PlaceholderAPI.getInstance().translateString(s,p);
        }
        s = s.replace("{info}",DataUtil.getPlayerStatus(p,false));
        s = s.replace("{ui_info}",DataUtil.getPlayerStatus(p,true));
        s = s.replace("{today}",DateUtil.getDate(Lang.Sign_TodayFormat));

        if(RewardUtil.getRewardIntroduction(p) != null)
        {
            s = s.replace("{introduction}",RewardUtil.getRewardIntroduction(p));
        }

        if(RewardUtil.getRewardName(p) != null)
        {
            s = s.replace("{reward_name}",RewardUtil.getRewardName(p));
        }

        s = s.replace("{prefix}",Lang.Prefix);
        s = s.replace("{date}",DataUtil.getPlayerDate(p,"date","sign"));
        s = s.replace("{total_all}",DataUtil.getPlayerDate(p,"total","all"));
        s = s.replace("{total_month}",DataUtil.getPlayerDate(p,"total","month"));
        s = s.replace("{total_week}",DataUtil.getPlayerDate(p,"total","week"));
        s = cc(s);
        return s;
    }
}
