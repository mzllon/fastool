package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;
import tech.fastool.core.exceptions.ArrayEmptyException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@linkplain NumberUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class NumberUtilTest {

    @Test
    public void avoidScientificNotation() {
        double d = 1.2E+10;
        assertEquals("12000000000", NumberUtil.avoidScientificNotation(d));
    }

    @Test
    public void isDigits() {
        assertFalse(NumberUtil.isDigits(null));
        assertFalse(NumberUtil.isDigits(""));
        assertFalse(NumberUtil.isDigits("+"));
        assertFalse(NumberUtil.isDigits("+0"));
        assertFalse(NumberUtil.isDigits("+1"));
        assertFalse(NumberUtil.isDigits("1+1"));
    }

    @Test
    public void isNumeric() {

    }

    @Test
    public void isDecimal() {
        assertFalse(NumberUtil.isDecimal(null));
        assertFalse(NumberUtil.isDecimal(""));
        assertFalse(NumberUtil.isDecimal("++"));
        assertTrue(NumberUtil.isDecimal("+0"));
        assertTrue(NumberUtil.isDecimal("+1"));
        assertFalse(NumberUtil.isDecimal("1+1"));
    }

    @Test
    public void yuan2Fen() {
        double yuan = 0.001;
        assertEquals(0, NumberUtil.yuan2Fen(yuan));
        yuan = 0.01;
        assertEquals(1, NumberUtil.yuan2Fen(yuan));

    }

    @Test
    public void testYuan2Fen() {
        double yuan = 0.001;
        assertEquals(0, NumberUtil.yuan2Fen(yuan, false));
        assertEquals(0, NumberUtil.yuan2Fen(yuan, true));
        yuan = 0.01;
        assertEquals(1, NumberUtil.yuan2Fen(yuan, true));
        yuan = 1.226;
        assertEquals(123, NumberUtil.yuan2Fen(yuan, true));

    }

    @Test
    public void testYuan2Fen1() {
    }

    @Test
    public void testYuan2Fen2() {
    }

    @Test
    public void testYuan2Fen3() {

    }

    @Test
    public void yuan2FenString() {
        String yuan = "0.001";
        assertEquals("0", NumberUtil.yuan2FenString(yuan));
        yuan = "0.01";
        assertEquals("1", NumberUtil.yuan2FenString(yuan));
        yuan = "1.226";
        assertEquals("123", NumberUtil.yuan2FenString(yuan));
    }

    @Test
    public void testYuan2FenString() {
        long yuan = 0;
        assertEquals("0", NumberUtil.yuan2FenString(yuan));
        yuan = 1;
        assertEquals("100", NumberUtil.yuan2FenString(yuan));
    }

    @Test
    public void testYuan2FenString1() {
        double yuan = 0;
        assertEquals("0", NumberUtil.yuan2FenString(yuan));
        yuan = 1;
        assertEquals("100", NumberUtil.yuan2FenString(yuan));
        yuan = 0.01;
        assertEquals("1", NumberUtil.yuan2FenString(yuan));
        yuan = 0.016;
        assertEquals("2", NumberUtil.yuan2FenString(yuan));
    }

    @Test
    public void fen2Yuan() {
        long fen = 1022;
        assertEquals(10.22D,NumberUtil.fen2Yuan(fen));
    }

    @Test
    public void testFen2Yuan() {
    }

    @Test
    public void fen2YuanString() {
        String fen = "0.01";
        assertEquals("0.00", NumberUtil.fen2YuanString(fen));
        fen = "1005";
        assertEquals("10.05", NumberUtil.fen2YuanString(fen));

    }

    @Test
    public void min() {
        assertEquals(1, NumberUtil.min(1, 2, 4, 2, 1));
    }

    @Test
    public void testMin() {
        assertEquals(1L, NumberUtil.min(1L, 2L, 4L, 2L, 1L));

    }

    @Test
    public void testMin1() {
        assertThrows(ArrayEmptyException.class, () -> {
            short[] array = null;
            NumberUtil.min(array);
        });
        short[] array = new short[]{10, 2, 5, 7, 1};
        assertEquals((short) 1, NumberUtil.min(array));
    }

    @Test
    public void testMin2() {
        assertThrows(ArrayEmptyException.class, () -> {
            double[] array = null;
            NumberUtil.min(array);
        });
        double[] array = new double[]{10, 2, 5, 7, 1};
        assertEquals(1, NumberUtil.min(array));
    }

    @Test
    public void testMin3() {
        assertThrows(ArrayEmptyException.class, () -> {
            float[] array = null;
            NumberUtil.min(array);
        });
        float[] array = new float[]{10, 2, 5, 7, 1};
        assertEquals((float) 1, NumberUtil.min(array));
    }

    @Test
    public void max() {
    }

    @Test
    public void testMax() {
    }

    @Test
    public void testMax1() {
    }

    @Test
    public void testMax2() {
    }

    @Test
    public void testMax3() {
    }

    @Test
    public void convert() {
        Number number = 1024;
        assertThrows(IllegalArgumentException.class, () -> {
            Byte b = NumberUtil.convert(number, Byte.class);
        });
        AtomicLong atomicLong = NumberUtil.convert(number, AtomicLong.class);
        assertEquals(1024L, atomicLong.get());
    }

    @Test
    public void createNumber() {
        String str = null;
        assertNull(NumberUtil.createNumber(str));
        str = "";
        assertNull(NumberUtil.createNumber(str));
        str = " ";
        assertNull(NumberUtil.createNumber(str));
        str = "   ";
        assertNull(NumberUtil.createNumber(str));
        str = "--1";
        assertNull(NumberUtil.createNumber(str));
        str = "0xA1";
        assertEquals(161, NumberUtil.createNumber(str));
        str = "-0xE";
        assertEquals(-14, NumberUtil.createNumber(str));
        str = "10.14";
        assertEquals(10.14F, NumberUtil.createNumber(str));
        str = "10.2f";
        assertEquals(10.2f, NumberUtil.createNumber(str));
        assertThrows(NumberFormatException.class, () -> {
            NumberUtil.createNumber("A10");
        });
    }

    @Test
    public void createInteger() {
    }

    @Test
    public void createFloat() {
    }

    @Test
    public void createLong() {
    }

    @Test
    public void createDouble() {
    }

    @Test
    public void createBigInteger() {
    }

    @Test
    public void createBigDecimal() {
        Number value = null;
        assertNull(NumberUtil.createBigDecimal(value));
        value = new BigDecimal("10.10");
        assertEquals(new BigDecimal("10.10"), NumberUtil.createBigDecimal(value));
        value = 1000L;
        assertEquals(new BigDecimal("1000"), NumberUtil.createBigDecimal(value));
        value = 211;
        assertEquals(new BigDecimal("211"), NumberUtil.createBigDecimal(value));
        value = new BigInteger("123123123123123123");
        assertEquals(new BigDecimal("123123123123123123"), NumberUtil.createBigDecimal(value));
        value = new AtomicInteger(100);
        assertEquals(new BigDecimal("100"), NumberUtil.createBigDecimal(value));

    }

    @Test
    public void testCreateBigDecimal() {
    }
}