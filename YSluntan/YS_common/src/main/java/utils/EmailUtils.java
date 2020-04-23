package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailUtils {

    private IdWorker idWorker;

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd : hh:mm:ss");
        Date date = new Date();
        String time = sdf.format(date.getTime());
        Date parse = sdf.parse(time);
        System.out.println(parse);
        System.out.println(time);
    }

    /**
     * 生成唯一识别的激活码
     * @return 激活码
     */
    public static String getCode(){
        return new IdWorker().nextId()+"";
    }

    /**
     * 生成激活码有效时间
     * @return 往后延十分钟的毫秒值
     */
    public static Long getKryEndTime(){
        return new Date().getTime()+1000*60*10;
    }
}
