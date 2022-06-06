package tech.fastool.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import tech.fastool.core.date.DatePattern;
import tech.fastool.core.date.DateUtil;
import tech.fastool.core.lang.CharUtil;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.core.lang.Validator;
import tech.fastool.core.lang.regex.Patterns;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 中国身份证工具类
 * <p>行政区划代码:https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E8%A1%8C%E6%94%BF%E5%8C%BA%E5%88%92%E4%BB%A3%E7%A0%81</p>
 * <p>另见中国人民共和国县以上行政区划代码:http://www.mca.gov.cn/article/sj/xzqh/2020/20201201.html</p>
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@UtilityClass
public final class IdCardUtil {

    /**
     * 老的中国公民身份证号码长度（15位）
     */
    public static final int OLD_CHINA_ID_CARD_LENGTH = 15;

    /**
     * 新的中国公民身份证号码长度（18位）
     */
    public static final int NEW_CHINA_ID_CARD_LENGTH = 18;

    /**
     * 每位加权因子
     */
    private static final int[] FACTORS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 省代码表
     */
    private static final Map<String, String> PROVINCE_CODES = new HashMap<>();

    static {
        PROVINCE_CODES.put("11", "北京");
        PROVINCE_CODES.put("12", "天津");
        PROVINCE_CODES.put("13", "河北");
        PROVINCE_CODES.put("14", "山西");
        PROVINCE_CODES.put("15", "内蒙古");
        PROVINCE_CODES.put("21", "辽宁");
        PROVINCE_CODES.put("22", "吉林");
        PROVINCE_CODES.put("23", "黑龙江");
        PROVINCE_CODES.put("31", "上海");
        PROVINCE_CODES.put("32", "江苏");
        PROVINCE_CODES.put("33", "浙江");
        PROVINCE_CODES.put("34", "安徽");
        PROVINCE_CODES.put("35", "福建");
        PROVINCE_CODES.put("36", "江西");
        PROVINCE_CODES.put("37", "山东");
        PROVINCE_CODES.put("41", "河南");
        PROVINCE_CODES.put("42", "湖北");
        PROVINCE_CODES.put("43", "湖南");
        PROVINCE_CODES.put("44", "广东");
        PROVINCE_CODES.put("45", "广西");
        PROVINCE_CODES.put("46", "海南");
        PROVINCE_CODES.put("50", "重庆");
        PROVINCE_CODES.put("51", "四川");
        PROVINCE_CODES.put("52", "贵州");
        PROVINCE_CODES.put("53", "云南");
        PROVINCE_CODES.put("54", "西藏");
        PROVINCE_CODES.put("61", "陕西");
        PROVINCE_CODES.put("62", "甘肃");
        PROVINCE_CODES.put("63", "青海");
        PROVINCE_CODES.put("64", "宁夏");
        PROVINCE_CODES.put("65", "新疆");
        PROVINCE_CODES.put("71", "台湾");
        PROVINCE_CODES.put("81", "香港");
        PROVINCE_CODES.put("82", "澳门");
        PROVINCE_CODES.put("83", "台湾");
        PROVINCE_CODES.put("91", "国外");

    }

    /**
     * 判断给定的字符串是否为有效的18位身份证号码
     *
     * @param value 被校验的字符串
     * @return true / false
     */
    public static boolean isValid18(String value) {
        return isValid18(value, true);
    }

    /**
     * 判断给定的字符串是否为有效的18位身份证号码
     *
     * @param value      被校验的字符串
     * @param ignoreCase 是否忽略大小写,{@code true}则忽略
     * @return true / false
     */
    public static boolean isValid18(String value, boolean ignoreCase) {
        if (StringUtil.isEmpty(value)) {
            return false;
        }
        if (NEW_CHINA_ID_CARD_LENGTH != value.length()) {
            return false;
        }
        String substr = value.substring(0, 17);
        if (!Patterns.DIGITS.matcher(substr).matches()) {
            return false;
        }

        // 省份代码
        String provinceCode = substr.substring(0, 2);
        if (!PROVINCE_CODES.containsKey(provinceCode)) {
            return false;
        }

        // 生日
        String birthday = substr.substring(6, 14);
        if (!Validator.isBirthday(birthday)) {
            return false;
        }

        // 判断校验码是否正确
        return CharUtil.equals(getCheckCode18(substr), value.charAt(17), ignoreCase);
    }

