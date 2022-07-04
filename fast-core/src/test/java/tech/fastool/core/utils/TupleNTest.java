package tech.fastool.core.utils;

import org.junit.jupiter.api.Test;
import tech.fastool.core.lang.tuple.TupleN;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@linkplain TupleN} Test
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class TupleNTest {

    @Test
    public void getElements() {
        TupleN tuple = new TupleN(null, 1, "A");
        assertArrayEquals(new Object[]{null, 1, "A"}, tuple.getElements());
    }

    @Test
    public void get() {
        TupleN tupleN = new TupleN(null, 1, "A", new Object());
        assertNull(tupleN.get(0));
        assertEquals("A", tupleN.get(2));
    }

    @Test
    public void testEquals() {
        TupleN tuple1 = new TupleN("A", 1, null);
        TupleN tuple2 = new TupleN("A", 1, null);
        assertEquals(tuple1, tuple2);
    }

    @Test
    public void testHashCode() {
        // ignore
    }

    @Test
    public void iterator() {
        TupleN tuple1 = new TupleN("A", 1, null);
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