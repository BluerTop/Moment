package top.bluer.moment.utils;

import java.util.Random;
import java.util.UUID;

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

    /**
     * @description: 生成固定长度随机码
     * @date: 2021/12/23 18:03
     * @codes: 扁鹊
     **/
    public static String getRanId() {
        int machineId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

}
