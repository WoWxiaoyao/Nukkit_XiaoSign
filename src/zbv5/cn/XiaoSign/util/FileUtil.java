package zbv5.cn.XiaoSign.util;

import cn.nukkit.utils.Config;
import zbv5.cn.XiaoSign.Main;
import zbv5.cn.XiaoSign.gui.SignGui;
import zbv5.cn.XiaoSign.lang.Lang;

import java.io.File;

public class FileUtil
{
    public static Config lang;
    public static Config config;
    public static Config windows;
    public static Config reward;

    public static void LoadFile()
    {
        File Lang_Yml = new File(Main.getInstance().getDataFolder(), "lang.yml");
        if (!Lang_Yml.exists())
        {
            Main.getInstance().saveResource("lang.yml", false);
        }
        lang = new Config(new File(Main.getInstance().getDataFolder() + "/lang.yml"));
        PrintUtil.PrintConsole("&3lang.yml &a加载.");
        Lang.LoadLang();

        File Config_Yml = new File(Main.getInstance().getDataFolder(), "config.yml");
        if (!Config_Yml.exists())
        {
            Main.getInstance().saveResource("config.yml", false);
        }
        config = new Config(new File(Main.getInstance().getDataFolder() + "/config.yml"));
        PrintUtil.PrintConsole("&3config.yml &a加载.");

        DataUtil.CheckDataStore();

        File Windows_Yml = new File(Main.getInstance().getDataFolder(), "windows.yml");
        if (!Windows_Yml.exists())
        {
            Main.getInstance().saveResource("windows.yml", false);
        }
        windows = new Config(new File(Main.getInstance().getDataFolder() + "/windows.yml"));
        PrintUtil.PrintConsole("&3windows.yml &a加载.");
        SignGui.LoadWindows();

        File Reward_Yml = new File(Main.getInstance().getDataFolder(), "reward.yml");
        if (!Reward_Yml.exists())
        {
            Main.getInstance().saveResource("reward.yml", false);
        }
        reward = new Config(new File(Main.getInstance().getDataFolder() + "/reward.yml"));
        PrintUtil.PrintConsole("&3reward.yml &a加载.");
        RewardUtil.LoadTotal();
    }
}
