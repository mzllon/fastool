package tech.fastool.core.lang.regex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tester for {@linkplain Regexes}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-14
 */
public class RegexesTest {

    @Test
    public void isMatch() {
        String hexStr = "e98008278401114d6e1ea10afa4abb57";
        assertTrue(Regexes.isMatch(PatternPool.HEX_STR, hexStr));
    }

    @Test
    public void testIsMatch() {

    }
}