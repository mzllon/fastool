package tech.fastool.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tester for {@linkplain Booleans}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class BooleansTest {

    @Test
    public void toChar() {
        assertEquals(1, Booleans.toChar(true));
        assertEquals(0, Booleans.toChar(false));
    }

    @Test
    public void toCharacter() {
        Character cr = 1;
        assertEquals(cr, Booleans.toCharacter(true));
    }

    @Test
    public void testToCharacter() {
        Boolean value = null;
        assertNull(Booleans.toCharacter(value));
        value = Boolean.TRUE;
        assertEquals(new Character((char) 1), Booleans.toCharacter(value));
    }

    @Test
    public void toInteger() {
        assertEquals(1, Booleans.toInteger(Boolean.TRUE));
        assertEquals(0, Booleans.toInteger(Boolean.FALSE));
        assertNull(Booleans.toInteger(null));
    }

}