package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import java.nio.charset.UnsupportedCharsetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain CharsetUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-31
 */
public class CharsetUtilTest {

    @Test
    public void defaultCharset() {
    }

    @Test
    public void defaultCharsetName() {
        String defaultCharsetName = CharsetUtil.defaultCharsetName();
        assertNotNull(defaultCharsetName);
    }

    @Test
    public void systemCharset() {
    }

    @Test
    public void systemCharsetName() {
        String systemCharsetName = CharsetUtil.systemCharsetName();
        if (FileUtil.isWindows()) {
            assertEquals(CharsetUtil.GBK_VALUE, systemCharsetName);
        } else {
            assertEquals(CharsetUtil.UTF_8_VALUE, systemCharsetName);
        }
    }

    @Test
    public void forName() {
        assertEquals(CharsetUtil.UTF_8, CharsetUtil.forName(""));
        assertEquals(CharsetUtil.UTF_8, CharsetUtil.forName(" "));
        assertEquals(CharsetUtil.UTF_8, CharsetUtil.forName("\t"));
        assertThrows(UnsupportedCharsetException.class, () ->
                assertEquals(CharsetUtil.UTF_8, CharsetUtil.forName("ISO")));
        assertEquals(CharsetUtil.ISO_8859_1_VALUE, CharsetUtil.forName("ISO-8859-1").name());
    }

    @Test
    public void testForName() {
    }

    @Test
    public void getCharset() {
        assertEquals(CharsetUtil.UTF_8, CharsetUtil.getCharset(CharsetUtil.UTF_8));
    }

    @Test
    void testGetCharset() {
    }

    @Test
    public void parseQuietly() {
        assertEquals(CharsetUtil.ISO_8859_1, CharsetUtil.parseQuietly("AAA", CharsetUtil.ISO_8859_1));
    }
}