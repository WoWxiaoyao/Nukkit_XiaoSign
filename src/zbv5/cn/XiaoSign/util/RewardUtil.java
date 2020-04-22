package zbv5.cn.XiaoSign.util;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import zbv5.cn.XiaoSign.Main;
import zbv5.cn.XiaoSign.lang.Lang;

import java.util.ArrayList;
import java.util.List;

public class RewardUtil
{
    public static boolean Week_Reward = false;
    public static boolean Month_Reward = false;

    public static void LoadTotal()
    {
        if(FileUtil.reward.getBoolean("WeekReward.Enable"))
        {
            Week_Reward = true;
            PrintUtil.PrintConsole("&a启用周累计签到奖励.");
        } else {
            PrintUtil.PrintConsole("&c禁用周累计签到奖励.");
        }
        if(FileUtil.reward.getBoolean("MonthReward.Enable"))
        {
            Month_Reward = true;
            PrintUtil.PrintConsole("&a启用月累计签到奖励.");
        } else {
            PrintUtil.PrintConsole("&c禁用月累计签到奖励.");
        }
    }

    public static void SendReward(Player p)
    {
        List<String> RunList = getReward(p);
        if(!RunList.isEmpty())
        {
            Run(p,RunList);
        } else {
            PrintUtil.PrintPlayer(p, Lang.NullReward);
        }
        CheckTotalReward(p);
    }

    public static List<String> getReward(Player p)
    {
        List<String> RunList = new ArrayList<>();
        String RewardName = getRewardName(p);
        if(RewardName != null)
        {
            RunList = FileUtil.reward.getStringList("Reward."+RewardName+".run");
        }
        return RunList;
    }

    public static void CheckTotalReward(Player p)
    {
        if(Week_Reward)
        {
            ConfigSection c = FileUtil.reward.getSection("WeekReward.List");
            for (String day : c.getKeys(false))
            {
                int Need = FileUtil.reward.getInt("WeekReward.List."+day+".Need");
                if(Integer.parseInt(DataUtil.getPlayerDate(p,"total","week")) == Need)
                {
                    List<String> RunList = FileUtil.reward.getStringList("WeekReward.List."+day+".run");
                    if(!RunList.isEmpty())
                    {
                        Run(p,RunList);
                        break;
                    } else {
                        PrintUtil.PrintPlayer(p, Lang.NullReward);
                    }
                }
            }
        }
        if(Month_Reward)
        {
            ConfigSection c = FileUtil.reward.getSection("MonthReward.List");
            for (String day : c.getKeys(false))
            {
                int Need = FileUtil.reward.getInt("MonthReward.List."+day+".Need");
                if(Integer.parseInt(DataUtil.getPlayerDate(p,"total","month")) == Need)
                {
                    List<String> RunList = FileUtil.reward.getStringList("MonthReward.List."+day+".run");
                    if(!RunList.isEmpty())
                    {
                        Run(p,RunList);
                        break;
                    } else {
                        PrintUtil.PrintPlayer(p, Lang.NullReward);
                    }
                }
            }
        }
    }
    public static String getRewardIntroduction(Player p)
    {
        String RewardName = getRewardName(p);
        String Introduction = "null";
        if(RewardName != null)
        {
            Introduction = FileUtil.reward.getString("Reward."+RewardName+".Introduction");
        }
        return Introduction;
    }

    public static String getRewardName(Player p)
    {
        String name = null;
        int priority = 2147483647;
        try
        {
            ConfigSection c = FileUtil.reward.getSection("Reward");
            for (String reward : c.getKeys(false))
            {
                if(p.hasPermission("XiaoSign.Reward."+reward))
                {
                    ConfigSection cc = FileUtil.config.getSection("Reward."+reward);
                    if(cc.getInt("priority") < priority)
                    {
                        name = reward;
                    }
                }
            }
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("{prefix}&3reward.yml &c读取出现问题!");
            e.printStackTrace();
        }
        return name;
    }





    public static void Run(Player p, List<String> RunList)
    {
        for(String s:RunList)
        {
            s = PrintUtil.HookVariable(p,s);
            if(s.startsWith("[message]"))
            {
                s=s.replace("[message]", "");
                PrintUtil.PrintPlayer(p,s);
            }
            if(s.startsWith("[bc]"))
            {
                s=s.replace("[bc]", "");
                PrintUtil.PrintBroadcast(s);
            }
            if(s.startsWith("[console]"))
            {
                s=s.replace("[console]", "");
                Main.getInstance().getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(),s);
            }
            if(s.startsWith("[player]"))
            {
                s=s.replace("[player]", "");
                Main.getInstance().getServer().dispatchCommand(p, s);
            }
            if(s.startsWith("[title]"))
            {
                s=s.replace("[title]", "");
                try
                {
                    String[] ss = s.split(",");
                    if(ss.length == 1)
                    {
                        p.sendTitle(s);
                    }
                    if(ss.length == 2)
                    {
                        p.sendTitle(ss[0],ss[1]);
                    }
                    if(ss.length == 5)
                    {
                        p.sendTitle(ss[0],ss[1],Integer.parseInt(ss[2]),Integer.parseInt(ss[3]),Integer.parseInt(ss[4]));
                    }
                }
                catch (Exception e)
                {
                    PrintUtil.PrintConsole("{prefix}&3Run出现问题 &c执行Title出现问题");
                    e.printStackTrace();
                }
            }
            if(s.startsWith("[ActionBar]"))
            {
                s=s.replace("[ActionBar]", "");
                try
                {
                    String[] ss = s.split(",");
                    if(ss.length == 1)
                    {
                        p.sendActionBar(s);
                    }
                    if(ss.length == 4)
                    {
                        p.sendActionBar(ss[0],Integer.parseInt(ss[1]),Integer.parseInt(ss[2]),Integer.parseInt(ss[3]));
                    }
                }
                catch (Exception e)
            {
                    PrintUtil.PrintConsole("{prefix}&3Run出现问题 &c执行ActionBar出现问题");
                    e.printStackTrace();
                }
            }
            if(s.startsWith("[op]"))
            {
                boolean op = p.isOp();
                p.setOp(true);
                try
                {
                    s=s.replace("[op]", "");
                    Main.getInstance().getServer().dispatchCommand(p, s);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                try
                {
                    p.setOp(op);
                }
                catch (Exception e)
                {
                    PrintUtil.PrintConsole("{prefix}&3Run出现问题 &c执行OP指令操作出现问题");
                    e.printStackTrace();
                    p.setOp(false);
                }
            }
            if(s.startsWith("[chat]"))
            {
                s=s.replace("[chat]", "");
                p.chat(s);
            }
        }
    }
}
