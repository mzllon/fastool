package tech.fastool.core.lang;

import tech.fastool.core.exceptions.DecoderRuntimeException;

import java.nio.charset.Charset;

/**
 * Hex Utilities
 *
 * @author miles.tang at 2022-01-27
 */
public final class Hexes {

    /**
     * Don't let anyone instantiate this class
     */
    private Hexes() {
        throw new AssertionError("Cannot create instance!");
    }

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将字节数组转为16进制小写字符串
     *
     * @param data 字节数组内容
     * @return 16进制字符串
     */
    public static String encodeToStr(final byte[] data) {
        return encodeToStr(data, true);
    }

    /**
     * 将字节数组转为16进制字符串
     *
     * @param data    字节数组内容
     * @param isLower 是否为小写
     * @return 16进制字符串
     */
    public static String encodeToStr(final byte[] data, final boolean isLower) {
        return new String(encode(data, isLower));
    }

    /**
     * 将字节数组转为字符数组
     *
     * @param data 字节数组
     * @return 字符数组
     */
    public static char[] encode(final byte[] data) {
        return encode(data, true);
    }

    /**
     * 将字节转为字符数组
     *
     * @param data        字节数组内容
     * @param toLowerCase {@code true}表示转小写，否则转大写
     * @return 字符数组
     */
    public static char[] encode(final byte[] data, final boolean toLowerCase) {
        return doEncode(Objects.requireNonNull(data), toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 解码,转为原始字符串是采用{@code UTF-8}编码
     *
     * @param data 16进制的字符串
     * @return 原始字符串内容
     */
    public static String decodeToStr(final String data) {
        return decodeToStr(data, null);
    }

    /**
     * 解码,转为原始字符串是采用{@code encoding}编码
     *
     * @param data           16进制的字符串
     * @param outputEncoding 编码
     * @return 原始字符串内容
     */
    public static String decodeToStr(final String data, final Charset outputEncoding) {
        byte[] decode = decode(data);
        return new String(decode, Charsets.getCharset(outputEncoding));
    }

    /**
     * 解码
     *
     * @param data 16进制的字符串
     * @return 字节数组
     */
    public static byte[] decode(final String data) {
        return decode(Objects.requireNonNull(data).toCharArray());
    }

    /**
     * 解码
     *
     * @param data 16进制的字符数组
     * @return 字节数组
     */
    public static byte[] decode(final char[] data) {
        return doDecode(data);
    }

    /**
     * 编码：将字节数组转为16进制的字符数组
     *
     * @param data   字节数组内容
     * @param digits 转码表
     * @return 16进制的字符数组
     */
    private static char[] doEncode(final byte[] data, final char[] digits) {
        final int length = data.length;
        final char[] out = new char[length << 1];
        //byte转hex原理：一个byte转成2个16进制
        for (int i = 0, j = 0; i < length; i++) {
            out[j++] = digits[(0xF0 & data[i]) >>> 4];
            out[j++] = digits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * 解码：将16进制转为字节数组
     *
     * @param data 16进制的字符数组
     * @return 字节数组
     */
    private static byte[] doDecode(final char[] data) {
        final int length = data.length;
        if ((length & 0x01) != 0) {
            throw new DecoderRuntimeException("Odd number of characters.");
        }

        final byte[] out = new byte[length >> 1];
        //hex转byte原理：两个hex得一个byte
        int temp;
        for (int i = 0, j = 0; j < length; i++) {
            temp = toDigit(data[j], j) << 4;
            j++;
            temp = temp | toDigit(data[j], j);
            j++;
            out[i] = (byte) (temp & 0XFF);
        }
        return out;
    }

    /**
     * 16进制转10进制
     *
     * @param ch    16进制的字符
     * @param index 位置
     * @return 10进制数字
     */
    private static int toDigit(final char ch, int index) {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new DecoderRuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

}
