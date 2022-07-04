package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import java.nio.charset.UnsupportedCharsetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for {@linkplain Charsets}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-31
 */
public class CharsetsTest {

    @Test
    public void defaultCharset() {
    }

    @Test
    public void defaultCharsetName() {
        String defaultCharsetName = Charsets.defaultCharsetName();
        assertNotNull(defaultCharsetName);
    }

    @Test
    public void systemCharset() {
    }

    @Test
    public void systemCharsetName() {
        String systemCharsetName = Charsets.systemCharsetName();
        if (Files.isWindows()) {
            assertEquals(Charsets.GBK_VALUE, systemCharsetName);
        } else {
            assertEquals(Charsets.UTF_8_VALUE, systemCharsetName);
        }
    }

    @Test
    public void forName() {
        assertEquals(Charsets.UTF_8, Charsets.forName(""));
        assertEquals(Charsets.UTF_8, Charsets.forName(" "));
        assertEquals(Charsets.UTF_8, Charsets.forName("\t"));
        assertThrows(UnsupportedCharsetException.class, () ->
                assertEquals(Charsets.UTF_8, Charsets.forName("ISO")));
        assertEquals(Charsets.ISO_8859_1_VALUE, Charsets.forName("ISO-8859-1").name());
    }

    @Test
    public void testForName() {
    }

    @Test
    public void getCharset() {
        assertEquals(Charsets.UTF_8, Charsets.getCharset(Charsets.UTF_8));
    }

    @Test
    void testGetCharset() {
    }

    @Test
    public void parseQuietly() {
        assertEquals(Charsets.ISO_8859_1, Charsets.parseQuietly("AAA", Charsets.ISO_8859_1));
    }
}