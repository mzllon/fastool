package tech.fastool.all;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.digest.MD5s;
import tech.fastool.core.lang.Arrays;
import tech.fastool.core.lang.Collections;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.*;
import tech.fastool.core.lang.reflect.Reflects;
import tech.fastool.core.lang.regex.Patterns;
import tech.fastool.http.api.convenient.*;
import tech.fastool.json.api.BaseTypeRef;
import tech.fastool.json.api.Jsons;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Supplier;

/**
 * 快捷工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-05
 */
@UtilityClass
public class $ {

    // region null/nonNull

    /**
     * 确认参数非空w
     *
     * @param obj 对象
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     * @see Objects#requireNonNull(Object)
     */
    public static <T> T requireNonNull(T obj) {
        return Objects.requireNonNull(obj);
    }

    /**
     * 确认参数非空
     *
     * @param obj     对象
     * @param message 异常消息,会通过{@linkplain String#valueOf(Object)}包装,即消息可{@code null}
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     * @see Objects#requireNonNull(Object, String)
     */
    public static <T> T requireNonNull(final T obj, String message) {
        return Objects.requireNonNull(obj, message);
    }

    /**
     * 如果对象为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     * @see Objects#isNull(Object)
     */
    public static boolean isNull(final Object obj) {
        return Objects.isNull(obj);
    }

    /**
     * 判断数组的任意一个元素是否为{@code null}
     *
     * @param array 数组
     * @return 如果数组任意一个元素为{@code null}则返回{@code true}，否则返回{@code false}
     * @see Objects#isAnyNull(Object...)
     */
    public static boolean isAnyNull(final Object... array) {
        return Objects.isAnyNull(array);
    }

    /**
     * 判断数组的元素都为{@code null}
     *
     * @param array 数组
     * @return 如果数组所有元素都为{@code null}则返回{@code true}，否则返回{@code false}
     * @see Objects#isAllNull(Object...)
     */
    public static boolean isAllNull(final Object... array) {
        return Objects.isAllNull(array);
    }

    /**
     * 如果对象不为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     * @see Objects#nonNull(Object)
     */
    public static boolean nonNull(final Object obj) {
        return Objects.nonNull(obj);
    }

    /**
     * 如果对象不为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     * @see #nonNull(Object)
     */
    public static boolean isNotNull(final Object obj) {
        return nonNull(obj);
    }

    // endregion


    // region empty/notEmpty

    /**
     * 要求数组非空
     *
     * @param array 待检测的数组
     * @param <T>   泛型
     * @return 如果不为空则返回原数组
     * @see Arrays#requireNotEmpty(Object[])
     */
    @SafeVarargs
    public static <T> T[] requireNotEmpty(final T... array) {
        return Arrays.requireNotEmpty(array);
    }


    /**
     * 判断是否为空
     *
     * @param array 数组
     * @param <T>   泛型类
     * @return 当数组为空或{@code null}时返回{@code true}
     * @see Arrays#isEmpty(Object[])
     */
    @SafeVarargs
    public static <T> boolean isEmpty(final T... array) {
        return Arrays.isEmpty(array);
    }

    /**
     * <p>判断对象是否为空或{@code null}</p>
     * 支持以下类型
     * <ul>
     *     <li>{@linkplain CharSequence}长度是否为0</li>
     *     <li>{@code Array}数组长度是否为0</li>
     *     <li>{@linkplain Collection}集合是否为空</li>
     *     <li>{@linkplain Map}是否为空</li>
     * </ul>
     *
     * @param obj 被判断的对象
     * @return 是否为空
     * @see Objects#isEmpty(Object)
     */
    public static boolean isEmpty(final Object obj) {
        return Objects.isEmpty(obj);
    }

    /**
     * 判断是否不为空或不为{@code null}
     *
     * @param array 数组
     * @param <T>   泛型类
     * @return 当数组不为空且不是{@code null}时返回{@code true}
     */
    @SafeVarargs
    public static <T> boolean isNotEmpty(final T... array) {
        return !isEmpty(array);
    }

