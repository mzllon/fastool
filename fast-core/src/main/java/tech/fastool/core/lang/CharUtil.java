package tech.fastool.core.lang;

/**
 * 字符工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
public final class CharUtil {

    /**
     * Don't let anyone instantiate this class
     */
    private CharUtil() {
        throw new AssertionError("Cannot create instance!");
    }

    //region 定义的公共的字符相关的常量

    /**
     * 点
     */
    public static final char DOT = '.';

    /**
     * 逗号
     */
    public static final char COMMA = ',';

    /**
     * 下划线
     */
    public static final char UNDERLINE = '_';

    /**
     * 中划线/破折号
     */
    public static final char DASH = '-';

    /**
     * - 字符
     */
    public static final char HYPHEN = DASH;

    /**
     * 斜杆
     */
    public static final char SLASH = '/';

    /**
     * 反斜杠
     */
    public static final char BACKSLASH = '\\';

    /**
     * 左边大括号 {
     */
    public static final char LEFT_CURLY_BRACKET = '{';

    /**
     * 右边大括号 }
     */
    public static final char RIGHT_CURLY_BRACKET = '}';

    /**
     * 作中括号 [
     */
    public static final char LEFT_MIDDLE_BRACKET = '[';

    /**
     * 右中括号 ]
     */
    public static final char RIGHT_MIDDLE_BRACKET = ']';

    /**
     * 左小括号 (
     */
    public static final char LEFT_BRACKET = '(';

    /**
     * 右小括号 )
     */
    public static final char RIGHT_BRACKET = ')';

    /**
     * 等号
     */
    public static final char EQUALS = '=';

    /**
     * 空格
     */
    public static final char SPACE = ' ';

    /**
     * 字符常量：冒号 {@code ':'}
     */
    public static final char COLON = ':';

    /**
     * TAB制表符 \t
     */
    public static final char TAB = '\t';

    /**
     * Carriage Return 回车符
     * https://www.cnblogs.com/xiaotiannet/p/3510586.html
     */
    public static final char CR = '\r';

    /**
     * Line Feed 换行
     * https://www.cnblogs.com/xiaotiannet/p/3510586.html
     */
    public static final char LF = '\n';

    /**
     * 单引号
     */
    public static final char SINGLE_QUOTE = '\'';

    /**
     * 双引号
     */
    public static final char DOUBLE_QUOTE = '"';

    /**
     * @ 符号
     */
    public static final char AT = '@';

    /**
     * # 符号
     */
    public static final char POUND = '#';

    /**
     * $ 符号
     */
    public static final char DOLLAR = '$';

    /**
     * %符号
     */
    public static final char PERCENT = '%';

    /**
     * & 符号
     */
    public static final char AMP = '&';

    /**
     * * 符号
     */
    public static final char ASTERISK = '*';

    /**
     * * 符号
     */
    public static final char STAR = ASTERISK;

     /**
     * 加号
     */
    public static final char PLUS = '+';

    /**
     * 减号
     */
    public static final char MINUS = DASH;

    /**
     * ASCII总共的长度
     */
    private static final int ASCII_LENGTH = 128;

    /**
     * cache ascii
     */
    private static final String[] CACHE = new String[ASCII_LENGTH];

    static {
        for (char i = 0; i < ASCII_LENGTH; i++) {
            CACHE[i] = String.valueOf(i);
        }
    }

    //endregion

    /**
     * 是否为ASCII字符，ASCII字符位于0~127之间
     *
     * <pre>
     *   CharUtil.isAscii('a')  = true
     *   CharUtil.isAscii('A')  = true
     *   CharUtil.isAscii('3')  = true
     *   CharUtil.isAscii('-')  = true
     *   CharUtil.isAscii('\n') = true
     *   CharUtil.isAscii('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符处
     * @return {@code true}表示为ASCII字符
     */
    public static boolean isAscii(char ch) {
        return ch < ASCII_LENGTH;
    }

    /**
     * 是否为可见ASCII字符，可见字符位于32~126之间
     *
     * <pre>
     *   CharUtil.isVisibleAscii('a')  = true
     *   CharUtil.isVisibleAscii('A')  = true
     *   CharUtil.isVisibleAscii('3')  = true
     *   CharUtil.isVisibleAscii('-')  = true
     *   CharUtil.isVisibleAscii('\n') = false
     *   CharUtil.isVisibleAscii('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符处
     * @return {@code true}表示为ASCII可见字符
     */
    public static boolean isVisibleAscii(char ch) {
        return ch >= 32 && ch < 127;
    }

    /**
     * 是否为ASCII控制符（不可见字符），控制符位于0~31和127
     *
     * <pre>
     *   CharUtil.isControlAscii('a')  = false
     *   CharUtil.isControlAscii('A')  = false
     *   CharUtil.isControlAscii('3')  = false
     *   CharUtil.isControlAscii('-')  = false
     *   CharUtil.isControlAscii('\n') = true
     *   CharUtil.isControlAscii('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return {@code true}表示为控制符
     */
    public static boolean isControlAscii(final char ch) {
        return ch < 32 || ch == 127;
    }

    /**
     * 判断是否为字母（包括大写字母和小写字母）<br>
     * 字母包括A~Z和a~z
     *
     * <pre>
     *   CharUtil.isLetter('a')  = true
     *   CharUtil.isLetter('A')  = true
     *   CharUtil.isLetter('3')  = false
     *   CharUtil.isLetter('-')  = false
     *   CharUtil.isLetter('\n') = false
     *   CharUtil.isLetter('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return {@code true}表示为字母
     */
    public static boolean isLetter(char ch) {
        return isLetterUpper(ch) || isLetterLower(ch);
    }

    /**
     * <p>
     * 判断是否为大写字母，大写字母包括A~Z
     * </p>
     *
     * <pre>
     *   CharUtil.isLetterUpper('a')  = false
     *   CharUtil.isLetterUpper('A')  = true
     *   CharUtil.isLetterUpper('3')  = false
     *   CharUtil.isLetterUpper('-')  = false
     *   CharUtil.isLetterUpper('\n') = false
     *   CharUtil.isLetterUpper('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return {@code true}表示为大写字母
     */
    public static boolean isLetterUpper(final char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    /**
     * <p>
     * 检查字符是否为小写字母，小写字母指a~z
     * </p>
     *
     * <pre>
     *   CharUtil.isLetterLower('a')  = true
     *   CharUtil.isLetterLower('A')  = false
     *   CharUtil.isLetterLower('3')  = false
     *   CharUtil.isLetterLower('-')  = false
     *   CharUtil.isLetterLower('\n') = false
     *   CharUtil.isLetterLower('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return {@code true}表示为小写字母
     */
    public static boolean isLetterLower(final char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    /**
     * <p>
     * 检查是否为数字字符，数字字符指0~9
     * </p>
     *
     * <pre>
     *   CharUtil.isNumber('a')  = false
     *   CharUtil.isNumber('A')  = false
     *   CharUtil.isNumber('3')  = true
     *   CharUtil.isNumber('-')  = false
     *   CharUtil.isNumber('\n') = false
     *   CharUtil.isNumber('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return {@code true}表示为数字字符
     */
    public static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    /**
     * 是否为16进制规范的字符，判断是否为如下字符
     * <pre>
     * 1. 0~9
     * 2. a~f
     * 4. A~F
     * </pre>
     *
     * @param ch 字符
     * @return 是否为16进制规范的字符
     */
    public static boolean isHexChar(char ch) {
        return isNumber(ch) || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F');
    }

    /**
     * 是否为字母或数字，包括A~Z、a~z、0~9
     *
     * <pre>
     *   CharUtil.isLetterOrNumber('a')  = true
     *   CharUtil.isLetterOrNumber('A')  = true
     *   CharUtil.isLetterOrNumber('3')  = true
     *   CharUtil.isLetterOrNumber('-')  = false
     *   CharUtil.isLetterOrNumber('\n') = false
     *   CharUtil.isLetterOrNumber('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return {@code true}表示为字母或数字
     */
    public static boolean isLetterOrNumber(final char ch) {
        return isLetter(ch) || isNumber(ch);
    }


    /**
     * 是否空白符，空白符包括空格、制表符、全角空格和不间断空格
     *
     * @param ch 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(char ch) {
        return isBlankChar((int) ch);
    }

    /**
     * 是否空白符，空白符包括空格、制表符、全角空格和不间断空格
     *
     * @param codePoint 代码点
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(int codePoint) {
        return Character.isWhitespace(codePoint) || Character.isSpaceChar(codePoint) || codePoint == '\ufeff' ||
                codePoint == '\u202a' || codePoint == '\u0000';
    }

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param ch 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    public static boolean isFileSeparator(char ch) {
        return BACKSLASH == ch || SLASH == ch;
    }

    /**
     * 给定对象对应的类是否为字符类，字符类包括：
     *
     * <pre>
     * Character.class
     * char.class
     * </pre>
     *
     * @param value 被检查的对象
     * @return {@code true}表示为字符类
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean isChar(Object value) {
        if (value == null) {
            return false;
        }
        return value instanceof Character || value.getClass().equals(char.class);
    }

    /**
     * 比较两个字符是否相同
     *
     * @param src             源字符
     * @param dest            目标字符
     * @param caseInsensitive 忽略大小写
     * @return {@code true}相同,否则不同
     */
    public static boolean equals(char src, char dest, boolean caseInsensitive) {
        if (caseInsensitive) {
            return Character.toLowerCase(src) == Character.toLowerCase(dest);
        }
        return src == dest;
    }

    /**
     * 字符转为字符串
     *
     * @param ch 字符
     * @return 字符串
     */
    public static String toString(char ch) {
        return ch < ASCII_LENGTH ? CACHE[ch] : String.valueOf(ch);
    }

}
