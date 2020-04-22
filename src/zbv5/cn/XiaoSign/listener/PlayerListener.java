package zbv5.cn.XiaoSign.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import org.json.JSONObject;
import zbv5.cn.XiaoSign.gui.SignGui;
import zbv5.cn.XiaoSign.lang.Lang;
import zbv5.cn.XiaoSign.util.DataUtil;
import zbv5.cn.XiaoSign.util.ItemUtil;
import zbv5.cn.XiaoSign.util.PrintUtil;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerListener implements Listener
{
    public static boolean JoinAuto = false;
    public static int Delay = 20;

    @EventHandler
    public void onClickWindow(PlayerFormRespondedEvent e)
    {
        if (e.getPlayer() == null) {
            return;
        }
        if (e.getResponse() == null) {
            return;
        }
        FormWindow gui = e.getWindow();

        if ((!(gui instanceof FormWindowSimple)) || (e.wasClosed()))
        {
            return;
        }

        JSONObject json = new JSONObject(e.getWindow().getJSONData());

        Player p = e.getPlayer();

        if(json.getString("title").equals(PrintUtil.HookVariable(p, SignGui.title)))
        {
            if (((FormResponseSimple)e.getResponse()).getClickedButton().getText().equalsIgnoreCase(PrintUtil.cc(Lang.Sign_Ui_NotSign)))
            {
                DataUtil.setPlayerSign(p,false);
            }
            if (((FormResponseSimple)e.getResponse()).getClickedButton().getText().equalsIgnoreCase(PrintUtil.cc(Lang.Sign_Ui_AlreadySign)))
            {
                PrintUtil.PrintPlayer(p,Lang.AlreadySign);
            }
            if (((FormResponseSimple)e.getResponse()).getClickedButton().getText().equalsIgnoreCase(PrintUtil.cc(Lang.Sign_Ui_CanRecoup)))
            {
                if(ItemUtil.TakePlayerItem(p,ItemUtil.card(1)))
                {
                    DataUtil.setPlayerSign(p,true);
                }else{
                    PrintUtil.PrintPlayer(p,Lang.FailCanRecoup);
                }
            }
        }
    }



    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        if(DataUtil.total_all.containsKey(p.getName()))
        {
            DataUtil.removeCache(p);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if(JoinAuto)
        {
            DataUtil.getPlayerDateCache(p);
            if(DataUtil.getPlayerDate(p,"info","sign").equals("NotSign"))
            {
                DelayOpen(p,Delay);
            }
        }
    }



    public static void DelayOpen(Player p,int i)
    {
        final Timer timer=new Timer(); TimerTask task=new TimerTask(){
        public void run()
        {
            if(p.isOnline())
            {
                SignGui.openGui(p);
            }
            timer.cancel();
        }
    };
        timer.schedule(task,i*1000);
    }




}