    /**
     * <p>判断对象是否为不为空或{@code null}</p>
     * 支持以下类型
     * <ul>
     *     <li>{@linkplain CharSequence}长度是否为0</li>
     *     <li>{@code Array}数组长度是否为0</li>
     *     <li>{@linkplain Collection}集合是否为空</li>
     *     <li>{@linkplain Map}是否为空</li>
     * </ul>
     *
     * @param obj 被判断的对象
     * @return 是否为空
     * @see #isEmpty(Object)
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * <p>判断数组中任意一个元素为空或{@code null}</p>
     * 数组本身为空或{@code null}，数组的元素支持如下类型：
     * <ul>
     *     <li>{@linkplain CharSequence}长度是否为0</li>
     *     <li>{@code Array}数组长度是否为0</li>
     *     <li>{@linkplain Collection}集合是否为空</li>
     *     <li>{@linkplain Map}是否为空</li>
     * </ul>
     *
     * @param array 数组
     * @return 是否任意为空
     */
    public static boolean isAnyEmpty(Object... array) {
        return Objects.isAnyEmpty(array);
    }


    // endregion


    // region equals and hashCode

    /**
     * <p>
     * 比较两个对象的内容(equals),如果两个对象相等则返回{@code true},如果两个中有一个为{@code null}则返回{@code false}.
     * 如果两个对象都是{@code null}则返回{@code true}.如果传入的参数类型是数组,则比较的数组里的对象内容,而不是数组引用比较.
     * </p>
     * <pre class="code">
     * $.nullSafeEquals("hello","hello"); //--- true
     * $.nullSafeEquals("hello","hell"); //--- false;
     * $.nullSafeEquals(4,4); //--- true
     * $.nullSafeEquals(new String[]{"aaaa","bbb"},new String[]{"aaaa","bbb"}); //--- true
     * </pre>
     *
     * @param a 第一个比较对象
     * @param b 第二个比较对象
     * @return 判断两个对象内容是否相等
     * @see Objects#equals(Object, Object)
     */
    public static boolean equals(@Nullable final Object a, @Nullable final Object b) {
        return Objects.equals(a, b);
    }

    /**
     * 对象的哈希码，如果对象为{@code null}则返回0
     *
     * @param obj 对象
     * @return 哈希码
     * @see Objects#hashCode(Object)
     */
    public static int hashCode(final Object obj) {
        return Objects.hashCode(obj);
    }

    // endregion


    // region compare

    /**
     * 判断两个对象的大小,注意空{@code null}比非空小
     *
     * @param a   对象a
     * @param b   对象b
     * @param <T> 对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     */
    public static <T extends Comparable<? super T>> int compare(@Nullable final T a, @Nullable final T b) {
        return compare(a, b, false);
    }

    /**
     * 判断两个对象的大小
     *
     * @param a           对象a
     * @param b           对象b
     * @param nullGreater 当被比对为{@code null}时是否排序在前面,{@code true}则表示{@code null}比任何非{@code null}大,{@code false}反之
     * @param <T>         对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     * @see Objects#compare(Comparable, Comparable, boolean)
     */
    public static <T extends Comparable<? super T>> int compare(@Nullable final T a, @Nullable final T b,
                                                                final boolean nullGreater) {
        return Objects.compare(a, b, nullGreater);
    }

