package zbv5.cn.XiaoSign.gui;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import zbv5.cn.XiaoSign.util.FileUtil;
import zbv5.cn.XiaoSign.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class SignGui
{
    public static String title = "";
    public static List<String> contents = new ArrayList<>();
    public static String button_1 = "";
    public static String button_2 = "";

    public static void openGui(Player p)
    {
        p.showFormWindow(getUi(p));
    }

    public static FormWindowSimple getUi(Player p)
    {
        String content = "";
              for (int i = 0; i < contents.size(); i++)
              {
                  content = PrintUtil.cc(content + contents.get(i) +"\n");
              }
        FormWindowSimple ui = new FormWindowSimple(PrintUtil.HookVariable(p,title), PrintUtil.HookVariable(p,content));
        ui.addButton(new ElementButton(PrintUtil.HookVariable(p,button_1)));
        ui.addButton(new ElementButton(PrintUtil.HookVariable(p,button_2)));
        return ui;
    }




    public static void LoadWindows()
    {
        try
        {
            title = FileUtil.windows.getString("Title");
            contents = FileUtil.windows.getStringList("Content");
            button_1 = FileUtil.windows.getString("Button_1");
            button_2 = FileUtil.windows.getString("Button_2");
            PrintUtil.PrintConsole("&3windows.yml &a读取完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&3windows.yml &c读取出现问题!");
            e.printStackTrace();
        }
    }
}
