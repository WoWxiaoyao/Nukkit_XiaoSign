package zbv5.cn.XiaoSign.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import zbv5.cn.XiaoSign.gui.SignGui;
import zbv5.cn.XiaoSign.lang.Lang;
import zbv5.cn.XiaoSign.util.DataUtil;
import zbv5.cn.XiaoSign.util.PrintUtil;

public class PlayerListener implements Listener
{
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
        FormWindowModal guiModal = (FormWindowModal)gui;
        FormResponseModal Clicked = (FormResponseModal)e.getResponse();
        Player p = e.getPlayer();
        if(guiModal.getTitle().equals(PrintUtil.HookVariable(p, SignGui.title)))
        {
            if(Clicked.getClickedButtonText().equals(PrintUtil.cc(Lang.Sign_Ui_NotSign)))
            {
                DataUtil.setPlayerSign(p,false);
            }
            if(Clicked.getClickedButtonText().equals(PrintUtil.cc(Lang.Sign_Ui_AlreadySign)))
            {
                PrintUtil.PrintPlayer(p,Lang.AlreadySign,true);
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
}