    /**
     * 判断两个对象大小
     *
     * @param a   对象a
     * @param b   对象b
     * @param c   比较器
     * @param <T> 对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     * @see Objects#compare(Object, Object, Comparator)
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        if (a == b) {
            return 0;
        }
        if (c == null) {
            throw new NullPointerException();
        }
        return Objects.compare(a, b, c);
    }

    // endregion


    // region get null safety

    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * $.getIfNull(null, null)      = null
     * $.getIfNull(null, "")        = ""
     * $.getIfNull(null, "zz")      = "zz"
     * $.getIfNull("abc", *)        = "abc"
     * $.getIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param obj          被检查对象，可能为{@code null}
     * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
     * @param <T>          对象类型
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     * @see Objects#getIfNull(Object, Object)
     */
    @Nullable
    public static <T> T getIfNull(@Nullable final T obj, @Nullable final T defaultValue) {
        return Objects.getIfNull(obj, defaultValue);
    }

    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * $.getIfNull(null, null)      报错
     * $.getIfNull(null, () -> {return null;})        = null
     * $.getIfNull(null, () -> {return "zz"})      = "zz"
     * $.getIfNull("abc", () -> {return "*";})        = "abc"
     * $.getIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param obj      被检查对象，可能为{@code null}
     * @param supplier 被检查对象为{@code null}返回的提供器的函数值，不可以为{@code null}
     * @param <T>      对象类型
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     * @see Objects#getIfNull(Object, Supplier)
     */
    @Nullable
    public static <T> T getIfNull(@Nullable final T obj, @NotNull Supplier<T> supplier) {
        return Objects.getIfNull(obj, supplier);
    }

    // endregion


    //region 判断对象是否是数组

    /**
     * 判断是否是数组类型
     * <pre>
     *     $.isArray(null);                                 = false
     *     $.isArray("123");                                = false
     *     $.isArray(new String[]{"aa","bb"});              = true
     * </pre>
     *
     * @param obj 对象
     * @return 如果是数组类型则返回{@code true},否则返回{@code false}
     * @see Arrays#isArray(Object)
     */
    public static boolean isArray(@Nullable final Object obj) {
        return Arrays.isArray(obj);
    }

    /**
     * 判断是否是数组类型
     * <pre>
     *     $.isPrimitiveArray(null)                         = false
     *     $.isPrimitiveArray(1)                            = false
     *     $.isPrimitiveArray(1L)                           = false
     *     $.isPrimitiveArray("1")                          = false
     *     $.isPrimitiveArray(new String[]{})               = false
     *     $.isPrimitiveArray(new int[]{})                  = true
     *     $.isPrimitiveArray(new byte[]{})                 = true
     * </pre>
     *
     * @param obj 对象实例
     * @return 如果是原生数组类型则返回{@code true},否则返回{@code false}
     * @see Arrays#isPrimitiveArray(Object)
     */
    public static boolean isPrimitiveArray(@Nullable final Object obj) {
        return Arrays.isPrimitiveArray(obj);
    }

    //endregion


    // region Array General

    /**
     * 获取数组的长度,如果参数为{@code null}则返回0
     *
     * @param array 数组对象
     * @return 数组的长度
     * @throws IllegalArgumentException 如果参数不是数组，则抛出该异常
     * @see Arrays#getLength(Object)
     */
    public static int getLength(@Nullable final Object array) throws IllegalArgumentException {
        return Arrays.getLength(array);
    }

    /**
     * 新建一个空数组
     *
     * @param componentType 数组类型
     * @param newSize       数组大小
     * @param <T>           数组元素类型
     * @return 空数组
     * @see Arrays#newArray(Class, int)
     */
    @NotNull
    public static <T> T[] newArray(@NotNull Class<?> componentType, int newSize) {
        return Arrays.newArray(componentType, newSize);
    }

    /**
     * 获取数组对象的元素类型
     *
     * @param array 数组
     * @return 元素类型
     * @see Arrays#getComponentType(Object)
     */
    @Nullable
    public static Class<?> getComponentType(@Nullable Object array) {
        return Arrays.getComponentType(array);
    }

    /**
     * 获取数组的元素类型
     *
     * @param arrayClass 数组类
     * @return 元素类型
     * @see Arrays#getComponentType(Class)
     */
    @Nullable
    public static Class<?> getComponentType(@Nullable Class<?> arrayClass) {
        return Arrays.getComponentType(arrayClass);
    }

