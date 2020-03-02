package zbv5.cn.XiaoSign.util;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil
{
    public static boolean Recoup = false;

    public static Item card(int sl)
    {
        Item i = new Item(ItemID.PAPER);
        i.setCount(sl);
        i.setCustomName(PrintUtil.cc(FileUtil.items.getString("Card.CustomName")));
        List<String> lores = new ArrayList<>();
        lores = FileUtil.items.getStringList("Card.Lores");
        String lore = "";
        for (int ii = 0; ii < lores.size(); ii++)
        {
            lore = PrintUtil.cc(lore + lores.get(ii) +"\n");
        }
        i.setLore(lore);
        i.addEnchantment(Enchantment.getEnchantment(17));
        return i;
    }



    public static void CheckRecoup()
    {
        if(FileUtil.config.getBoolean("Recoup"))
        {
            Recoup = true;
            PrintUtil.PrintConsole("&a启用补签功能.");
        } else {
            PrintUtil.PrintConsole("&c禁用补签功能.");
        }
    }

    public static boolean TakePlayerItem(Player p, Item item)
    {
        boolean b = false;
        int Count = 0;
        for (Item i : p.getInventory().getContents().values())
        {
            if((i != null) && (i.hasCustomName()) && (i.getCustomName().equals(item.getCustomName())) &&(i.hasEnchantments()))
            {
                if(item.getCount()  <= i.getCount())
                {
                    Count = Count + i.getCount();
                    p.getInventory().remove(i);
                    b = true;
                } else {
                    b = false;
                }
            }
        }
        if(b)
        {
            int a = Count - item.getCount();
            if(a > 0)
            {
                p.getInventory().addItem(card(a));
            }

        }


        return b;
    }
}
