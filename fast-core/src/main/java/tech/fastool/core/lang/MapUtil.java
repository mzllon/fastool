package tech.fastool.core.lang;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-31
 */
@UtilityClass
final public class MapUtil {

    /**
     * Default load factor for {@link HashMap}/{@link LinkedHashMap} variants.
     *
     * @see #newHashMap(int)
     * @see #newLinkedHashMap(int)
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // region instance

    /**
     * 创建{@linkplain HashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键
     * @param <V>          值
     * @return HashMap实例
     */
    public static <K, V> HashMap<K, V> newHashMap(int expectedSize) {
        return new HashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    /**
     * 创建{@linkplain LinkedHashMap}
     *
     * @param expectedSize 期望的大小
     * @param <K>          键
     * @param <V>          值
     * @return LinkedHashMap实例
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int expectedSize) {
        return new LinkedHashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }

    // endregion


    // region 是否为空判断

    /**
     * 判断map是否为空
     * <pre class="code">CollectionUtils.isEmpty(hashmap);</pre>
     *
     * @param map map集合
     * @return 如果map为{@code null}或为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断map是否为不为空
     * <pre class="code">CollectionUtils.isNotEmpty(hashmap);</pre>
     *
     * @param map map集合
     * @return 如果map不为{@code null}且不为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    // endregion

}
