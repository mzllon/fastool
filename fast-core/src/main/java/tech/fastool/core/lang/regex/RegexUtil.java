package tech.fastool.core.lang.regex;

import tech.fastool.core.lang.ObjectUtil;
import tech.fastool.core.lang.StringUtil;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class RegexUtil {

    /**
     * 给定内容是否匹配正则表达式
     *
     * @param regex 正则表达式
     * @param cse   字符串
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(String regex, CharSequence cse) {
        if (StringUtil.isEmpty(regex)) {
            return false;
        }
        if (cse == null) {
            return false;
        }
        return isMatch(Patterns.get(regex), cse);
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param cse     字符串
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(Pattern pattern, CharSequence cse) {
        if (ObjectUtil.isAnyNull(pattern, cse)) {
            return false;
        }
        return pattern.matcher(cse).matches();
    }

}
