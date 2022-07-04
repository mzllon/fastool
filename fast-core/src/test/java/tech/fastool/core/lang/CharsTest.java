package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain Chars}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
public class CharsTest {

    @Test
    public void isAscii() {
        char ch = Chars.TAB;
        assertTrue(Chars.isAscii(ch));
        ch = '0';
        assertTrue(Chars.isAscii(ch));
        ch = ' ';
        assertTrue(Chars.isAscii(ch));
        ch = Chars.DOT;
        assertTrue(Chars.isAscii(ch));
        ch = Chars.COMMA;
        assertTrue(Chars.isAscii(ch));
        ch = Chars.UNDERLINE;
        assertTrue(Chars.isAscii(ch));
        ch = '。';
        assertFalse(Chars.isAscii(ch));
    }

    @Test
    public void isVisibleAscii() {
        assertTrue(Chars.isVisibleAscii(Chars.HYPHEN));
        assertFalse(Chars.isVisibleAscii(Chars.TAB));
        assertFalse(Chars.isVisibleAscii(Chars.CR));
        assertFalse(Chars.isVisibleAscii(Chars.LF));
        char ch = 255;
        assertFalse(Chars.isVisibleAscii(ch));
    }

    @Test
    public void isControlAscii() {
        char ch = 13;
        assertTrue(Chars.isControlAscii(ch));
        ch = 49;
        assertFalse(Chars.isControlAscii(ch));
    }

    @Test
    public void isLetter() {
    }

    @Test
    public void isLetterUpper() {
    }

    @Test
    public void isLetterLower() {
    }

    @Test
    public void isNumber() {
    }

    @Test
    public void isHexChar() {
        char ch = Chars.LEFT_CURLY_BRACKET;
        assertFalse(Chars.isHexChar(ch));
        ch = 'A';
        assertTrue(Chars.isHexChar(ch));
        ch = 'a';
        assertTrue(Chars.isHexChar(ch));
        ch = 'z';
        assertFalse(Chars.isHexChar(ch));
        ch = Chars.LEFT_MIDDLE_BRACKET;
        assertFalse(Chars.isHexChar(ch));
        ch = '2';
        assertTrue(Chars.isHexChar(ch));
        ch = '9';
        assertTrue(Chars.isHexChar(ch));
    }

    @Test
    public void isLetterOrNumber() {
        char ch = Chars.LEFT_CURLY_BRACKET;
        assertFalse(Chars.isLetterOrNumber(ch));
        ch = 'A';
        assertTrue(Chars.isLetterOrNumber(ch));
        ch = 'z';
        assertTrue(Chars.isLetterOrNumber(ch));
        ch = Chars.RIGHT_CURLY_BRACKET;
        assertFalse(Chars.isLetterOrNumber(ch));
        ch = '2';
        assertTrue(Chars.isLetterOrNumber(ch));
    }

    @Test
    public void isBlankChar() {
        char ch = Chars.SPACE;
        assertTrue(Chars.isBlankChar(ch));
        ch = Chars.TAB;
        assertTrue(Chars.isBlankChar(ch));
        ch = '0';
        assertFalse(Chars.isBlankChar(ch));
    }

    @Test
    public void testIsBlankChar() {
    }

    @Test
    public void isFileSeparator() {
        char ch = '/';
        assertTrue(Chars.isFileSeparator(ch));
        ch = '\\';
        assertTrue(Chars.isFileSeparator(ch));
        ch = '0';
        assertFalse(Chars.isFileSeparator(ch));
    }

    @Test
    public void isChar() {
        Integer i = 10;
        assertFalse(Chars.isChar(i));
        char ch = Chars.SINGLE_QUOTE;
        assertTrue(Chars.isChar(ch));

    }

    @Test
    public void testEquals() {
        char ch = '"';
        assertTrue(Chars.equals(ch, Chars.DOUBLE_QUOTE, false));
        ch = 'A';
        char c1 = 'a';
        assertFalse(Chars.equals(ch, c1, false));
        assertTrue(Chars.equals(ch, c1, true));
    }

    @Test
    public void testToString() {
        assertEquals("*", Chars.toString(Chars.STAR));
        assertEquals("&", Chars.toString(Chars.AMP));
        assertEquals("%", Chars.toString(Chars.PERCENT));
        assertEquals("@", Chars.toString(Chars.AT));
    }

}