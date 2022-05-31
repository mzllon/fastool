package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tester for {@linkplain MapUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-31
 */
public class MapUtilTest {

    @Test
    public void newHashMap() {
        HashMap<Object, Object> hashMap = MapUtil.newHashMap(8);
        assertNotNull(hashMap);
    }

    @Test
    public void newLinkedHashMap() {
        LinkedHashMap<Object, Object> hashMap = MapUtil.newLinkedHashMap(8);
        assertNotNull(hashMap);
    }

    @Test
    void isEmpty() {
    }

    @Test
    void isNotEmpty() {
    }
}