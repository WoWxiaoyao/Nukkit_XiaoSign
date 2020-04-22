package zbv5.cn.XiaoSign.lang;

import zbv5.cn.XiaoSign.util.FileUtil;
import zbv5.cn.XiaoSign.util.PrintUtil;

public class Lang
{
    public static String Prefix = "&6[&bXiaoSign&6]";
    public static String NoPermission = "{prefix}&c你没有权限这样做";
    public static String NeedPlayer = "{prefix}&c只有玩家才能执行该操作.";
    public static String SuccessReload = "{prefix}&a重载完成!";
    public static String FailReload = "{prefix}&c重载失败!请前往控制台查看报错.";
    public static String NoInteger = "{prefix}&c输入值非整数.";
    public static String NullCommand = "{prefix}&c未知指令.";
    public static String NullReward = "{prefix}&c服务器设定奖励异常,请联系管理处理.";
    public static String NullPlayer = "{prefix}&c玩家不存在或不在线.";
    public static String FullPlayer = "{prefix}&c玩家背包已满.";
    public static String NoTotal = "&c没有签到记录";
    public static String AlreadySign = "&c没有签到记录";
    public static String Sign_AlreadySign = "&a已签到";
    public static String Sign_NotSign = "&6可签到";
    public static String Sign_Ui_AlreadySign = "&a已签到";
    public static String Sign_Ui_CanRecoup = "&e可补签";
    public static String Sign_Ui_NotSign = "&6点击签到";
    public static String Sign_TodayFormat = "yyyy/MM/dd";
    public static String SuccessCanRecoup = "{prefix}&a补签成功!";
    public static String FailCanRecoup = "{prefix}&c补签失败.";
    public static String SuccessRun = "{prefix}&a执行成功!";

    public static void LoadLang()
    {
        try
        {
            Prefix = FileUtil.lang.getString("Prefix");
            NoPermission = FileUtil.lang.getString("NoPermission");
            SuccessReload = FileUtil.lang.getString("SuccessReload");
            FailReload = FileUtil.lang.getString("FailReload");
            NoInteger = FileUtil.lang.getString("NoInteger");
            NeedPlayer = FileUtil.lang.getString("NeedPlayer");
            NullCommand = FileUtil.lang.getString("NullCommand");
            NullPlayer = FileUtil.lang.getString("NullPlayer");
            NullReward = FileUtil.lang.getString("NullReward");
            FullPlayer = FileUtil.lang.getString("FullPlayer");
            NoTotal = FileUtil.lang.getString("NoTotal");
            AlreadySign = FileUtil.lang.getString("AlreadySign");
            Sign_AlreadySign = FileUtil.lang.getString("Sign.AlreadySign");
            Sign_NotSign = FileUtil.lang.getString("Sign.NotSign");
            Sign_Ui_AlreadySign = FileUtil.lang.getString("Sign.Ui_AlreadySign");
            Sign_Ui_CanRecoup  = FileUtil.lang.getString("Sign.Ui_CanRecoup");
            Sign_Ui_NotSign = FileUtil.lang.getString("Sign.Ui_NotSign");
            Sign_TodayFormat = FileUtil.lang.getString("Sign.TodayFormat");
            SuccessCanRecoup = FileUtil.lang.getString("SuccessCanRecoup");
            FailCanRecoup = FileUtil.lang.getString("FailCanRecoup");
            SuccessRun = FileUtil.lang.getString("SuccessRun");
            PrintUtil.PrintConsole("&3lang.yml &a读取完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&3lang.yml &c读取出现问题!");
            e.printStackTrace();
        }
    }
}
