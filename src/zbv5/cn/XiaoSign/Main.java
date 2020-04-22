package zbv5.cn.XiaoSign;

import cn.nukkit.plugin.PluginBase;
import zbv5.cn.XiaoSign.util.*;


public class Main extends PluginBase
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
        PluginUtil.LoadPlugin();
    }

    @Override
    public void onDisable()
    {
        PluginUtil.DisablePlugin();
    }
}
