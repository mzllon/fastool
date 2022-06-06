package tech.fastool.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tester for {@linkplain BooleanUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class BooleanUtilTest {

    @Test
    public void toChar() {
        assertEquals(1, BooleanUtil.toChar(true));
        assertEquals(0, BooleanUtil.toChar(false));
    }

    @Test
    public void toCharacter() {
        Character cr = 1;
        assertEquals(cr, BooleanUtil.toCharacter(true));
    }

    @Test
    public void testToCharacter() {
        Boolean value = null;
        assertNull(BooleanUtil.toCharacter(value));
        value = Boolean.TRUE;
        assertEquals(new Character((char) 1), BooleanUtil.toCharacter(value));
    }

    @Test
    public void toInteger() {
        assertEquals(1, BooleanUtil.toInteger(Boolean.TRUE));
        assertEquals(0, BooleanUtil.toInteger(Boolean.FALSE));
        assertNull(BooleanUtil.toInteger(null));
    }

}