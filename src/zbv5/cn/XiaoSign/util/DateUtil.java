package zbv5.cn.XiaoSign.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil
{
    // yyyy/MM/dd
    public static String getDate(String s)
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(s);
        return dateFormat.format(date);
    }

    public static List<String> getWeekDate()
    {
        List<String> WeekDateList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();

        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
        {
            cal.add(Calendar.DATE, -1);
        }
        for (int i = 1; i < 8; i++)
        {
            WeekDateList.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        return WeekDateList;
    }

    public static int getDayInt()
    {
        String today = getDate("yyyy/MM/dd");
        int i = 0;
        for(String s:getWeekDate())
        {
            i = i+1;
            if(s.equals(today))
            {
                break;
            }
        }
        return i;
    }
}
