package tech.fastool.core.lang;

import lombok.experimental.UtilityClass;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
@UtilityClass
public class StringUtil {

    // region 定义的公共的字符串相关的常量

    /**
     * null字符串
     */
    public static final String NULL = "null";

    public static final String UNDEFINED = "undefined";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 英文逗号字符串
     */
    public static final String COMMA = EMPTY_STRING + CharUtil.COMMA;

    /**
     * 下划线
     */
    public static final String UNDERLINE = EMPTY_STRING + CharUtil.UNDERLINE;

    /**
     * 中划线/破折号
     */
    public static final String DASH = EMPTY_STRING + CharUtil.DASH;

    /**
     * 点
     */
    public static final String DOT = EMPTY_STRING + CharUtil.DOT;

    /**
     * 左边大括号
     */
    public static final String LEFT_CURLY_BRACKET = EMPTY_STRING + CharUtil.LEFT_CURLY_BRACKET;

    /**
     * 分隔符（左） {
     */
    public static final String DELIMITER_START = LEFT_CURLY_BRACKET;

    /**
     * 右边大括号
     */
    public static final String RIGHT_CURLY_BRACKET = EMPTY_STRING + CharUtil.RIGHT_CURLY_BRACKET;

    /**
     * 分隔符（右） }
     */
    public static final String DELIMITER_END = RIGHT_CURLY_BRACKET;

    /**
     * 空的JSON字符串
     */
    public static final String EMPTY_JSON = LEFT_CURLY_BRACKET + RIGHT_CURLY_BRACKET;

    /**
     * 正斜杠
     */
    public static final String SLASH = EMPTY_STRING + CharUtil.SLASH;

    /**
     * 反斜杠
     */
    public static final String BACKSLASH = EMPTY_STRING + CharUtil.BACKSLASH;

    /**
     * &符号
     */
    public static final String AMP = EMPTY_STRING + CharUtil.AMP;

    /**
     * :符号
     */
    public static final String COLON = EMPTY_STRING + CharUtil.COLON;

    /**
     * 等号
     */
    public static final String EQUALS = EMPTY_STRING + CharUtil.EQUALS;

    /**
     * 空格
     */
    public static final String SPACE = EMPTY_STRING + CharUtil.SPACE;

    // endregion


    // region 判断字符串是否为空或不为空

    /**
     * 字符串是否为空白，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     *
     * @param cse 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(CharSequence cse) {
        if (isEmpty(cse)) {
            return true;
        }
        int length = cse.length();
        for (int i = 0; i < length; i++) {
            if (!CharUtil.isBlankChar(cse.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符粗是否不为空，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     *
     * @param cse 字符粗
     * @return 是否不为空
     */
    public static boolean isNotBlank(CharSequence cse) {
        return !isBlank(cse);
    }

    /**
     * 判断字符粗是否不为空，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     * <p>在Web环境下，字符串"null"或"undefined"也是为空</p>
     *
     * @param cse 字符粗
     * @return 是否不为空
     * @see #isNotBlank(CharSequence)
     */
    public static boolean isNotBlankInWebEnv(CharSequence cse) {
        if (StringUtil.isNotBlank(cse)) {
            String str = cse.toString();
            return !(StringUtil.NULL.equals(str) || StringUtil.UNDEFINED.equals(str));
        }
        return false;
    }

