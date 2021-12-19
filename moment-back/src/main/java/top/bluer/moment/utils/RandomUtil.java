package top.bluer.moment.utils;

import java.util.Random;

/**
 * @program: moment-back
 * @description: 随机数工具类
 * @author: bluer
 * @date: 2021-11-26 16:15
 * codes: 扁鹊
 **/
public class RandomUtil {
    /**
    * @description: length用户要求产生字符串的长度
    * @date: 2021/11/26 16:15
    * codes: 扁鹊
    **/
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
    * @description: length用户要求产生字符串的长度
    * @date: 2021/11/26 16:16
    * codes: 扁鹊
    **/
    public static String getRandom(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
