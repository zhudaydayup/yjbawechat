package yjbd.yjbawechat.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Totle:DateTimeUtils
 * @ProjectName:worktitle_rear-end
 * @author:社会码农
 * @data:2018/11/710:59
 */
public class DateTimeUtils {

    private static String strDateTime = "";
    private static Date date = null;

    private static DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
        * @Description: ${将Date类型转化为String类型，"yyyy/MM/dd HH:mm:ss"}
        * @param: ${tags}
        * @return: ${string}
        * @author: 社会码农
        * @date: 2018/11/7 11:29
    */
    public static String getNowTime() {

        LocalDateTime ldt1 = LocalDateTime.now();
        strDateTime = dtf1.format(ldt1);
        return strDateTime;

    }

    /**
        * @Description: ${将Date类型转化为String类型，"yyyy/MM/dd"}
        * @param: ${tags}
        * @return: ${string}
        * @author: 社会码农
        * @date: 2018/11/7 11:29
    */
    public static String getNowDate() {

        LocalDateTime ldt2 = LocalDateTime.now();
        strDateTime = dtf2.format(ldt2);
        return strDateTime;

    }

    /**
        * @Description: ${将String类型转化为Date类型}
        * @param: ${tags}
        * @return: ${return_type}
        * @author: 社会码农
        * @date: 2018/11/7 11:30
    */
    public static Date formatDateTime(String strDate) {

        DateFormat dtf3 = new SimpleDateFormat("yyyy/MM/dd");

        try {

            date = dtf3.parse(strDate);

        }catch (ParseException e) {

            e.printStackTrace();

        }
        return date;

    }

}
