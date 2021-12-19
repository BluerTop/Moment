package top.bluer.moment.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: moment-back
 * @description: 模板渲染工具类
 * @author: bluer
 * @date: 2021-11-26 17:06
 * codes: 扁鹊
 **/
public class TemplateUtil {

    /**
    * @description: 自定义渲染模板
    * @date: 2021/11/26 17:07
    * codes: 扁鹊
    **/
    public static String processTemplate(String template, Map<String, Object> params) {
        if (template == null || params == null)
            return null;
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
