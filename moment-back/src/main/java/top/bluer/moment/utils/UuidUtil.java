package top.bluer.moment.utils;

import java.util.UUID;

/**
 * @program: moment-back
 * @description: UUID工具类
 * @author: bluer
 * @date: 2021-12-12 14:55
 * codes: 扁鹊
 **/
public class UuidUtil {
    /**
    * @description: 不带-
    * @date: 2021/12/12 14:57
    * @codes: 扁鹊
    **/
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    /**
    * @description: 不带-
    * @date: 2021/12/12 14:57
    * @codes: 扁鹊
    **/
    public static String beltUUID() {
        return UUID.randomUUID().toString();
    }
}
