package tech.fastool.core.lang.regex;

import tech.fastool.core.lang.SimpleCache;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 常见的正则表达式：<a href="https://any86.github.io/any-rule/">https://any86.github.io/any-rule/</a>
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class Patterns {

    /**
     * 英文字母 、数字和下划线
     */
    public final static Pattern GENERAL = Pattern.compile("^\\w+$");

    /**
     * 数字:正整数,0和负整数
     */
    public final static Pattern NUMBERS = Pattern.compile("^[-+]?\\d+$");

    /**
     * 纯数字
     */
    public static final Pattern DIGITS = Pattern.compile("^\\d+$");

    /**
     * 金额,2位小数点
     */
    public static final Pattern MONEY = Pattern.compile("\\d+(.\\d{2})?");

    /**
     * 任意一个中文/汉字
     */
    public static final Pattern CHINESE_ANY = Pattern.compile("[\u4E00-\u9FFF！|，。（）《》“”？：；【】]");

    /**
     * 多个中文
     */
    public static final Pattern CHINESES = Pattern.compile("[\u4E00-\u9FFF！|，。（）《》“”？：；【】]+");

    /**
     * Http(s) Or SFT(s)
     */
    public final static Pattern URL_HTTP_OR_FTP = Pattern.compile("^(((ht|f)tps?)://)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$");

    /**
     * IP v4
     */
    public final static Pattern IPV4 = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]).){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(?::(?:[0-9]|[1-9][0-9]{1,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]))?$");

    /**
     * IP v6
     */
    public final static Pattern IPV6 = Pattern.compile("^(?:(?:(?:[0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))|\\[(?:(?:(?:[0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))\\](?::(?:[0-9]|[1-9][0-9]{1,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]))?$", Pattern.CASE_INSENSITIVE);

    /**
     * 火车车次
     */
    public static final Pattern TRAIN_CODE = Pattern.compile("^[GCDZTSPKXLY1-9]\\d{1,4}$");

    /**
     * 手机机身码(IMEI)
     */
    public static final Pattern IMEI = Pattern.compile("^\\d{15,17}$");

    /**
     * 车牌号(新能源+非新能源)
     */
    public static final Pattern PLATE_CODE = Pattern.compile("^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[ABCDEFGHJK])|([ABCDEFGHJK]([A-HJ-NP-Z0-9])[0-9]{4})))|" +
            "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]\\d{3}\\d{1,3}[领])|" +
            "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$");

    /**
     * 手机号(mobile phone)中国(严谨), 根据工信部2019年最新公布的手机号段
     */
    public static final Pattern MOBILE_PHONE_STRICT = Pattern.compile("^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[189]))\\d{8}$");

    /**
     * 手机号(mobile phone)中国(宽松), 只要是13,14,15,16,17,18,19开头即可
     */
    public static final Pattern MOBILE_PHONE_COMPATIBLE = Pattern.compile("^(?:(?:\\+|00)86)?1[3-9]\\d{9}$");

    /**
     * 手机号(mobile phone)中国(最宽松), 只要是1开头即可, 如果你的手机号是用来接收短信, 优先建议选择这一条
     */
    public static final Pattern MOBILE_PHONE = Pattern.compile("^(?:(?:\\+|00)86)?1\\d{10}$");

    /**
     * date(日期)
     */
    public static final Pattern DATE = Pattern.compile("^\\d{4}(-)(1[0-2]|0?\\d)\\1([0-2]\\d|\\d|30|31)$");

    /**
     * email(邮箱)
     * 正则来自：http://emailregex.com/
     */
    public static final Pattern EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", Pattern.CASE_INSENSITIVE);

    /**
     * 社会统一信用代码
     * <pre>
     * 第一部分：登记管理部门代码1位 (数字或大写英文字母)
     * 第二部分：机构类别代码1位 (数字或大写英文字母)
     * 第三部分：登记管理机关行政区划码6位 (数字)
     * 第四部分：主体标识码（组织机构代码）9位 (数字或大写英文字母)
     * 第五部分：校验码1位 (数字或大写英文字母)
     * </pre>
     */
    public static final Pattern CREDIT_CODE = Pattern.compile("^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$");

    /**
     * 身份证号(1代,15位数字)
     */
    public static final Pattern ID_CARD_NUMBER_15 = Pattern.compile("^[1-9]\\d{7}(?:0\\d|10|11|12)(?:0[1-9]|[1-2][\\d]|30|31)\\d{3}$");

    /**
     * 身份证号(2代,18位数字),最后一位是校验位,可能为数字或字符X
     */
    public static final Pattern ID_CARD_NUMBER_18 = Pattern.compile("^[1-9]\\d{5}(?:18|19|20|21)\\d{2}(?:0[1-9]|10|11|12)(?:0[1-9]|[1-2]\\d|30|31)\\d{3}[\\dXx]$");

    /**
     * 身份证号, 支持1/2代(15位/18位数字)
     */
    public static final Pattern ID_CARD_NUMBER = Pattern.compile("(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0[1-9]|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)");

    /**
     * 中国的邮箱编码
     */
    public static final Pattern ZIPCODE = Pattern.compile("^(0[1-7]|1[0-356]|2[0-7]|3[0-6]|4[0-7]|5[1-7]|6[1-7]|7[0-5]|8[013-6])\\d{4}$");

    /**
     * mac地址
     */
    public static final Pattern MAC_ADDR = Pattern.compile("^((([a-f0-9]{2}:){5})|(([a-f0-9]{2}-){5}))[a-f0-9]{2}$", Pattern.CASE_INSENSITIVE);

    /**
     * 时间正则，如
     * 12:00
     * 12:00:00
     * 9:3
     */
    public static final Pattern TIME = Pattern.compile("^\\d{1,2}:\\d{1,2}(:\\d{1,2})?$");

    /**
     * 生日,支持形如
     * 2022-1-2
     * 2022/1/2
     * 2022年1月2日
     * 2022.1.2
     */
    public static final Pattern BIRTHDAY = Pattern.compile("^(\\d{2,4})([/\\-.年]?)(\\d{1,2})([/\\-.月]?)(\\d{1,2})日?$");

    // ---

    /**
     * Pattern Pool
     */
    private static final SimpleCache<RegexWithFlags, Pattern> POOL = new SimpleCache<>(128);

    /**
     * 先从Pattern池中查找正则对应的{@link Pattern}，找不到则编译正则表达式并入池。
     *
     * @param regex 正则表达式
     * @return {@link Pattern}
     */
    public static Pattern get(String regex) {
        return get(regex, 0);
    }

    /**
     * 先从Pattern池中查找正则对应的{@link Pattern}，找不到则编译正则表达式并入池。
     *
     * @param regex 正则表达式
     * @return {@link Pattern}
     */
    public static Pattern get(String regex, int flags) {
        return POOL.computeIfAbsent(new RegexWithFlags(regex, flags), regexWithFlags -> Pattern.compile(regex, flags));
    }

    /**
     * 移除缓存
     *
     * @param regex 正则
     * @param flags 标识
     * @return 移除的{@link Pattern}，可能为{@code null}
     */
    public static Pattern remove(String regex, int flags) {
        return POOL.remove(new RegexWithFlags(regex, flags));
    }

    /**
     * 清空缓存池
     */
    public static void clear() {
        POOL.clear();
    }

    /**
     * regex with flags
     */
    private static class RegexWithFlags {

        private final String regex;

        private final int flags;


        private RegexWithFlags(String regex, int flags) {
            this.regex = regex;
            this.flags = flags;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RegexWithFlags that = (RegexWithFlags) o;
            return flags == that.flags &&
                    regex.equals(that.regex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(regex, flags);
        }
    }

}
