package tech.fastool.core.lang;

import tech.fastool.core.exceptions.ClassNotFoundRuntimeException;
import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.io.FastByteArrayOutputStream;

import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 对象工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-07-04
 */
public class Objects {

    // region null/nonNull

    /**
     * 如果对象为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     */
    public static boolean isNull(final Object obj) {
        return obj == null;
    }

    /**
     * 判断数组的任意一个元素是否为{@code null}
     *
     * @param array 数组
     * @return 如果数组任意一个元素为{@code null}则返回{@code true}，否则返回{@code false}
     */
    public static boolean isAnyNull(final Object... array) {
        if (array == null || array.length == 0) {
            return true;
        }
        for (Object obj : array) {
            if (isNull(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组的元素都为{@code null}
     *
     * @param array 数组
     * @return 如果数组所有元素都为{@code null}则返回{@code true}，否则返回{@code false}
     */
    public static boolean isAllNull(final Object... array) {
        if (array == null || array.length == 0) {
            return true;
        }
        for (Object obj : array) {
            if (!isNull(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 如果对象不为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     */
    public static boolean nonNull(final Object obj) {
        return obj != null;
    }

    /**
     * 如果对象不为空则返回{@code true},否则返回{@code false}
     *
     * @param obj 被检查的对象
     * @return {@code true} or {@code false}
     */
    public static boolean isNotNull(final Object obj) {
        return nonNull(obj);
    }

    // endregion


    // region empty/notEmpty

    /**
     * <p>判断对象是否为空或{@code null}</p>
     * 支持以下类型
     * <ul>
     *     <li>{@linkplain CharSequence}长度是否为0</li>
     *     <li>{@code Array}数组长度是否为0</li>
     *     <li>{@linkplain Collection}集合是否为空</li>
     *     <li>{@linkplain Map} 是否为空</li>
     * </ul>
     *
     * @param obj 被判断的对象
     * @return 是否为空
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return StringUtil.isEmpty((CharSequence) obj);
        } else if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.isEmpty(obj);
        } else if (obj instanceof Collection<?>) {
            return CollectionUtil.isEmpty((Collection<?>) obj);
        } else if (obj instanceof Map<?, ?>) {
            return MapUtil.isEmpty((Map<?, ?>) obj);
        } else if (obj instanceof Buffer) {
            return ((Buffer) obj).hasRemaining();
        }
        return false;
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
     * @param objArray 数组
     * @return 是否任意为空
     */
    public static boolean isAnyEmpty(Object... objArray) {
        if (ArrayUtil.isEmpty(objArray)) {
            return true;
        }
        for (Object obj : objArray) {
            if (isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    // endregion


    // region equals and hashCode

    /**
     * <p>
     * 比较两个对象的内容(equals),如果两个对象相等则返回{@code true},如果两个中有一个为{@code null}则返回{@code false}.
     * 如果两个对象都是{@code null}则返回{@code true}.如果传入的参数类型是数组,则比较的数组里的对象内容,而不是数组引用比较.
     * </p>
     * <pre class="code">
     * Objects.equals("hello","hello"); //--- true
     * Objects.equals("hello","hell"); //--- false;
     * Objects.equals(4,4); //--- true
     * Objects.equals(new String[]{"aaaa","bbb"},new String[]{"aaaa","bbb"}); //--- true
     * </pre>
     *
     * @param a 第一个比较对象
     * @param b 第二个比较对象
     * @return 判断两个对象内容是否相等
     * @see Arrays#equals(Object[], Object[])
     */
    public static boolean equals(final Object a, final Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.equals(b)) {
            return true;
        }
        if (a.getClass().isArray() && b.getClass().isArray()) {
            if (a instanceof Object[] && b instanceof Object[]) {
                return Arrays.equals((Object[]) a, (Object[]) b);
            }
            if (a instanceof boolean[] && b instanceof boolean[]) {
                return Arrays.equals((boolean[]) a, (boolean[]) b);
            }
            if (a instanceof byte[] && b instanceof byte[]) {
                return Arrays.equals((byte[]) a, (byte[]) b);
            }
            if (a instanceof char[] && b instanceof char[]) {
                return Arrays.equals((char[]) a, (char[]) b);
            }
            if (a instanceof double[] && b instanceof double[]) {
                return Arrays.equals((double[]) a, (double[]) b);
            }
            if (a instanceof float[] && b instanceof float[]) {
                return Arrays.equals((float[]) a, (float[]) b);
            }
            if (a instanceof int[] && b instanceof int[]) {
                return Arrays.equals((int[]) a, (int[]) b);
            }
            if (a instanceof long[] && b instanceof long[]) {
                return Arrays.equals((long[]) a, (long[]) b);
            }
            if (a instanceof short[] && b instanceof short[]) {
                return Arrays.equals((short[]) a, (short[]) b);
            }
        }
        return false;
    }

    /**
     * 对象的哈希码，如果对象为{@code null}则返回0
     *
     * @param obj 对象
     * @return 哈希码
     */
    public static int hashCode(final Object obj) {
        return obj != null ? obj.hashCode() : 0;
    }

    /**
     * 多个元素的哈希值
     *
     * @param values 数组元素
     * @return 哈希值
     */
    public static int hashCode(Object... values) {
        return ArrayUtil.hashCode(values);
    }

    /**
     * 多个元素的哈希值
     *
     * @param values 数组元素
     * @return 哈希值
     */
    public static int hash(Object... values) {
        return hashCode(values);
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
    public static <T extends Comparable<? super T>> int compare(T a, T b) {
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
     */
    public static <T extends Comparable<? super T>> int compare(final T a, final T b, final boolean nullGreater) {
        if (a == b) {
            return 0;
        } else if (a == null) {
            return nullGreater ? 1 : -1;
        } else if (b == null) {
            return nullGreater ? -1 : 1;
        }
        return a.compareTo(b);
    }

    /**
     * 判断两个对象大小
     *
     * @param a   对象a
     * @param b   对象b
     * @param c   比较器
     * @param <T> 对象的类型
     * @return 如果 a &gt; b 则返回1,如果 a = b 则返回0,如果 a &lt; b 则返回-1
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        if (a == b) {
            return 0;
        }
        if (c == null) {
            throw new NullPointerException();
        }
        return c.compare(a, b);
    }

    // endregion


    // region get null safety

    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * Objects.getIfNull(null, null)      = null
     * Objects.getIfNull(null, "")        = ""
     * Objects.getIfNull(null, "zz")      = "zz"
     * Objects.getIfNull("abc", *)        = "abc"
     * Objects.getIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param obj          被检查对象，可能为{@code null}
     * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
     * @param <T>          对象类型
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     */
    public static <T> T getIfNull(final T obj, final T defaultValue) {
        return isNull(obj) ? defaultValue : obj;
    }

    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * Objects.getIfNull(null, null)      报错
     * Objects.getIfNull(null, () -> {return null;})        = null
     * Objects.getIfNull(null, () -> {return "zz"})      = "zz"
     * Objects.getIfNull("abc", () -> {return "*";})        = "abc"
     * Objects.getIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param obj      被检查对象，可能为{@code null}
     * @param supplier 被检查对象为{@code null}返回的提供器的函数值，不可以为{@code null}
     * @param <T>      对象类型
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     */
    public static <T> T getIfNull(final T obj, Supplier<T> supplier) {
        return isNull(obj) ? requireNonNull(supplier, "supplier is null").get() : obj;
    }

    /**
     * 如果给定对象为空则返回默认值，否则返回当前对象
     *
     * @param obj          被检测的对象
     * @param defaultValue 默认值函数
     * @param <T>          对象的类型
     * @return 对象值
     */
    public static <T> T getIfEmpty(final T obj, final T defaultValue) {
        return isEmpty(obj) ? defaultValue : obj;
    }

    /**
     * 如果给定对象为空则返回默认值，否则返回当前对象
     *
     * @param obj      被检测的对象
     * @param supplier 默认值
     * @param <T>      对象的类型
     * @return 对象值
     */
    public static <T> T getIfEmpty(final T obj, final Supplier<T> supplier) {
        return isEmpty(obj) ? requireNonNull(supplier, "supplier is null").get() : obj;
    }

    // endregion


    // region Assert

    /**
     * 判断表达式是否为{@code true}
     *
     * @param expression 表达式
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, null);
    }

    /**
     * 判断表达式是否为{@code true}
     *
     * @param expression 表达式
     * @param message    异常提示消息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            if (message == null) {
                throw new IllegalArgumentException();
            } else {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * 确认参数非空w
     *
     * @param reference 对象
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T requireNonNull(final T reference) {
        return requireNonNull(reference, null);
    }

    /**
     * 确认参数非空
     *
     * @param reference 对象
     * @param message   异常消息,会通过{@linkplain String#valueOf(Object)}包装,即消息可{@code null}
     * @return 校验后的非空对象
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T requireNonNull(final T reference, String message) {
        if (reference == null) {
            if (message == null) {
                throw new NullPointerException();
            }
            throw new NullPointerException(message);
        }
        return reference;
    }

    /**
     * 确认字符串非空
     *
     * @param reference 被检测的对象
     * @return 非空后返回原始字符串
     */
    public static <T> T requireNotEmpty(final T reference) {
        return requireNotEmpty(reference, null);
    }

    /**
     * 确认字符串非空
     *
     * @param reference 被检测的字符串
     * @param message   如果为空抛出异常信息
     * @return 非空后返回原始字符串
     */
    public static <T> T requireNotEmpty(final T reference, final String message) {
        if (Objects.isEmpty(reference)) {
            if (message == null) {
                throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException(message);
        }
        return reference;
    }

    // endregion


    // region serialization

    /**
     * 序列化对象为字节码
     *
     * @param obj 待序列化的对象
     * @throws IoRuntimeException 当序列化失败抛出该异常
     */
    public static byte[] serialize(Serializable obj) throws IoRuntimeException {
        if (obj == null) {
            return null;
        }
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(obj);
            oos.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

    /**
     * 将字节码反序列化为对象
     *
     * @param data 字节码
     * @param <T>  对象类型
     * @return 反序列化的对象
     * @throws IoRuntimeException            反序列化失败时抛出该异常
     * @throws ClassNotFoundRuntimeException 对象类型找不到时抛出该异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] data) throws IoRuntimeException, ClassNotFoundRuntimeException {
        if (ArrayUtil.isEmpty(data)) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (T) ois.readObject();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }
    }

    // endregion

}