    /**
     * 数组的任意元素是否为空白
     *
     * @param array 数组
     * @return 如果数组为空或元素中为空白则返回{@code true}，否则返回{@code  false}
     * @see #isBlank(CharSequence)
     */
    public static boolean isAnyBlank(CharSequence... array) {
        if (ArrayUtil.isEmpty(array)) {
            return true;
        }
        for (CharSequence cse : array) {
            if (isBlank(cse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组的所有元素都为空白
     *
     * @param array 数组
     * @return 数组是否都为空
     */
    public static boolean isAllBlank(CharSequence... array) {
        if (ArrayUtil.isEmpty(array)) {
            return true;
        }
        for (CharSequence cse : array) {
            if (isNotBlank(cse)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数组的任意一个字符串不为空白字符
     *
     * @param array 数组
     * @return true / false
     * @see #isNotBlank(CharSequence)
     */
    public static boolean isAnyNotBlank(CharSequence... array) {
        if (ArrayUtil.isEmpty(array)) {
            return false;
        }
        for (CharSequence cse : array) {
            if (isNotBlank(cse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组的所有元素都不为空白字符
     *
     * @param array 数组
     * @return true / false
     * @see #isNotBlank(CharSequence)
     */
    public static boolean isAllNotBlank(CharSequence... array) {
        if (ArrayUtil.isEmpty(array)) {
            return false;
        }
        for (CharSequence cse : array) {
            if (isBlank(cse)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>集合本身不是{@code null}</li>
     * <li>集合中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strColl 字符串列表
     * @return 当且仅当字符串列表均非空则返回{@code true},反之则返回{@code false}.
     * @see #hasLength(CharSequence)
     */
    public static boolean isAnyNotBlank(Collection<CharSequence> strColl) {
        if (CollectionUtil.isEmpty(strColl)) {
            return false;
        }
        for (CharSequence cs : strColl) {
            if (isNotBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>集合本身不是{@code null}</li>
     * <li>集合中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strColl 字符串列表
     * @return 当且仅当字符串列表均非空则返回{@code true},反之则返回{@code false}.
     * @see #hasLength(CharSequence)
     */
    public static boolean isAllNotBlank(Collection<CharSequence> strColl) {
        if (CollectionUtil.isEmpty(strColl)) {
            return false;
        }
        for (CharSequence str : strColl) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     *
     * @param cse 被检测的字符序列
     * @return 字符串是否为空
     */
    public static boolean isEmpty(CharSequence cse) {
        return cse == null || cse.length() == 0;
    }

    /**
     * 判断字符串列表中有任何一个字符串是否为空，空字符串定义
     * <ul>
     * <li>数组本身为<code>null</code></li>
     * <li>数组中任一元素<code>null</code></li>
     * <li>数组中任一元素为""</li>
     * </ul>
     * 以下为示例代码及其返回值
     * <pre>
     *     StringUtil.isAnyEmpty();               = true
     *     StringUtil.isAnyEmpty("");             = true
     *     StringUtil.isAnyEmpty("","1");         = true
     *     StringUtil.isAnyEmpty("1",null,"");    = true
     *     StringUtil.isAnyEmpty("a","b");        = false
     * </pre>
     *
     * @param array 被检测的字符串列表
     * @return 字符串列表中是否有任一字符串为空
     * @see StringUtil#isEmpty(CharSequence)
     */
    public static boolean isAnyEmpty(CharSequence... array) {
        if (ArrayUtil.isEmpty(array)) {
            return true;
        }
        for (CharSequence ele : array) {
            if (isEmpty(ele)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串列表是否都为空，空字符串定义
     * <ul>
     * <li>数组本身为<code>null</code></li>
     * <li>数组中所有元素<code>null</code>或则""</li>
     * </ul>
     * 以下为示例代码及其返回值
     * <pre>
     *     StringUtil.isAllEmpty();               = true
     *     StringUtil.isAllEmpty("");             = true
     *     StringUtil.isAllEmpty("","1");         = false
     *     StringUtil.isAllEmpty("",null,"");     = true
     *     StringUtil.isAllEmpty("a","b");        = false
     * </pre>
     *
     * @param array 字符串列表
     * @return 字符串列表所有元素是否都为空
     * @see #isEmpty(CharSequence)
     */
    public static boolean isAllEmpty(CharSequence... array) {
        if (ArrayUtil.isEmpty(array)) {
            return true;
        }
        for (CharSequence cse : array) {
            if (isNotEmpty(cse)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     *
     * @param cse 被检测的字符序列
     * @return 字符串是否不为空
     */
    public static boolean isNotEmpty(CharSequence cse) {
        return hasLength(cse);
    }

    /**
     * 判断字符串不是空字符串，空字符串定义
     * <ul>
     * <li><code>null</code></li>
     * <li>""</li>
     * </ul>
     * <pre>
     *     StringUtil.hasLength(null);              = false
     *     StringUtil.hasLength("");                = false
     *     StringUtil.hasLength(" ");               = true
     *     StringUtil.hasLength("Hi");              = true
     * </pre>
     *
     * @param cse 被检测的字符串
     * @return 非空字符串
     */
    public static boolean hasLength(CharSequence cse) {
        return !isEmpty(cse);
    }

    /**
     * 判断字符串是否存在文本字符，文本字符串的定义：
     * <ul>
     * <li>不是{@code null}</li>
     * <li>不是""</li>
     * <li>字符串中的任意一个字符不是''</li>
     * </ul>
     * <pre>
     *     StringUtil.hasText(null);              = false
     *     StringUtil.hasText("");                = false
     *     StringUtil.hasText(" ");               = false
     *     StringUtil.hasText("  ");              = false
     *     StringUtil.hasText(" a");              = true
     *     StringUtil.hasText("a");               = true
     * </pre>
     *
     * @param cse 被检测的字符串
     * @return 非空白字符串
     * @see #isNotBlank(CharSequence)
     */
    public static boolean hasText(CharSequence cse) {
        return isNotBlank(cse);
    }

    /**
     * 判断字符串中是否存在任意一个空白子符
     * <pre>
     *     StringUtil.containsBlank(null);            = false
     *     StringUtil.containsBlank("");              = false
     *     StringUtil.containsBlank(" ");             = true
     *     StringUtil.containsBlank("ab d");          = true
     *     StringUtil.containsBlank("abcd");          = false
     * </pre>
     *
     * @param cse 被检测的字符串
     * @return 当字符串存在一个空白符时就返回{@code true},否则返回{@code false}
     * @see Character#isWhitespace(char)
     */
    public static boolean containsBlank(CharSequence cse) {
        if (isEmpty(cse)) {
            return false;
        }
        for (int len = cse.length(), i = 0; i < len; i++) {
            if (Character.isWhitespace(cse.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串中是否存在任意一个空白子符
     *
     * @param cse 被检测的字符串
     * @return true or false
     * @see #containsBlank(CharSequence)
     */
    public static boolean containsWhitespace(CharSequence cse) {
        return containsBlank(cse);
    }

    // endregion


    // region General

    /**
     * 构建字符串
     * 如果编码为{@code  null}则采用系统编码
     *
     * @param data     字节数组
     * @param encoding 编码，可以为空
     * @return 字符串
     */
    public static String str(byte[] data, Charset encoding) {
        if (data == null) {
            return null;
        }
        return (encoding == null) ? new String(data) : new String(data, encoding);
    }

    /**
     * {@link CharSequence} 转为字符串
     *
     * @param cse {@link CharSequence}
     * @return 字符串
     */
    public static String str(CharSequence cse) {
        return null == cse ? null : cse.toString();
    }

    // endregion


    // region 去除字符串头尾部的空白部分

    /**
     * 去除字符串左右两侧的空白符
     * <pre>
     *     StringUtil.trim(null);               = null
     *     StringUtil.trim("");              = ""
     *     StringUtil.trim("    ")           = ""
     *     StringUtil.trim("  a b  ");       = "a b"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回已经去除左右两边的空白的字符串
     * @see Character#isWhitespace(char)
     */
    public static String trim(CharSequence cse) {
        return trim(cse, 0);
    }

    /**
     * 去除字符串左侧的空白字符
     * <pre>
     *     StringUtil.ltrim(null);            = null
     *     StringUtil.ltrim("ab");            = "ab"
     *     StringUtil.ltrim("  ab c");        = "ab c"
     *     StringUtil.ltrim("  ab c ");       = "ab c "
     * </pre>
     *
     * @param cse 字符串
     * @return 返回去除后的字符串
     */
    public static String trimLeft(CharSequence cse) {
        return trim(cse, -1);
    }

    /**
     * 去除字符串右侧的空白字符
     * <pre>
     *     StringUtil.rtrim(null);            = null
     *     StringUtil.rtrim("ab");            = "ab"
     *     StringUtil.rtrim(" ab c");         = " ab c"
     *     StringUtil.rtrim(" ab c ");        = " ab c"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回去除后的字符串
     */
    public static String trimRight(CharSequence cse) {
        return trim(cse, 1);
    }

    /**
     * 去除字符串头尾部空白部分
     * <ul>
     * <li>当{@code mode}=-1去除字符串左侧部分空白</li>
     * <li>当{@code mode}=0去除字符串左右两侧部分空白</li>
     * <li>当{@code mode}=1去除字符串右侧部分空白</li>
     * </ul>
     *
     * @param cse  字符串
     * @param mode 去除模式
     * @return 去除空白后的字符串
     */
    public static String trim(CharSequence cse, int mode) {
        if (cse == null) {
            return null;
        }

        int length = cse.length(), start = 0, end = length;

        if (mode <= 0) {
            //trim by left
            while (start < end && CharUtil.isBlankChar(cse.charAt(start))) {
                start++;
            }
        }

        if (mode >= 0) {
            //trim by right
            while (start < end && CharUtil.isBlankChar(cse.charAt(end - 1))) {
                end--;
            }
        }

        if (start > 0 || end < length) {
            return cse.toString().substring(start, end);
        }
        return cse.toString();
    }

    /**
     * 去除字符串数组中的每个元素左右两侧空白部分
     *
     * @param array 原数组
     * @return 新数组，不为{@code null}
     */
    public static String[] trim(CharSequence... array) {
        if (array == null || array.length == 0) {
            return ArrayUtil.EMPTY_STRING_ARRAY;
        }
        return trimToList(array).toArray(ArrayUtil.EMPTY_STRING_ARRAY);
    }

    /**
     * 去除字符串数组中的每个元素左右两侧空白部分
     *
     * @param array 原数组
     * @return 新集合，不为{@code null}
     */
    public static List<String> trimToList(CharSequence... array) {
        if (ArrayUtil.isEmpty(array)) {
            return ListUtil.emptyList();
        }
        return Arrays.stream(array).map(StringUtil::trim).collect(Collectors.toList());
    }

    /**
     * 去除字符串集合中的每个元素左右两侧空白部分
     *
     * @param array 原数组，不为{@code null}
     */
    public static List<String> trim(List<CharSequence> array) {
        if (ListUtil.isEmpty(array)) {
            return ListUtil.emptyList();
        }
        return array.stream().map(StringUtil::trim).collect(Collectors.toList());
    }


    /**
     * 去除字符串中所有的空白字符
     * <pre>
     *     StringUtil.trimAllBlank(null);            = null
     *     StringUtil.trimAllBlank(" a ");           = "a"
     *     StringUtil.trimAllBlank(" a b c ");       = "abc"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回去除后的字符串
     * @see Character#isWhitespace(char)
     */
    public static String trimAllBlank(CharSequence cse) {
        if (isEmpty(cse)) {
            return str(cse);
        }
        StringBuilder sb = new StringBuilder(cse);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * 去除字符串中所有的空白字符
     *
     * @param cse 字符串
     * @return 去除空白字符的字符串
     * @see #trimAllBlank(CharSequence)
     */
    public static String trimAllWhitespace(CharSequence cse) {
        return trimAllBlank(cse);
    }

    // endregion

}