    /**
     * 数组转String
     *
     * @param obj 数组对象
     * @return 数组字符串
     * @see Arrays#toString(Object)
     */
    @Nullable
    public static String toString(@Nullable Object obj) {
        return Arrays.toString(obj);
    }

    // endregion


    // region indexOf


    /**
     * 返回数组中指定元素所在位置，未找到返回{@link Arrays#INDEX_NOT_FOUND}
     *
     * @param <T>     数组类型
     * @param array   数组
     * @param element 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link Arrays#INDEX_NOT_FOUND}
     * @see Arrays#indexOf(Object[], Object)
     */
    public static <T> int indexOf(@Nullable T[] array, @Nullable T element) {
        return Arrays.indexOf(array, element);
    }

    // endregion


    // region contains

    /**
     * <p>判断数组中是否包含了指定的元素</p>
     * <pre class="code">
     * ArrayUtil.containsElement(new String[]{"aaaa","bbb","cc",null},null); //--- true
     * ArrayUtil.containsElement(new String[]{"aaaa","bbb","cc"},"cc"); //--- true
     * ArrayUtil.containsElement(new String[]{"aaaa","bbb","cc",null},"xx"); //--- false
     * </pre>
     *
     * @param array   数组
     * @param element 检查的元素对象
     * @param <T>     泛型类型声明
     * @return 如果数组中存在则返回{@code true},否则返回{@code false}
     * @see Arrays#contains(Object[], Object)
     */
    public static <T> boolean contains(final T[] array, T element) {
        return Arrays.contains(array, element);
    }

    // endregion


    // region 判断集合是否为空

    /**
     * 判断集合是否为空
     * <pre class="code">CollectionUtil.isEmpty(list);</pre>
     *
     * @param c 集合
     * @return 如果集合为{@code null}或为空是则返回{@code true}，否则返回{@code false}
     * @see Collections#isEmpty(Collection)
     */
    public static boolean isEmpty(Collection<?> c) {
        return Collections.isEmpty(c);
    }

    /**
     * 判断集合不为空
     *
     * @param c 集合
     * @return 不为空则返回{@code true}，否则返回{@code  false}
     * @see #isEmpty(Collection)
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
        return join(src, Strings.COMMA, ignoreNull);
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
     * @see Collections#join(Collection, String, boolean, boolean)
     */
    public static String join(final Collection<String> src, String separator, final boolean ignoreNull, final boolean sortable) {
        return Collections.join(src, separator, ignoreNull, sortable);
    }

