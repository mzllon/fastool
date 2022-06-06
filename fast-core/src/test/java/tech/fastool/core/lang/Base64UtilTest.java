package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tester for {@linkplain Base64Util}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class Base64UtilTest {

    @Test
    public void encode() {
        // https://www.bejson.com/enc/base64/
        String str = "中国·杭州";
        assertEquals("5Lit5Zu9wrfmna3lt54=", Base64Util.encode(str));
    }

    @Test
    public void testEncode() {
        // https://www.bejson.com/enc/base64/
        String str = "中国·杭州";
        assertEquals("1tC5+qGkurzW3Q==", Base64Util.encode(str, CharsetUtil.GBK));
    }

    @Test
    public void testEncode2() {
        String base64Data = "SmF2YeWInee6p+eoi+W6j+WRmA==";
        String str = "Java初级程序员";
        assertArrayEquals(str.getBytes(), Base64Util.decode(base64Data));
        assertEquals(str, Base64Util.decodeToStr(base64Data));
        assertEquals(str, Base64Util.decodeToStr(base64Data, CharsetUtil.UTF_8_VALUE));

    }

    @Test
    public void testEncode3() {
        String str = "Java初级程序员";
        String base64Data = "SmF2YeWInee6p+eoi+W6j+WRmA==";
        String urlSafeBase64 = StringUtil.replace(base64Data, '+', '-');
        assertEquals(str, Base64Util.decodeToStr(urlSafeBase64, true));
    }

    @Test
    public void testEncode4() {
        String str = "IT界现在好卷";
        byte[] data = str.getBytes(CharsetUtil.UTF_8);
        assertEquals("SVTnlYznjrDlnKjlpb3ljbc=", Base64Util.encode(data));
        data = str.getBytes(CharsetUtil.GBK);
        assertEquals("SVS958/W1Nq6w77t", Base64Util.encode(data));
    }

    @Test
    public void testEncode5() {
        String str = "IT界现在好卷";
        byte[] data = str.getBytes(CharsetUtil.UTF_8);
        final InputStream in = new ByteArrayInputStream(data);
        assertEquals("SVTnlYznjrDlnKjlpb3ljbc=", Base64Util.encode(in));

    }

    @Test
    public void testEncode6() {
    }

    @Test
    public void decode() {
    }

    @Test
    public void testDecode() {
    }

    @Test
    public void decodeToStr() {
    }

    @Test
    public void testDecodeToStr() {
    }

    @Test
    public void testDecodeToStr1() {
    }

    @Test
    public void testDecodeToStr2() {
    }

    @Test
    public void testDecodeToStr3() {
    }

    @Test
    public void testDecodeToStr4() {
    }
}