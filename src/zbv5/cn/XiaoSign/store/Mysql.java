package zbv5.cn.XiaoSign.store;

import cn.nukkit.Player;
import zbv5.cn.XiaoSign.Main;
import zbv5.cn.XiaoSign.lang.Lang;
import zbv5.cn.XiaoSign.util.DataUtil;
import zbv5.cn.XiaoSign.util.DateUtil;
import zbv5.cn.XiaoSign.util.FileUtil;
import zbv5.cn.XiaoSign.util.PrintUtil;

import java.sql.*;
import java.util.List;

public class Mysql
{
    static final String Driver = "com.mysql.jdbc.Driver";
    static final String Url = FileUtil.config.getString("Mysql.Url");
    static final String User = FileUtil.config.getString("Mysql.User");
    static final String Pass = FileUtil.config.getString("Mysql.PassWord");

    public static void createTable()
    {
        try {
            Class.forName(Driver);
        }catch (ClassNotFoundException e){
            PrintUtil.PrintConsole("&3Mysql &c数据库驱动异常!");
            e.printStackTrace();
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Url,User,Pass);
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            st.execute("CREATE TABLE IF NOT EXISTS `"+table+"` (`name` VARCHAR(40) PRIMARY KEY, `total_all`  VARCHAR(40), `total_month`  VARCHAR(40), `total_week`  VARCHAR(40), `date`  VARCHAR(40) );");
            PrintUtil.PrintConsole("&3Mysql&7（table:"+table+") &a连接成功!");
        } catch (SQLException e)
        {
            PrintUtil.PrintConsole("&3Mysql &c数据库创表出现问题!");
            e.printStackTrace();

            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
    }

    public static void getPlayerData(Player p)
    {
        Connection conn = null;
        int all = 0;
        int month = 0;
        int week = 0;
        String sign = "NotSign";
        String date = Lang.NoTotal;
        try {
            conn = DriverManager.getConnection(Url,User,Pass);
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE name=?");
            String sql = "select * from "+table+" where name='" + p.getName() + "' ";
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next())
            {
                all = rs.getInt("total_all");
                date = rs.getString("date");
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
                    week = rs.getInt("total_week");
                }
                //本年
                if(DateUtil.getDate("yyyy").equals(dates[0]))
                {
                    //本月
                    if(DateUtil.getDate("MM").equals(dates[1]))
                    {
                        month = rs.getInt("total_month");
                    }
                }
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e)
        {
            PrintUtil.PrintConsole("&3玩家数据 &c查询出现问题!");
            e.printStackTrace();
        }
        DataUtil.setCache(p,all,month,week,date,sign);
    }


    public static void PlayerSign(Player p)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Url,User,Pass);
            Statement st = conn.createStatement();
            String table = FileUtil.config.getString("Mysql.Table");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+table+" WHERE name=?");
            String sql = "select * from "+table+" where name='" + p.getName() + "' ";
            ResultSet rs = ps.executeQuery(sql);
            while (!rs.next())
            {
                st.execute("INSERT INTO "+table+" VALUES ('" + p.getName() + "', '1', '1', '1', '"+DateUtil.getDate("yyyy/MM/dd")+"' )");
                rs.close();
                st.close();
                conn.close();
                DataUtil.setCache(p,1,1,1,DateUtil.getDate("yyyy/MM/dd"),"AlreadySign");
                return;
            }
            int all = Integer.parseInt(DataUtil.getPlayerDate(p,"total","all"));
            int month = Integer.parseInt(DataUtil.getPlayerDate(p,"total","month"));
            int week = Integer.parseInt(DataUtil.getPlayerDate(p,"total","week"));
            String date = DataUtil.getPlayerDate(p,"date","sign");
            String[] dates = date.split("/");

            all = all +1;
            //判断周
            List<String> WeekList = DateUtil.getWeekDate();
            if(WeekList.contains(date))
            {
                week = week +1;
            } else {
                week = 1;
            }
            //本年
            if(DateUtil.getDate("yyyy").equals(dates[0]))
            {
                //本月
                if(DateUtil.getDate("MM").equals(dates[1]))
                {
                    month = month +1;
                } else {
                    month = 1;
                }
            } else {
                month = 1;
            }
            st.executeUpdate("UPDATE "+table+" set name= '" + p.getName() + "' , total_all= '" + all + "', total_month='" + month + "' , total_week='" + week + "', date='" + DateUtil.getDate("yyyy/MM/dd") + "'  WHERE name='" + p.getName() + "'");
            DataUtil.setCache(p,all,month,week,date,"AlreadySign");
        } catch (SQLException e)
        {
            PrintUtil.PrintConsole("&3玩家数据 &c修改出现问题!");
            e.printStackTrace();
        }
    }
}
