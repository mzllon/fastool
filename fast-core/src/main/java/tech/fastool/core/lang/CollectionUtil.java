package tech.fastool.core.lang;

import java.util.*;

/**
 * 集合工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
public class CollectionUtil {

    // region 判断集合是否为空

    /**
     * 判断集合是否为空
     * <pre class="code">CollectionUtil.isEmpty(list);</pre>
     *
     * @param c 集合
     * @return 如果集合为{@code null}或为空是则返回{@code true}，否则返回{@code false}
     */
    public static boolean isEmpty(Collection<?> c) {
        return (c == null || c.isEmpty());
    }

    /**
     * 判断集合不为空
     *
     * @param c 集合
     * @return 不为空则返回{@code true}，否则返回{@code  false}
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

    // endregion

    // region JOIN相关方法

    /**
     * 将集合数据转为字符串，每个元素之间采用英文逗号 <span style="color: red;">,</span> 拼接。
     * <p>元素的值为{@code null}会忽略</p>
     *
     * @param src 集合数据
     * @return 字符串
     */
    public static String join(final Collection<String> src) {
        return join(src, true);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     * <p>元素的值为{@code null}会忽略</p>
     *
     * @param src       集合数据
     * @param separator 分隔符
     * @return 字符串
     */
    public static String join(final Collection<String> src, String separator) {
        return join(src, separator, true);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用,拼接。
     * {@code sortable}参数可以使集合{@code src}先进行正序排序，然后各个元素在拼接。
     *
     * @param src        集合数据
     * @param ignoreNull 值为{@code null}忽略
     * @return 字符串
     */
    public static String join(final Collection<String> src, final boolean ignoreNull) {
        return join(src, StringUtil.COMMA, ignoreNull);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     *
     * @param src        集合数据
     * @param separator  分隔符
     * @param ignoreNull 忽略null
     * @return 字符串
     */
    public static String join(final Collection<String> src, String separator, final boolean ignoreNull) {
        return join(src, separator, ignoreNull, false);
    }

    /**
     * 将集合数据转为字符串，每个元素之间采用{@code separator}拼接。
     * {@code sortable}参数可以使集合{@code src}先进行正序排序，然后各个元素在拼接。
     *
     * <p>Note:如果Key要先排序则集合元素中不能存在{@code null}，因为{@linkplain TreeSet}数据结构规定</p>
     *
     * @param src        集合数据
     * @param separator  分隔符
     * @param ignoreNull 值为{@code null}忽略
     * @param sortable   值为{@code true}则正序排序，否则默认
     * @return 字符串
     */
    public static String join(final Collection<String> src, String separator, final boolean ignoreNull, final boolean sortable) {
        if (src == null) {
            return null;
        }
        if (src.isEmpty()) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }

        Collection<String> resultColl = src;
        if (sortable) {
            resultColl = new TreeSet<>(src);
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String str : resultColl) {
            if (ignoreNull && str == null) {
                continue;
            }
            if (isFirst) {
                sb.append(str);
                isFirst = false;
            } else {
                sb.append(separator).append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 将所有指定的元素添加到集合中
     *
     * @param coll     集合
     * @param elements 待添加的元素
     * @param <E>      元的类型
     * @return 如果集合有变化则返回{@code true}
     */
    public static <E> boolean addAll(Collection<? super E> coll, E... elements) {
        if (coll == null) {
            return false;
        }
        if (ArrayUtil.isEmpty(elements)) {
            return false;
        }
        boolean result = false;
        for (E element : elements) {
            result |= coll.add(element);
        }
        return result;
    }

    /**
     * 将集合和数组合并到新的集合中
     * 如果源集合是{@code List}接口则返回{@code ArrayList}；如果源集合是{@code Set}则返回{@code HashSet}
     *
     * @param coll     源集合
     * @param elements 数组
     * @param <E>      元素类型
     * @return 新集合
     */
    public static <E> Collection<? extends E> mergeToColl(Collection<? extends E> coll, E... elements) {
        if (isEmpty(coll) && ArrayUtil.isEmpty(elements)) {
            return (coll == null) ? ListUtil.emptyList() : coll;
        }
        if (isNotEmpty(coll) && ArrayUtil.isEmpty(elements)) {
            return coll;
        }
        if (isEmpty(coll) && ArrayUtil.isNotEmpty(elements)) {
            return ListUtil.newArrayList(elements);
        }
        Collection<E> results;
        if (coll instanceof List) {
            results = new ArrayList<>(coll.size() + elements.length);
        } else if (coll instanceof Set) {
            results = new HashSet<>(coll.size() + elements.length);
        } else {
            throw new UnsupportedOperationException("Unsupported collection type :" + coll.getClass());
        }
        results.addAll(coll);
        addAll(results, elements);
        return results;
    }

    // endregion


}
