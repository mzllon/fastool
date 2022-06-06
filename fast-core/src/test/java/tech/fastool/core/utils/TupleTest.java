package tech.fastool.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@linkplain Tuple} Test
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class TupleTest {

    @Test
    public void getElements() {
        Tuple tuple = new Tuple(null, 1, "A");
        assertArrayEquals(new Object[]{null, 1, "A"}, tuple.getElements());
    }

    @Test
    public void get() {
        Tuple tuple = new Tuple(null, 1, "A", new Object());
        assertNull(tuple.get(0));
        assertEquals("A", tuple.get(2));
    }

    @Test
    public void testEquals() {
        Tuple tuple1 = new Tuple("A", 1, null);
        Tuple tuple2 = new Tuple("A", 1, null);
        assertEquals(tuple1, tuple2);
    }

    @Test
    public void testHashCode() {
        // ignore
    }

    @Test
    public void iterator() {
        Tuple tuple1 = new Tuple("A", 1, null);
        Object ele = tuple1.iterator().next();
        assertEquals("A", ele);
    }

    @Test
    public void forEach() {
    }

    @Test
    public void testToString() {

    }
}