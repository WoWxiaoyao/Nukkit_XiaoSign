package zbv5.cn.XiaoSign.store;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import zbv5.cn.XiaoSign.Main;
import zbv5.cn.XiaoSign.lang.Lang;
import zbv5.cn.XiaoSign.util.DataUtil;
import zbv5.cn.XiaoSign.util.DateUtil;
import zbv5.cn.XiaoSign.util.PrintUtil;

import java.io.File;
import java.util.List;

public class Yml
{
    public static Config data;

    public static void createData()
    {
        File PlayerData = new File(Main.getInstance().getDataFolder(), "data.yml");
        if (!PlayerData.exists())
        {
            try {
                PlayerData.createNewFile();
            }
            catch (Exception e)
            {
                PrintUtil.PrintConsole("&3玩家数据 &c创建出现问题!");
                e.printStackTrace();
            }
        }
        data = new Config(new File(Main.getInstance().getDataFolder() + "/data.yml"));
        PrintUtil.PrintConsole("&3data.yml &a加载.");
    }

    public static void getPlayerData(Player p)
    {
        int all = 0;
        int month = 0;
        int week = 0;
        String sign = "NotSign";
        String date = Lang.NoTotal;
        if(data.getString(p.getName()+".date") != null)
        {
            all = data.getInt(p.getName()+".total.all");
            date = data.getString(p.getName()+".date");
            String[] dates = date.split("/");
            if(DateUtil.getDate("yyyy/MM/dd").equals(date))
            {
                sign = "AlreadySign";
            } else {
                sign = "NotSign";
            }
            //判断周
            List<String> WeekList = DateUtil.getWeekDate();
            if(WeekList.contains(date))
            {
                week = data.getInt(p.getName()+".total.week");
            }
            //年 月 判断
            if((DateUtil.getDate("yyyy").equals(dates[0])) && DateUtil.getDate("MM").equals(dates[1]))
            {
                month = data.getInt(p.getName()+".total.month");
            }

        }
        DataUtil.setCache(p,all,month,week,date,sign);
    }

    public static void PlayerSign(Player p)
    {
        File PlayerData = new File(Main.getInstance().getDataFolder(), "data.yml");

        try {
            if(data.getString(p.getName()+".date") == null)
            {
                data.set(p.getName()+".uuid", p.getUniqueId().toString());
                data.set(p.getName()+".total.all", 1);
                data.set(p.getName()+".total.month", 1);
                data.set(p.getName()+".total.week", 1);
                //日期
                data.set(p.getName()+".date", DateUtil.getDate("yyyy/MM/dd"));
            } else {
                String date = DataUtil.getPlayerDate(p,"date","sign");
                String[] dates = date.split("/");
                //总
                int new_all = Integer.parseInt(DataUtil.getPlayerDate(p,"total","all")) + 1;
                data.set(p.getName()+".total.all", new_all);
                //当天
                if(!DateUtil.getDate("yyyy/MM/dd").equals(date))
                {
                    data.set(p.getName()+".date", DateUtil.getDate("yyyy/MM/dd"));
                }
                //判断周
                List<String> WeekList = DateUtil.getWeekDate();
                if(WeekList.contains(DataUtil.getPlayerDate(p,"date","sign")))
                {
                    //防止补签大于当前所在星期数
                    if(Integer.parseInt(DataUtil.getPlayerDate(p,"total","week")) < DateUtil.getDayInt())
                    {
                        int new_week = Integer.parseInt(DataUtil.getPlayerDate(p,"total","week")) + 1;
                        data.set(p.getName()+".total.week", new_week);
                    }
                } else {
                    data.set(p.getName()+".total.week", 1);
                }
                //本年
                if((DateUtil.getDate("yyyy").equals(dates[0])) && DateUtil.getDate("MM").equals(dates[1]))
                {
                    int new_month = Integer.parseInt(DataUtil.getPlayerDate(p,"total","month")) + 1;
                    data.set(p.getName()+".total.month", new_month);
                } else {
                    data.set(p.getName()+".total.month", 1);
                }
            }
            data.save(PlayerData);
            data = new Config(new File(Main.getInstance().getDataFolder() + "/data.yml"));
            getPlayerData(p);

        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&3玩家数据 &c创建出现问题!");
            e.printStackTrace();
        }
    }
}
