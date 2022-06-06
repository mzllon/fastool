package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;
import tech.fastool.core.lang.regex.Patterns;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for {@linkplain RandomUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class RandomUtilTest {

    @Test
    public void getRandom() {
        assertTrue(RandomUtil.getRandom() instanceof ThreadLocalRandom);
    }

    @Test
    public void nextBoolean() {
    }

    @Test
    public void nextInt() {
    }

    @Test
    public void testNextInt() {
    }

    @Test
    public void testNextInt1() {
    }

    @Test
    public void nextLong() {
    }

    @Test
    public void testNextLong() {
    }

    @Test
    public void testNextLong1() {
    }

    @Test
    public void nextDouble() {
    }

    @Test
    public void testNextDouble() {
    }

    @Test
    public void testNextDouble1() {
    }

    @Test
    public void element() {
    }

    @Test
    public void testElement() {
        List<String> elements = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
        String element = RandomUtil.element(elements, 4);
        assertTrue(elements.indexOf(element) <= 4);
    }

    @Test
    public void testElement1() {
    }

    @Test
    public void testElement2() {
    }

    @Test
    public void str() {
    }

    @Test
    public void testStr() {
    }

    @Test
    public void numberStr() {
        String code = RandomUtil.numberStr(6);
        assertTrue(Patterns.DIGITS.matcher(code).matches());
    }

    @Test
    public void strReadable() {
        String str = RandomUtil.strReadable(6);
        for (char ch : str.toCharArray()) {
            // 0 o O l 1 I i
            assertFalse(ch == '0' || ch == 'o' || ch == 'O' || ch == 'l' || ch == '1' || ch == 'I' || ch == 'i');
        }
    }

    @Test
    public void strExcludeStr() {
    }
}