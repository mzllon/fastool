package tech.fastool.core.lang;

import java.util.Collection;

/**
 * 字符串工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
public class StringUtil {

    /**
     * Don't let anyone instantiate this class
     */
    private StringUtil() {
        throw new AssertionError("Cannot create instance!");
    }

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

}