    /**
     * 将所有指定的元素添加到集合中
     *
     * @param coll     集合
     * @param elements 待添加的元素
     * @param <E>      元的类型
     * @return 如果集合有变化则返回{@code true}
     * @see Collections#addAll(Collection, Object[])
     */
    @SafeVarargs
    public static <E> boolean addAll(Collection<? super E> coll, E... elements) {
        return Collections.addAll(coll, elements);
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
    @SafeVarargs
    public static <E> Collection<? extends E> merge(Collection<? extends E> coll, E... elements) {
        return Collections.merge(coll, elements);
    }

    /**
     * 新建一个ArrayList
     *
     * @param <T>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     * @see Lists#newArrayList(Object[])
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        return Lists.newArrayList(values);
    }

    // endregion


    // region 是否为空判断

    /**
     * 判断map是否为空
     * <pre class="code">CollectionUtils.isEmpty(hashmap);</pre>
     *
     * @param map map集合
     * @return 如果map为{@code null}或为空是则返回{@code true}，否则返回{@code false}
     * @see Maps#isEmpty(Map)
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return Maps.isEmpty(map);
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

    // region 判断字符串是否为空或不为空

    /**
     * 字符串是否为空白，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     *
     * @param cse 被检测的字符串
     * @return 是否为空
     * @see Strings#isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cse) {
        return Strings.isBlank(cse);
    }

    /**
     * 判断字符粗是否不为空，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     *
     * @param cse 字符粗
     * @return 是否不为空
     */
    public static boolean isNotBlank(CharSequence cse) {
        return !isBlank(cse);
    }

    /**
     * 判断字符粗是否不为空，空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""
     * <p>在Web环境下，字符串"null"或"undefined"也是为空</p>
     *
     * @param cse 字符粗
     * @return 是否不为空
     * @see Strings#isNotBlankInWebEnv(CharSequence)
     */
    public static boolean isNotBlankInWebEnv(CharSequence cse) {
        return Strings.isNotBlankInWebEnv(cse);
    }

    /**
     * 数组的任意元素是否为空白
     *
     * @param array 数组
     * @return 如果数组为空或元素中为空白则返回{@code true}，否则返回{@code  false}
     * @see Strings#isAnyBlank(CharSequence...)
     */
    public static boolean isAnyBlank(CharSequence... array) {
        return Strings.isAnyBlank(array);
    }

    /**
     * 判断数组的所有元素都为空白
     *
     * @param array 数组
     * @return 数组是否都为空
     * @see Strings#isAllBlank(CharSequence...)
     */
    public static boolean isAllBlank(CharSequence... array) {
        return Strings.isAllBlank(array);
    }

    /**
     * 判断数组的任意一个字符串不为空白字符
     *
     * @param array 数组
     * @return true / false
     * @see Strings#isAnyNotBlank(CharSequence...)
     */
    public static boolean isAnyNotBlank(CharSequence... array) {
        return Strings.isAnyNotBlank(array);
    }

    /**
     * 判断数组的所有元素都不为空白字符
     *
     * @param array 数组
     * @return true / false
     * @see Strings#isAllNotBlank(CharSequence...)
     */
    public static boolean isAllNotBlank(CharSequence... array) {
        return Strings.isAllNotBlank(array);
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>集合本身不是{@code null}</li>
     * <li>集合中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strColl 字符串列表
     * @return 当且仅当字符串列表均非空则返回{@code true},反之则返回{@code false}.
     * @see Strings#isAnyNotBlank(Collection)
     */
    public static boolean isAnyNotBlank(Collection<CharSequence> strColl) {
        return Strings.isAnyNotBlank(strColl);
    }

    /**
     * 判断字符串列表都不为空，空字符串定义
     * <ul>
     * <li>集合本身不是{@code null}</li>
     * <li>集合中的所有元素都不是<code>null</code>或则""</li>
     * </ul>
     *
     * @param strColl 字符串列表
     * @return 当且仅当字符串列表均非空则返回{@code true},反之则返回{@code false}.
     * @see Strings#isAllNotBlank(Collection)
     */
    public static boolean isAllNotBlank(Collection<CharSequence> strColl) {
        return Strings.isAllNotBlank(strColl);
    }

    // endregion


    // region String General

    /**
     * 构建字符串
     * 如果编码为{@code  null}则采用系统编码
     *
     * @param data     字节数组
     * @param encoding 编码，可以为空
     * @return 字符串
     * @see Strings#str(byte[], Charset)
     */
    public static String str(byte[] data, Charset encoding) {
        return Strings.str(data, encoding);
    }

    /**
     * {@link CharSequence} 转为字符串
     *
     * @param cse {@link CharSequence}
     * @return 字符串
     * @see Strings#str(CharSequence)
     */
    public static String str(CharSequence cse) {
        return Strings.str(cse);
    }

    /**
     * 给定内容是否匹配正则表达式
     *
     * @param regex 正则表达式
     * @param cse   字符串
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     * @see Patterns#isMatch(String, CharSequence)
     */
    public static boolean isMatch(String regex, CharSequence cse) {
        return Patterns.isMatch(regex, cse);
    }

    // endregion


    // region String Trim

    /**
     * 去除字符串左右两侧的空白符
     * <pre>
     *     $.trim(null);               = null
     *     $.trim("");              = ""
     *     $.trim("    ")           = ""
     *     $.trim("  a b  ");       = "a b"
     * </pre>
     *
     * @param cse 字符串
     * @return 返回已经去除左右两边的空白的字符串
     * @see Strings#trim(CharSequence)
     */
    public static String trim(CharSequence cse) {
        return Strings.trim(cse);
    }

    // endregion


    // region Bean Converter

    public static Map<String, Object> toMap(@Nullable Object bean) {
        if (Objects.isAnyNull(bean)) {
            return null;
        }
        Map<String, Object> target = new HashMap<>(Maps.DEFAULT_INITIAL_CAPACITY);
        Beans.copyProperties(bean, target);
        return target;
    }

    /**
     * 将map 转为 bean
     *
     * @param map       map
     * @param beanClass 对象类型
     * @param <T>       泛型标记
     * @return {T}
     */
    @Nullable
    public static <T> T toBean(@Nullable Map<String, Object> map, Class<T> beanClass) {
        if (Objects.isAnyNull(map, beanClass)) {
            return null;
        }
        T target = Reflects.newInstance(beanClass);
        Beans.mapToBean(map, target, null);
        return target;
    }

    /**
     * 拷贝集合中的对象
     *
     * @param src         源集合
     * @param targetClass 模板bean类型
     * @param <E>泛型标记
     * @return 目标集合
     */
    @Nullable
    public static <E> List<E> copyProperties(@Nullable Collection<?> src, Class<E> targetClass) {
        return copyProperties(src, targetClass, Arrays.EMPTY_STRING_ARRAY);
    }

    /**
     * 拷贝集合中的对象
     *
     * @param src         源集合
     * @param targetClass 模板bean类型
     * @param <E>泛型标记
     * @return 目标集合
     */
    @SuppressWarnings("all")
    @Nullable
    public static <E> List<E> copyProperties(@Nullable Collection<?> src, Class<E> targetClass,
                                             @Nullable String... ignoreProperties) {
        if (Objects.isAnyNull(src, targetClass)) {
            return null;
        }
        List<E> list = Lists.newArrayList();
        for (Object obj : src) {
            list.add(copyProperties(obj, targetClass, ignoreProperties));
        }
        return list;
    }

    /**
     * 拷贝对象
     *
     * @param src         源bean对象
     * @param targetClass 目标bean类型
     * @param <E>         泛型标记
     * @return 目标bean对象
     */
    @Nullable
    public static <E> E copyProperties(@Nullable Object src, Class<E> targetClass) {
        return copyProperties(src, targetClass, Arrays.EMPTY_STRING_ARRAY);
    }

    /**
     * 拷贝对象
     *
     * @param src              源bean对象
     * @param targetClass      目标bean类型
     * @param ignoreProperties 忽略的属性
     * @param <E>              泛型标记
     * @return 目标bean对象
     */
    @Nullable
    public static <E> E copyProperties(@Nullable Object src, Class<E> targetClass,
                                       @Nullable String... ignoreProperties) {
        if (Objects.isAnyNull(src, targetClass)) {
            return null;
        }
        E target = Reflects.newInstance(targetClass);
        Beans.copyProperties(src, target, ignoreProperties);
        return target;
    }

    // endregion


    // region JSON

    /**
     * Java对象转为JSON字符串
     *
     * @param obj Java对象
     * @return JSON字符串
     */
    public static String toJson(@Nullable Object obj) {
        return Jsons.toJson(obj);
    }

    /**
     * Java对象转为JSON字符串
     *
     * @param obj                 Java对象
     * @param ignorePropertyNames 忽略的属性
     * @return JSON字符串
     */
    public static String toJson(@Nullable Object obj, String... ignorePropertyNames) {
        return Jsons.toJson(obj, ignorePropertyNames);
    }

    /**
     * JSON字符串转为Java对象
     *
     * @param json  JSON字符串
     * @param clazz Java类型
     * @param <T>   泛型标记
     * @return Java对象
     */
    public static <T> T fromJson(@Nullable String json, @NotNull Class<T> clazz) {
        return Jsons.fromJson(json, clazz);
    }

    /**
     * JSON字符串转为Java对象
     *
     * @param json    JSON字符串
     * @param typeRef 泛型类型的包装
     * @param <T>     泛型标记
     * @return Java对象
     */
    public static <T> T fromJson(@Nullable String json, @NotNull BaseTypeRef<T> typeRef) {
        return Jsons.fromJson(json, typeRef);
    }

    // endregion


    // region HTTP

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return {@link GetRequest}
     */
    public static GetRequest get(@NotNull String url) {
        return HttpClients.get(url);
    }

    /**
     * FORM/POST表单提交
     *
     * @param url 提交地址
     * @return {@link PostRequest}
     */
    public static PostRequest post(String url) {
        return HttpClients.post(url);
    }

    /**
     * DELETE 请求
     *
     * @param url 请求地址
     * @return {@linkplain DeleteRequest}
     */
    public static DeleteRequest delete(@NotNull String url) {
        return HttpClients.delete(url);
    }

    /**
     * PUT 请求
     *
     * @param url 请求地址
     * @return {@linkplain PutRequest}
     */
    public static PutRequest put(@NotNull String url) {
        return HttpClients.put(url);
    }

    // endregion


    // region 金额转换

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     * @see Numbers#fen2YuanString(String)
     */
    public static String fen2YuanString(String fen) {
        return Numbers.fen2YuanString(fen);
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     * @see Numbers#fen2YuanString(long)
     */
    public static String fen2YuanString(long fen) {
        return Numbers.fen2YuanString(fen);
    }

    /**
     * 人民币金额分转元，保留2位小数
     *
     * @param fen 金额，单位分
     * @return 金额元
     * @see Numbers#fen2YuanString(long)
     */
    public static String fen2YuanString(Long fen) {
        return Numbers.fen2YuanString(fen);
    }

    /**
     * 人民币金额转为分
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额,单位为元
     * @return 金额分
     */
    public static long yuan2Fen(double yuan) {
        return yuan2Fen(Double.toString(yuan));
    }

    /**
     * 人民币金额转为分
     * <p>Note: 默认保留2位小数,超过则四舍五入</p>
     *
     * @param yuan 金额,单位为元
     * @return 金额分
     */
    public static long yuan2Fen(String yuan) {
        return yuan2Fen(yuan, true);
    }

    /**
     * 人民币金额转为分,保留小数位位数
     *
     * @param yuan  金额,单位为元
     * @param round 如果值{@code true}则超出部分四舍五入，否则直接忽略
     * @return 金额分
     */
    public static long yuan2Fen(String yuan, boolean round) {
        return Numbers.yuan2Fen(yuan, round);
    }

    // endregion


    // region Map 获取值

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map map
     * @param key 键
     * @return 值
     */
    public static String getStr(Map<?, ?> map, Object key) {
        return getStr(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map          map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static String getStr(Map<?, ?> map, Object key, String defaultValue) {
        return get(map, key, String.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map map
     * @param key 键
     * @return 值
     */
    public static Integer getInt(Map<?, ?> map, Object key) {
        return getInt(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map          map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Integer getInt(Map<?, ?> map, Object key, Integer defaultValue) {
        return get(map, key, Integer.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Double
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Double getDouble(Map<?, ?> map, Object key) {
        return getDouble(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Double
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Double getDouble(Map<?, ?> map, Object key, Double defaultValue) {
        return get(map, key, Double.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Float
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Float getFloat(Map<?, ?> map, Object key) {
        return getFloat(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Float
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Float getFloat(Map<?, ?> map, Object key, Float defaultValue) {
        return get(map, key, Float.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Short
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Short getShort(Map<?, ?> map, Object key) {
        return getShort(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Short
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Short getShort(Map<?, ?> map, Object key, Short defaultValue) {
        return get(map, key, Short.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Boolean getBool(Map<?, ?> map, Object key) {
        return getBool(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Boolean getBool(Map<?, ?> map, Object key, Boolean defaultValue) {
        return get(map, key, Boolean.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Character
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Character getChar(Map<?, ?> map, Object key) {
        return getChar(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Character
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Character getChar(Map<?, ?> map, Object key, Character defaultValue) {
        return get(map, key, Character.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Byte
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Byte getByte(Map<?, ?> map, Object key) {
        return getByte(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Byte
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Byte getByte(Map<?, ?> map, Object key, Byte defaultValue) {
        return get(map, key, Byte.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Long getLong(Map<?, ?> map, Object key) {
        return getLong(map, key, null);
    }

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Long getLong(Map<?, ?> map, Object key, Long defaultValue) {
        return get(map, key, Long.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param map  map
     * @param key  键
     * @param type 值类型
     * @param <T>  目标值类型
     * @return 值
     */
    public static <T> T get(Map<?, ?> map, Object key, Class<T> type) {
        return get(map, key, type, null);
    }

    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param map          map
     * @param key          键
     * @param type         值类型
     * @param <T>          目标值类型
     * @param defaultValue 默认值
     * @return 值
     * @see Maps#get(Map, Object, Class, Object)
     */
    public static <T> T get(Map<?, ?> map, Object key, Class<T> type, T defaultValue) {
        return Maps.get(map, key, type, defaultValue);
    }

    // endregion


    // region Id Generate

    /**
     * 返回UUID
     *
     * @return UUID
     * @see Ids#uuid()
     */
    public static String uuid() {
        return Ids.uuid();
    }

    /**
     * 返回不带-的UUID
     *
     * @return UUID No Dash
     * @see Ids#uuidNoDash()
     */
    public static String uuidNoDash() {
        return Ids.uuidNoDash();
    }

    /**
     * 返回UUID
     *
     * @return UUID
     * @see Ids#secureUuid()
     */
    public static String secureUuid() {
        return Ids.secureUuid();
    }

    /**
     * 返回不带-的UUID
     *
     * @return UUID No Dash
     * @see Ids#secureUuidNoDash()
     */
    public static String secureUuidNoDash() {
        return Ids.secureUuidNoDash();
    }

    /**
     * 返回带有日期标识的随机ID
     *
     * @return id contains date
     * @see Ids#dateId()
     */
    public static String dateId() {
        return Ids.dateId();
    }

    /**
     * 返回一个BASE64的ID
     *
     * @return id With BASE64
     * @see Ids#base64Id()
     */
    public static String base64Id() {
        return Ids.base64Id();
    }

    /**
     * 返回一个雪花算法生成的ID
     *
     * @return id with snowflake
     * @see Ids#snowflakeId()
     */
    public static long snowflakeId() {
        return Ids.snowflakeId();
    }

    /**
     * 返回一个雪花算法生成的ID
     *
     * @return id with snowflake
     */
    public static String snowflakeIdStr() {
        return String.valueOf(snowflakeId());
    }


    // endregion


    // region MD5

    /**
     * 计算字符串的md5值，结果转为16进制的小写字符串返回
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     * @see MD5s#digestHex(String)
     */
    public static String md5Hex(final String data) {
        return MD5s.digestHex(data);
    }

    /**
     * 计算的流的md5值，结果转为16进制的字符串返回
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     * @see MD5s#digest(InputStream)
     */
    public static String md5Hex(final InputStream data) {
        return MD5s.digestHex(data);
    }

    /**
     * 计算字符串的md5值，结果转为16进制的大写字符串返回
     *
     * @param data 待计算的数据
     * @return 16进制的字符串
     * @see MD5s#digestUpperHex(String)
     */
    public static String md5UpperHex(final String data) {
        return MD5s.digestUpperHex(data, null);
    }

    /**
     * 计算字符串(默认UTF8)的MD5值，将结果转为BASE64输出
     *
     * @param data 待计算的数据
     * @return BASE64的字符串
     * @see MD5s#digestBase64(String)
     */
    public static String md5Base64(final String data) {
        return MD5s.digestBase64(data, null);
    }

    // endregion

}
