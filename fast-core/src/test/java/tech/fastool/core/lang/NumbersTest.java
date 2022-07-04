package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;
import tech.fastool.core.exceptions.ArrayEmptyException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@linkplain Numbers}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class NumbersTest {

    @Test
    public void avoidScientificNotation() {
        double d = 1.2E+10;
        assertEquals("12000000000", Numbers.avoidScientificNotation(d));
    }

    @Test
    public void isDigits() {
        assertFalse(Numbers.isDigits(null));
        assertFalse(Numbers.isDigits(""));
        assertFalse(Numbers.isDigits("+"));
        assertFalse(Numbers.isDigits("+0"));
        assertFalse(Numbers.isDigits("+1"));
        assertFalse(Numbers.isDigits("1+1"));
    }

    @Test
    public void isNumeric() {

    }

    @Test
    public void isDecimal() {
        assertFalse(Numbers.isDecimal(null));
        assertFalse(Numbers.isDecimal(""));
        assertFalse(Numbers.isDecimal("++"));
        assertTrue(Numbers.isDecimal("+0"));
        assertTrue(Numbers.isDecimal("+1"));
        assertFalse(Numbers.isDecimal("1+1"));
    }

    @Test
    public void yuan2Fen() {
        double yuan = 0.001;
        assertEquals(0, Numbers.yuan2Fen(yuan));
        yuan = 0.01;
        assertEquals(1, Numbers.yuan2Fen(yuan));

    }

    @Test
    public void testYuan2Fen() {
        double yuan = 0.001;
        assertEquals(0, Numbers.yuan2Fen(yuan, false));
        assertEquals(0, Numbers.yuan2Fen(yuan, true));
        yuan = 0.01;
        assertEquals(1, Numbers.yuan2Fen(yuan, true));
        yuan = 1.226;
        assertEquals(123, Numbers.yuan2Fen(yuan, true));

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
        assertEquals("0", Numbers.yuan2FenString(yuan));
        yuan = "0.01";
        assertEquals("1", Numbers.yuan2FenString(yuan));
        yuan = "1.226";
        assertEquals("123", Numbers.yuan2FenString(yuan));
    }

    @Test
    public void testYuan2FenString() {
        long yuan = 0;
        assertEquals("0", Numbers.yuan2FenString(yuan));
        yuan = 1;
        assertEquals("100", Numbers.yuan2FenString(yuan));
    }

    @Test
    public void testYuan2FenString1() {
        double yuan = 0;
        assertEquals("0", Numbers.yuan2FenString(yuan));
        yuan = 1;
        assertEquals("100", Numbers.yuan2FenString(yuan));
        yuan = 0.01;
        assertEquals("1", Numbers.yuan2FenString(yuan));
        yuan = 0.016;
        assertEquals("2", Numbers.yuan2FenString(yuan));
    }

    @Test
    public void fen2Yuan() {
        long fen = 1022;
        assertEquals(10.22D, Numbers.fen2Yuan(fen));
    }

    @Test
    public void testFen2Yuan() {
    }

    @Test
    public void fen2YuanString() {
        String fen = "0.01";
        assertEquals("0.00", Numbers.fen2YuanString(fen));
        fen = "1005";
        assertEquals("10.05", Numbers.fen2YuanString(fen));

    }

    @Test
    public void min() {
        assertEquals(1, Numbers.min(1, 2, 4, 2, 1));
    }

    @Test
    public void testMin() {
        assertEquals(1L, Numbers.min(1L, 2L, 4L, 2L, 1L));

    }

    @Test
    public void testMin1() {
        assertThrows(ArrayEmptyException.class, () -> {
            short[] array = null;
            Numbers.min(array);
        });
        short[] array = new short[]{10, 2, 5, 7, 1};
        assertEquals((short) 1, Numbers.min(array));
    }

    @Test
    public void testMin2() {
        assertThrows(ArrayEmptyException.class, () -> {
            double[] array = null;
            Numbers.min(array);
        });
        double[] array = new double[]{10, 2, 5, 7, 1};
        assertEquals(1, Numbers.min(array));
    }

    @Test
    public void testMin3() {
        assertThrows(ArrayEmptyException.class, () -> {
            float[] array = null;
            Numbers.min(array);
        });
        float[] array = new float[]{10, 2, 5, 7, 1};
        assertEquals((float) 1, Numbers.min(array));
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
            Byte b = Numbers.convert(number, Byte.class);
        });
        AtomicLong atomicLong = Numbers.convert(number, AtomicLong.class);
        assertEquals(1024L, atomicLong.get());
    }

    @Test
    public void createNumber() {
        String str = null;
        assertNull(Numbers.createNumber(str));
        str = "";
        assertNull(Numbers.createNumber(str));
        str = " ";
        assertNull(Numbers.createNumber(str));
        str = "   ";
        assertNull(Numbers.createNumber(str));
        str = "--1";
        assertNull(Numbers.createNumber(str));
        str = "0xA1";
        assertEquals(161, Numbers.createNumber(str));
        str = "-0xE";
        assertEquals(-14, Numbers.createNumber(str));
        str = "10.14";
        assertEquals(10.14F, Numbers.createNumber(str));
        str = "10.2f";
        assertEquals(10.2f, Numbers.createNumber(str));
        assertThrows(NumberFormatException.class, () -> {
            Numbers.createNumber("A10");
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
        assertNull(Numbers.createBigDecimal(value));
        value = new BigDecimal("10.10");
        assertEquals(new BigDecimal("10.10"), Numbers.createBigDecimal(value));
        value = 1000L;
        assertEquals(new BigDecimal("1000"), Numbers.createBigDecimal(value));
        value = 211;
        assertEquals(new BigDecimal("211"), Numbers.createBigDecimal(value));
        value = new BigInteger("123123123123123123");
        assertEquals(new BigDecimal("123123123123123123"), Numbers.createBigDecimal(value));
        value = new AtomicInteger(100);
        assertEquals(new BigDecimal("100"), Numbers.createBigDecimal(value));

    }

    @Test
    public void testCreateBigDecimal() {
    }
}