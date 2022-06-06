package tech.fastool.all;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tester for {@linkplain  $}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-05
 */
public class $Test {

    @Test
    public void requireNonNull() {
        assertThrows(NullPointerException.class, () -> {
            Object obj = null;
            $.requireNonNull(obj);
        });
        Object o1 = "";
        assertEquals("", $.requireNonNull(o1));
    }

    @Test
    void isNull() {
    }

    @Test
    void isAnyNull() {
    }

    @Test
    void isAllNull() {
    }

    @Test
    void nonNull() {
    }

    @Test
    void isNotNull() {
    }

    @Test
    void requireNotEmpty() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void testIsEmpty() {
    }

    @Test
    void isNotEmpty() {
    }

    @Test
    void testIsNotEmpty() {
    }

    @Test
    void isAnyEmpty() {
    }

    @Test
    void safeEquals() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void compare() {
    }

    @Test
    void testCompare() {
    }

    @Test
    void testCompare1() {
    }

    @Test
    void getIfNull() {
    }

    @Test
    void testGetIfNull() {
    }

    @Test
    void isArray() {
    }

    @Test
    void isPrimitiveArray() {
    }

    @Test
    void getLength() {
    }

    @Test
    void newArray() {
    }

    @Test
    void getComponentType() {
    }

    @Test
    void testGetComponentType() {
    }

    @Test
    void testToString() {
    }

    @Test
    void indexOf() {
    }

    @Test
    void contains() {
    }

    @Test
    void testIsEmpty1() {
    }

    @Test
    void testIsNotEmpty1() {
    }

    @Test
    void join() {
    }

    @Test
    void testJoin() {
    }

    @Test
    void testJoin1() {
    }

    @Test
    void testJoin2() {
    }

    @Test
    void testJoin3() {
    }

    @Test
    void addAll() {
    }

    @Test
    void merge() {
    }

    @Test
    void newArrayList() {
    }

    @Test
    void testIsEmpty2() {
    }

    @Test
    void testIsNotEmpty2() {
    }

    @Test
    void isBlank() {
    }

    @Test
    void isNotBlank() {
    }

    @Test
    void isNotBlankInWebEnv() {
    }

    @Test
    void isAnyBlank() {
    }

    @Test
    void isAllBlank() {
    }

    @Test
    void isAnyNotBlank() {
    }

    @Test
    void isAllNotBlank() {
    }

    @Test
    void testIsAnyNotBlank() {
    }

    @Test
    void testIsAllNotBlank() {
    }

    @Test
    void str() {
    }

    @Test
    void testStr() {
    }

    @Test
    void trim() {
    }

    @Test
    void toMap() {
    }

    @Test
    void toBean() {
    }

    @Test
    void copyProperties() {
    }

    @Test
    void testCopyProperties() {
    }
}