package tech.fastool.core.lang.regex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tester for {@linkplain RegexUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-14
 */
public class RegexUtilTest {

    @Test
    public void isMatch() {
        String hexStr = "e98008278401114d6e1ea10afa4abb57";
        assertTrue(RegexUtil.isMatch(Patterns.HEX_STR, hexStr));
    }

    @Test
    public void testIsMatch() {

    }
}