    /**
     * 获得第18位校验码
     *
     * @param code17 前17位字符
     * @return 第18位校验码字符
     */
    private static char getCheckCode18(String code17) {
        int sum = 0;
        char[] array = code17.toCharArray();
        for (int i = 0, length = array.length; i < length; i++) {
            sum += ((array[i] - '0') * FACTORS[i]);
        }
        int result = sum % 11;
        int code = (12 - result) % 11;
        return (code == 10) ? 'X' : ((char) ('0' + code));
    }

    /**
     * 根据身份证号码返回省份代码
     *
     * @param idCard 身份证号码
     * @return 省份编码
     */
    public static String getProvinceCode( String idCard) {
        if (NEW_CHINA_ID_CARD_LENGTH == idCard.length()) {
            return idCard.substring(0, 2);
        }
        return null;
    }

    /**
     * 根据身份证号码返回城市代码
     * 参见:http://www.mca.gov.cn/article/sj/xzqh/2020/20201201.html
     *
     * @param idCard 身份证号码
     * @return 城市编码
     */
    public static String getCityCode( String idCard) {
        if (NEW_CHINA_ID_CARD_LENGTH == idCard.length()) {
            return idCard.substring(0, 4) + "00";
        }
        return null;
    }

    /**
     * 根据身份证号码返回区县代码
     *
     * @param idCard 身份证号码
     * @return 区县编码
     */
    public static String getDistrictCode( String idCard) {
        if (NEW_CHINA_ID_CARD_LENGTH == idCard.length()) {
            return idCard.substring(0, 6);
        }
        return null;
    }

    /**
     * 根据身份证号码返回性别
     *
     * @param idCard 身份证号码
     * @return 性别
     */
    public static Gender getGender( String idCard) {
        if (NEW_CHINA_ID_CARD_LENGTH == idCard.length()) {
            throw new IllegalArgumentException("ID Card length must be 18");
        }
        char ch = idCard.charAt(16);
        return (ch % 2 != 0) ? Gender.MALE : Gender.FEMALE;
    }

    /**
     * 根据身份证号码返回出生日期
     *
     * @param idCard 身份证号码
     * @return 出生日期
     */
    public static String getBirthday(String idCard) {
        if (NEW_CHINA_ID_CARD_LENGTH == idCard.length()) {
            return idCard.substring(6, 14);
        }
        return null;
    }

    /**
     * 根据身份证号码返回出生日期
     *
     * @param idCard 身份证号码
     * @return 出生日期
     */
    public static Date getBirthday2(String idCard) {
        String birthday = getBirthday(idCard);
        return DateUtil.parse(birthday, DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 根据身份证号码返回年龄
     *
     * @param idCard 身份证号码
     * @return 年龄
     */
    public static int getAge( String idCard) {
        return getAge(idCard, DateUtil.now());
    }

    /**
     * 根据身份证号码返回年龄
     *
     * @param idCard       身份证号码
     * @param comparedDate 指定的日期
     * @return 年龄
     */
    public static int getAge( String idCard, Date comparedDate) {
        Date birthday = getBirthday2(idCard);
        return DateUtil.age(birthday, comparedDate);
    }

    @Getter
    public static class IdCardVo implements Serializable {

        private static final long serialVersionUID = 2021L;

        /**
         * 居民身份证号码
         */
        private final String idCard;

        /**
         * 省份代码
         */
        private final String provinceCode;

        /**
         * 城市代码
         */
        private final String cityCode;

        /**
         * 区县代码
         */
        private final String districtCode;

        /**
         * 性别
         */
        private final Gender gender;

        /**
         * 年龄
         */
        private final int age;

        public IdCardVo(String idCard) {
            this.idCard = idCard;
            this.provinceCode = IdCardUtil.getProvinceCode(idCard);
            this.cityCode = IdCardUtil.getCityCode(idCard);
            this.districtCode = IdCardUtil.getDistrictCode(idCard);
            this.gender = IdCardUtil.getGender(idCard);
            this.age = IdCardUtil.getAge(idCard);
        }

    }

    /**
     * 性别信息
     */
    @Getter
    @AllArgsConstructor
    public enum Gender {

        /**
         * 男
         */
        MALE(1, "男"),

        /***
         * 女
         */
        FEMALE(2, "女"),
        ;

        private final int code;

        private final String desc;

    }

}
