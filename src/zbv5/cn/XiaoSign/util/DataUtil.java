package zbv5.cn.XiaoSign.util;

import java.util.HashMap;

import cn.nukkit.Player;
import zbv5.cn.XiaoSign.lang.Lang;
import zbv5.cn.XiaoSign.store.Mysql;
import zbv5.cn.XiaoSign.store.Yml;
import zbv5.cn.XiaoSign.util.FileUtil;
public class DataUtil
{
    public static String Store = "Yml";

    public static HashMap<String, Integer> total_all = new HashMap<String, Integer>();
    public static HashMap<String, Integer> total_month = new HashMap<String, Integer>();
    public static HashMap<String, Integer> total_week = new HashMap<String, Integer>();
    public static HashMap<String, String> date_sign = new HashMap<String, String>();
    public static HashMap<String, String> today = new HashMap<String, String>();

    public static void CheckDataStore()
    {
        if(FileUtil.config.getString("Store").equals("Yml"))
        {
            Store = "Yml";
            PrintUtil.PrintConsole("&e储存方式: &6"+Store);
            Yml.createData();
            return;
        }
        if(FileUtil.config.getString("Store").equals("Mysql"))
        {
            Store = "Mysql";
            PrintUtil.PrintConsole("&e储存方式: &6"+Store);
            Mysql.createTable();
            return;
        }
        PrintUtil.PrintConsole("&c储存方式异常,请检查配置文件Store配置.");
    }

    public static void getPlayerDateCache(Player p)
    {
        if(Store.equals("Yml"))
        {
            Yml.getPlayerData(p);
        }
        if(Store.equals("Mysql"))
        {
            Mysql.getPlayerData(p);
        }
    }
    // b为补签预留 1.0.0不进行开发
    public static void setPlayerSign(Player p,boolean b)
    {
        if(!b)
        {
            if(getPlayerDate(p,"info","sign").equals("NotSign"))
            {
                if(Store.equals("Yml"))
                {
                    Yml.PlayerSign(p);
                }
                if(Store.equals("Mysql"))
                {
                    Mysql.PlayerSign(p);
                }
                RewardUtil.SendReward(p);
            }
        }
    }

    public static String getPlayerDate(Player p,String type,String info)
    {
        if(!total_all.containsKey(p.getName()))
        {
            getPlayerDateCache(p);
        }
        if(type.equals("date"))
        {
            if(info.equals("sign"))
            {
                return date_sign.get(p.getName());
            }
        }
        if(type.equals("total"))
        {
            if(info.equals("all"))
            {
                return Integer.toString(total_all.get(p.getName()));
            }
            if(info.equals("month"))
            {
                return Integer.toString(total_month.get(p.getName()));
            }
            if(info.equals("week"))
            {
                return Integer.toString(total_week.get(p.getName()));
            }
        }
        if(type.equals("info"))
        {
            if(info.equals("sign"))
            {
                return today.get(p.getName());
            }
        }
        return "null";
    }
    public static void setCache(Player p, int all, int month, int week ,String date,String sign)
    {
        total_all.put(p.getName(),all);
        total_month.put(p.getName(),month);
        total_week.put(p.getName(),week);
        date_sign.put(p.getName(),date);
        today.put(p.getName(),sign);
    }

    public static void removeCache(Player p)
    {
        total_all.remove(p.getName());
        total_month.remove(p.getName());
        total_week.remove(p.getName());
        date_sign.remove(p.getName());
        today.remove(p.getName());
    }

    public static String getPlayerStatus(Player p,Boolean ui)
    {
        if(!total_all.containsKey(p.getName()))
        {
            getPlayerDateCache(p);
        }
        if(getPlayerDate(p,"info","sign").equals("NotSign"))
        {
            if(ui)
            {
                return Lang.Sign_Ui_NotSign;
            } else {
                return Lang.Sign_NotSign;
            }
        }
        if(getPlayerDate(p,"info","sign").equals("AlreadySign"))
        {
            if(ui)
            {
                return Lang.Sign_Ui_AlreadySign;
            } else {
                return Lang.Sign_AlreadySign;
            }
        }
        return "null";
    }
}
