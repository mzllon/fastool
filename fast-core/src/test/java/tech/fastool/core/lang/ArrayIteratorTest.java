package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain ArrayIterator}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ArrayIteratorTest {

    @Test
    public void test() {
        ArrayIterator<Integer> arrayIterator = new ArrayIterator<>(new Integer[]{7, 4, 9, 1});
        assertTrue(arrayIterator.hasNext());
        Integer value = arrayIterator.next();
        assertEquals(7, value);
        assertTrue(arrayIterator.hasNext());
        value = arrayIterator.next();
        assertEquals(4, value);
        assertTrue(arrayIterator.hasNext());
        value = arrayIterator.next();
        assertEquals(9, value);
        assertTrue(arrayIterator.hasNext());
        value = arrayIterator.next();
        assertEquals(1, value);
        assertFalse(arrayIterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> {
            Integer ignore = arrayIterator.next();
        });
        arrayIterator.reset();
        assertTrue(arrayIterator.hasNext());
        value = arrayIterator.next();
        assertEquals(7, value);
        assertThrows(UnsupportedOperationException.class, arrayIterator::remove);
    }

}