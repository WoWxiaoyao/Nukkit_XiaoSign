package zbv5.cn.XiaoSign.util;

import cn.nukkit.Player;
import zbv5.cn.XiaoSign.Main;

public class TaskUtil
{
    public static void run()
    {
        Main.getInstance().getServer().getScheduler().scheduleRepeatingTask(Main.getInstance(), () -> {
            if(DateUtil.getDate("HH:mm:ss").equals("00:00:00"))
            {
                for(Player p:Main.getInstance().getServer().getOnlinePlayers().values())
                {
                    DataUtil.removeCache(p);
                }
            }
        }, 20, true);
    }
}
