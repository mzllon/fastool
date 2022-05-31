package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tester for {@linkplain ArrayUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
public class ArrayUtilTest {

    @Test
    public void isArray() {
        assertFalse(ArrayUtil.isArray(null));
        assertFalse(ArrayUtil.isArray("123"));
        assertFalse(ArrayUtil.isArray(123));
        assertTrue(ArrayUtil.isArray(new String[]{"aa", "bb"}));
        assertTrue(ArrayUtil.isArray(new int[]{}));
    }

    @Test
    public void isPrimitiveArray() {
        assertFalse(ArrayUtil.isPrimitiveArray(null));
        assertFalse(ArrayUtil.isPrimitiveArray(1));
        assertFalse(ArrayUtil.isPrimitiveArray(1L));
        assertFalse(ArrayUtil.isPrimitiveArray("1"));
        assertFalse(ArrayUtil.isPrimitiveArray(new String[]{}));
        assertTrue(ArrayUtil.isPrimitiveArray(new int[]{}));
        assertTrue(ArrayUtil.isPrimitiveArray(new byte[]{}));
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void testIsEmpty() {
    }

    @Test
    public void testIsEmpty1() {
    }

    @Test
    public void testIsEmpty2() {
    }

    @Test
    public void testIsEmpty3() {
    }

    @Test
    public void testIsEmpty4() {
    }

    @Test
    public void testIsEmpty5() {
    }

    @Test
    public void testIsEmpty6() {
    }

    @Test
    public void testIsEmpty7() {
    }

    @Test
    public void isNotEmpty() {
    }

    @Test
    public void testIsNotEmpty() {
        boolean[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new boolean[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new boolean[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new boolean[]{true};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty1() {
        byte[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new byte[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new byte[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new byte[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty2() {
        short[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new short[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new short[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new short[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty3() {
        int[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new int[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new int[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new int[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty4() {
        float[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new float[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new float[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new float[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty5() {
        double[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new double[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new double[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new double[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty6() {
        long[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new long[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new long[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new long[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsNotEmpty7() {
        String[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new String[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new String[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new String[]{"1"};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void testIsEmpty8() {
        char[] array = null;
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new char[0];
        assertFalse(ArrayUtil.isNotEmpty(array));
        array = new char[1];
        assertTrue(ArrayUtil.isNotEmpty(array));
        array = new char[]{1};
        assertTrue(ArrayUtil.isNotEmpty(array));
    }

    @Test
    public void isAnyNull() {
        String[] array = null;
        assertTrue(ArrayUtil.isAnyNull(array));
        array = new String[0];
        assertTrue(ArrayUtil.isAnyNull(array));
        array = new String[1];
        assertTrue(ArrayUtil.isAnyNull(array));
        array = new String[]{"1"};
        assertFalse(ArrayUtil.isAnyNull(array));
        array = new String[]{"1", null};
        assertTrue(ArrayUtil.isAnyNull(array));
    }

}