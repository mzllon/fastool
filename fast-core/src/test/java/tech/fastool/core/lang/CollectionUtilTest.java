package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain CollectionUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-31
 */
public class CollectionUtilTest {

    @Test
    public void isEmpty() {
        Set<String> sets = null;
        assertTrue(CollectionUtil.isEmpty(sets));
        sets = new HashSet<>();
        assertTrue(CollectionUtil.isEmpty(sets));
        sets.add(null);
        assertFalse(CollectionUtil.isEmpty(sets));
    }

    @Test
    public void isNotEmpty() {
        Set<String> sets = null;
        assertFalse(CollectionUtil.isNotEmpty(sets));
        sets = new HashSet<>();
        assertFalse(CollectionUtil.isNotEmpty(sets));
        sets.add(null);
        assertTrue(CollectionUtil.isNotEmpty(sets));
    }

    @Test
    public void join() {
        List<String> list = null;
        assertNull(CollectionUtil.join(list));
        list = new ArrayList<>();
        assertEquals("", CollectionUtil.join(list));
        list.add(null);
        assertEquals("", CollectionUtil.join(list));
        list.add("A");
        assertEquals("A", CollectionUtil.join(list));
        list.add("Op");
        assertEquals("A,Op", CollectionUtil.join(list));

    }

    @Test
    public void testJoin() {
        String separator = "|";
        List<String> list = new ArrayList<>();
        list.add("A");
        assertEquals("A", CollectionUtil.join(list, separator));
        list.add("Op");
        assertEquals("A|Op", CollectionUtil.join(list, separator));
    }

    @Test
    public void testJoin1() {
        List<String> sets = new ArrayList<>();
        sets.add("fAST");
        String separator = "^";
        boolean ignoreNull = false, sortable = true;
        assertEquals("fAST", CollectionUtil.join(sets, separator, ignoreNull, sortable));
        sets.add("Author");
        assertEquals("Author^fAST", CollectionUtil.join(sets, separator, ignoreNull, sortable));
        sortable = false;
        assertEquals("fAST^Author", CollectionUtil.join(sets, separator, ignoreNull, sortable));
    }

    @Test
    void testJoin2() {
    }

    @Test
    void testJoin3() {
    }

    @Test
    public void addAll() {

    }

    @Test
    public void mergeToColl() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        String[] elements = {"A", "B"};

        Collection<String> expected = Arrays.asList("A", "B", "C", "A", "B");
        assertEquals(expected, CollectionUtil.mergeToColl(list, elements));

        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("C");

        expected = new HashSet<>();
        expected.add("A");
        expected.add("C");
        expected.add("B");
        assertEquals(expected, set);

    }
}