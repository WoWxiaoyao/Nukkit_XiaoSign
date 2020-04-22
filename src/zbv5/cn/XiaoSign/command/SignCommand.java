package zbv5.cn.XiaoSign.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.plugin.Plugin;
import zbv5.cn.XiaoSign.Main;
import zbv5.cn.XiaoSign.gui.SignGui;
import zbv5.cn.XiaoSign.lang.Lang;
import zbv5.cn.XiaoSign.util.DataUtil;
import zbv5.cn.XiaoSign.util.ItemUtil;
import zbv5.cn.XiaoSign.util.PluginUtil;
import zbv5.cn.XiaoSign.util.PrintUtil;

import java.util.regex.Pattern;

public class SignCommand extends Command implements PluginIdentifiableCommand
{
    private final Main plugin;
    public SignCommand(Main plugin)
    {
        super("XiaoSign", "XiaoSign 插件主指令.", "/XiaoSign <help>", new String[]{"xsign", "sign"});
        this.setPermission("XiaoSign.command");
        this.getCommandParameters().clear();
        this.addCommandParameters("default", new CommandParameter[]{
                new CommandParameter("命令", false, new String[]{"help","reload"})
        });
        this.plugin = plugin;
    }

    public Plugin getPlugin()
    {
        return this.plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args)
    {
        if (!this.plugin.isEnabled() || !this.testPermission(sender))
        {
            return false;
        }
        if ((args.length == 0) || (args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?")))
        {
            if((sender instanceof Player) && (args.length == 0))
            {
                if(!hasPermission(sender,"XiaoSign.commands.open"))
                {
                    PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
                    return false;
                }
                Player p = (Player)sender;
                SignGui.openGui(p);
            } else {
                Help(sender,label);
            }
            return false;
        }
        if(args[0].equalsIgnoreCase("give"))
        {
            if(hasPermission(sender,"XiaoSign.commands.give"))
            {
                if (args.length >= 3)
                {
                    Pattern pattern = Pattern.compile("[0-9]*");
                    if(pattern.matcher(args[1]).matches())
                    {
                        Player p = Main.getInstance().getServer().getPlayer(getPlayerName(2,args));
                        if((p != null) && (p.isOnline()))
                        {
                            Give(sender,p,Integer.parseInt(args[1]));
                        } else {
                            PrintUtil.PrintCommandSender(sender,Lang.NullPlayer);
                        }
                    } else {
                        PrintUtil.PrintCommandSender(sender,Lang.NoInteger);
                    }
                } else {
                    // ~ args[0] 1 2
                    Correct(sender,"/XiaoSign give <数量> <玩家>");
                }
            } else{
                PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
            }
            return false;
        }
        if(args[0].equalsIgnoreCase("info"))
        {
            if(hasPermission(sender,"XiaoSign.commands.info"))
            {
                if(args.length == 1)
                {
                    if(sender instanceof Player)
                    {
                        Player p = (Player)sender;
                        Info(sender,p);
                    } else {
                        PrintUtil.PrintCommandSender(sender,Lang.NeedPlayer);
                    }
                } else {
                    Player p = Main.getInstance().getServer().getPlayer(getPlayerName(1,args));
                    if((p != null) && (p.isOnline()))
                    {
                        Info(sender,p);
                    } else {
                        InfoOffline(sender,getPlayerName(1,args));
                    }
                }
            } else {
                PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            if(hasPermission(sender,"XiaoSign.commands.reload"))
            {
                PluginUtil.reloadLoadPlugin(sender);
            } else {
                PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
            }
            return true;
        }

        PrintUtil.PrintCommandSender(sender,Lang.NullCommand);
        return false;
    }


    private static void Help(CommandSender sender,String label)
    {
        if(sender != null)
        {
            PrintUtil.PrintCommandSender(sender,"&6========= [&bXiaoSign&6] =========");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" &7- &b打开签到页面&7(仅支持玩家)");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" &ainfo &e<玩家> &7- &b查询玩家签到情况");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" &agive &e<数量> &e<玩家> &7- &b给予玩家补签卡");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" &areload &7- &c重载配置文件");
        }
    }
    private static void Give(CommandSender sender,Player p,int sl)
    {
        if(ItemUtil.getFullInventorySize(p) == 0)
        {
            PrintUtil.PrintCommandSender(sender,Lang.FullPlayer);
        } else {
            p.getInventory().addItem(ItemUtil.card(sl));
            PrintUtil.PrintCommandSender(sender,Lang.SuccessRun);
        }
    }
    private static void Correct(CommandSender sender,String s)
    {
        if(sender != null)
        {
            PrintUtil.PrintCommandSender(sender,"{prefix}&c正确用法:&a"+s);
        }
    }

    private static void Info(CommandSender sender,Player p)
    {
        if(sender != null)
        {
            PrintUtil.PrintCommandSender(sender,"&6=== [&bXiaoSign&6] &6===");
            PrintUtil.PrintCommandSender(sender,"&e玩家:&a"+p.getName());
            PrintUtil.PrintCommandSender(sender,"&3状态:&a在线");
            PrintUtil.PrintCommandSender(sender,"&b今日:"+DataUtil.getPlayerStatus(p,false));
            PrintUtil.PrintCommandSender(sender,"&d最后签到日期:&3"+ DataUtil.getPlayerDate(p,"date","sign"));
            PrintUtil.PrintCommandSender(sender,"&6本周累计签到:&3"+ DataUtil.getPlayerDate(p,"total","week"));
            PrintUtil.PrintCommandSender(sender,"&6本月累计签到:&3"+ DataUtil.getPlayerDate(p,"total","month"));
            PrintUtil.PrintCommandSender(sender,"&6总共累计签到:&3"+ DataUtil.getPlayerDate(p,"total","all"));
        }
    }
    private static void InfoOffline(CommandSender sender,String PlayerName)
    {
        if(sender != null)
        {
            String s = DataUtil.getOfflinePlayerDate(PlayerName);
            if((s.equalsIgnoreCase("null_data"))||(s.equalsIgnoreCase("null")))
            {
                PrintUtil.PrintCommandSender(sender,"{prefix}&c查询失败,玩家数据不存在或发生未知错误.");
                return;
            }
            String[] ss = s.split("-");
            PrintUtil.PrintCommandSender(sender,"&6=== [&bXiaoSign&6] &6===");
            PrintUtil.PrintCommandSender(sender,"&e玩家:&a"+PlayerName);
            PrintUtil.PrintCommandSender(sender,"&3状态:&c离线");
            PrintUtil.PrintCommandSender(sender,"&b今日:"+DataUtil.getStatus(ss[0]));
            PrintUtil.PrintCommandSender(sender,"&d最后签到日期:&3"+ ss[1]);
            PrintUtil.PrintCommandSender(sender,"&6本周累计签到:&3"+ ss[2]);
            PrintUtil.PrintCommandSender(sender,"&6本月累计签到:&3"+ ss[3]);
            PrintUtil.PrintCommandSender(sender,"&6总共累计签到:&3"+ ss[4]);
        }
    }
    private static boolean hasPermission(CommandSender sender,String Permission)
    {
        return (sender.isOp()) || (sender.hasPermission("XiaoSign.admin")) || (sender.hasPermission(Permission));
    }

    private static String getPlayerName(int start,String[] ss)
    {
        int l = ss.length -1 ;
        if(l >= start)
        {
            if(l == start) return ss[l];
            int run = l - start;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i<=run; i++)
            {
                if(i==run)
                {
                    sb.append(ss[start+i]);
                } else {
                    sb.append(ss[start+i]).append(" ");
                }
            }
            return sb.toString();
        }
        return null;
    }
}
