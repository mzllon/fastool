package tech.fastool.core.lang;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain  ObjectUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-03-31
 */
public class ObjectUtilTest {

    @Test
    public void isEmpty() {
    }

    @Test
    public void isNotEmpty() {
        Object obj = null;
        assertFalse(ObjectUtil.isNotEmpty(obj));
        obj = "";
        assertFalse(ObjectUtil.isNotEmpty(obj));
        obj = 1;
        assertTrue(ObjectUtil.isNotEmpty(obj));
        obj = " ";
        assertTrue(ObjectUtil.isNotEmpty(obj));
        obj = "\n";
        assertTrue(ObjectUtil.isNotEmpty(obj));
        obj = new int[0];
        assertFalse(ObjectUtil.isNotEmpty(obj));
        obj = new Integer[2];
        assertTrue(ObjectUtil.isNotEmpty(obj)); // 数组不为空就代表对象不为空
        obj = new String[]{null, null};
        assertTrue(ObjectUtil.isNotEmpty(obj));

    }

    @Test
    public void isAnyEmpty() {
        Object[] array = null;
        assertTrue(ObjectUtil.isAnyEmpty(array));
        array = new Object[0];
        assertTrue(ObjectUtil.isAnyEmpty(array));
        array = new Object[2];
        assertTrue(ObjectUtil.isAnyEmpty(array));
        array = new Object[]{null, "1"};
        assertTrue(ObjectUtil.isAnyEmpty(array));
        array = new Object[]{"", 1};
        assertTrue(ObjectUtil.isAnyEmpty(array));
        array = new Object[]{"1", 0};
        assertFalse(ObjectUtil.isAnyEmpty(array));
    }

    @Test
    public void isNull() {
    }

    @Test
    public void isAnyNull() {
    }

    @Test
    public void isAllNull() {
    }

    @Test
    public void isNoneNull() {
        Object[] array = null;
        assertTrue(ObjectUtil.isNoneNull(array));
        array = new Object[]{};
        assertTrue(ObjectUtil.isNoneNull(array));
        array = new Object[2];
        assertTrue(ObjectUtil.isNoneNull(array));
        array = new Object[]{null, null, null};
        assertTrue(ObjectUtil.isNoneNull(array));
        array = new Object[]{"", null};
        assertFalse(ObjectUtil.isNoneNull(array));
    }

    @Test
    public void nonNull() {
    }

    @Test
    public void isNotNull() {
        Object obj = null;
        assertFalse(ObjectUtil.isNotNull(obj));
        obj = "";
        assertTrue(ObjectUtil.isNotNull(obj));
        obj = 1;
        assertTrue(ObjectUtil.isNotNull(obj));
    }

    @Test
    public void nullSafeEquals() {
        Object a = null, b = null;
        assertTrue(ObjectUtil.nullSafeEquals(a, b));
        a = 1;
        assertFalse(ObjectUtil.nullSafeEquals(a, b));
        b = 2;
        assertFalse(ObjectUtil.nullSafeEquals(a, b));
        b = 1;
        assertTrue(ObjectUtil.nullSafeEquals(a, b));
        a = "";
        assertFalse(ObjectUtil.nullSafeEquals(a, b));
        a = new Object[]{1, 2};
        b = new Object[]{1, 2};
        assertTrue(ObjectUtil.nullSafeEquals(a, b));
        a = new int[]{0, 1};
        b = new int[]{0, 1};
        assertTrue(ObjectUtil.nullSafeEquals(a, b));
        a = new String[]{"a", "b"};
        b = new String[]{"a", "b"};
        assertTrue(ObjectUtil.nullSafeEquals(a, b));

    }

    @Test
    public void testEquals() {
        Object a = null, b = null;
        assertTrue(ObjectUtil.equals(a, b));
        a = 1;
        assertFalse(ObjectUtil.equals(a, b));
        b = 2;
        assertFalse(ObjectUtil.equals(a, b));
        b = 1;
        assertTrue(ObjectUtil.equals(a, b));
        a = "";
        assertFalse(ObjectUtil.equals(a, b));
        a = new Object[]{1, 2};
        b = new Object[]{1, 2};
        assertFalse(ObjectUtil.equals(a, b));
    }

    @Test
    public void compare() {
        Integer a = null, b = null;
        assertEquals(0, ObjectUtil.compare(a, b));
        a = 10;
        assertEquals(1, ObjectUtil.compare(a, b));
        b = 10;
        assertEquals(0, ObjectUtil.compare(a, b));
        a = 20;
        assertEquals(1, ObjectUtil.compare(a, b));
        String str1 = "Abc", str2 = "abc";
        assertEquals(-32, ObjectUtil.compare(str1, str2));
    }

    @Test
    public void testCompare() {
        Apple apple1 = new Apple(1L, 10), apple2 = new Apple(2L, 5),
                apple3 = new Apple(3L, 12);
        Comparator<Apple> comparator = new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.weight.compareTo(o2.weight);
            }
        };
        assertEquals(1, ObjectUtil.compare(apple1, apple2, comparator));
        assertEquals(-1, ObjectUtil.compare(apple2, apple3, comparator));
    }

    @Test
    public void testCompare1() {
    }

    @Test
    public void testHashCode() {
        Object a = null;
        assertEquals(0, ObjectUtil.hashCode(a));
        Integer val = 100;
        assertEquals(val, ObjectUtil.hashCode(val));
    }

    @Test
    public void getIfNull() {
        assertEquals("a", ObjectUtil.getIfNull(null, "a"));
        assertEquals("res", ObjectUtil.getIfNull("res", "a"));
    }

    @Test
    public void testGetIfNull() {
        assertEquals("a", ObjectUtil.getIfNull(null, (Supplier<Object>) () -> "a"));
        assertEquals("res", ObjectUtil.getIfNull("res", (Supplier<Object>) () -> "a"));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Apple implements Serializable {

        private Long id;

        private Integer weight;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Apple)) return false;
            Apple apple = (Apple) o;
            return id.equals(apple.id) && weight.equals(apple.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, weight);
        }
    }
}