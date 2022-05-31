package tech.fastool.core.lang;

import tech.fastool.core.exceptions.ArrayEmptyException;
import tech.fastool.core.exceptions.GenericRuntimeException;

import java.lang.reflect.Array;

/**
 * 数组工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
public class ArrayUtil {

    /**
     * Don't let anyone instantiate this class
     */
    private ArrayUtil() {
        throw new AssertionError("Cannot create instance!");
    }

    //region 数组相关的常量

    /**
     * 数组中元素未找到的下标，值为-1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 空数组(字符串类型)
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 空数组(字符类型)
     */
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];

    /**
     * 空数组(Long类型)
     */
    public static final long[] EMPTY_LONG_ARRAY = new long[0];

    /**
     * 空数组(int类型)
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    /**
     * 空数组(short类型)
     */
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];

    /**
     * 空数组(byte类型)
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * 空数组(double类型)
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    /**
     * 空数组(float类型)
     */
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];

    /**
     * 空数组(boolean类型)
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

    /**
     * 空数组(字符类型)
     */
    public static final Character[] EMPTY_CHAR_OBJECT_ARRAY = new Character[0];

    /**
     * 空数组(Long类型)
     */
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];

    /**
     * 空数组(Integer类型)
     */
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

    /**
     * 空数组(Short类型)
     */
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];

    /**
     * 空数组(Byte类型)
     */
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];

    /**
     * 空数组(Double类型)
     */
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];

    /**
     * 空数组(Float类型)
     */
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];

    /**
     * 空数组(Boolean类型)
     */
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];

    //endregion


    //region 判断对象是否是数组

    /**
     * 判断是否是数组类型
     * <pre>
     *     ArrayUtil.isArray(null);                                 = false
     *     ArrayUtil.isArray("123");                                = false
     *     ArrayUtil.isArray(new String[]{"aa","bb"});              = true
     * </pre>
     *
     * @param obj 对象
     * @return 如果是数组类型则返回{@code true},否则返回{@code false}
     */
    public static boolean isArray(final Object obj) {
        return (obj != null && obj.getClass().isArray());
    }

    /**
     * 判断是否是数组类型
     * <pre>
     *     ArrayUtil.isPrimitiveArray(null)                         = false
     *     ArrayUtil.isPrimitiveArray(1)                            = false
     *     ArrayUtil.isPrimitiveArray(1L)                           = false
     *     ArrayUtil.isPrimitiveArray("1")                          = false
     *     ArrayUtil.isPrimitiveArray(new String[]{})               = false
     *     ArrayUtil.isPrimitiveArray(new int[]{})                  = true
     *     ArrayUtil.isPrimitiveArray(new byte[]{})                 = true
     * </pre>
     *
     * @param obj 对象实例
     * @return 如果是原生数组类型则返回{@code true},否则返回{@code false}
     */
    public static boolean isPrimitiveArray(final Object obj) {
        return isArray(obj) && obj.getClass().getComponentType().isPrimitive();
    }

    //endregion


    // region assert empty/notEmpty

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final char... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final boolean... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final byte... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final short... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final int... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final long... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final float... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    public static boolean isEmpty(final double... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为空
     *
     * @param array 数组
     * @param <T>   泛型类
     * @return 当数组为空或{@code null}时返回{@code true}
     */
    @SafeVarargs
    public static <T> boolean isEmpty(final T... array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组对象是否为{@code null}或者长度为0
     *
     * @param array 数组对象
     * @return 是否为{@code null}或长度为0
     */
    public static boolean isEmpty(final Object array) {
        if (array == null) {
            return true;
        } else if (isArray(array)) {
            return getLength(array) == 0;
        }
        throw new GenericRuntimeException("Object to provide is not a array!");
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
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final char... array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final boolean... array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final byte... array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final short... array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final int... array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final float... array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final double... array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组非空且长度大于0
     *
     * @param array 数组
     * @return 判断成功则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(final long... array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组是否为空或数组列表有任意一个元素为{@code null}则返回{@code true}，否则返回{@code false}
     *
     * @param array 数组
     * @param <T>   泛型类型
     * @return {@code true} or {@code false}
     */
    @SafeVarargs
    public static <T> boolean isAnyNull(final T... array) {
        if (isEmpty(array)) {
            return true;
        }
        for (T element : array) {
            if (element == null) {
                return true;
            }
        }
        return false;
    }

    // endregion


    //region 数组转为String

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static <T> String join(T[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static <T> String join(T[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(char[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(char[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(boolean[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(boolean[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(byte[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code ,}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(byte[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(short[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(short[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(int[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(int[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(long[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(long[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(float[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(float[] array) {
        return join(array, StringUtil.COMMA);
    }

    /**
     * 将数组转为字符串，使用英文半角逗号连接
     *
     * @param array     数组
     * @param separator 数组元素分隔符
     * @return 如果数组为空则返回空字符串
     */
    public static String join(double[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return StringUtil.EMPTY_STRING;
        }
        if (separator == null) {
            separator = StringUtil.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder();
        int length = array.length - 1;
        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == length) {
                return builder.toString();
            }
            builder.append(separator);
        }
    }

    /**
     * 将数组转为字符串，使用{@code separator}将元素连接起来
     *
     * @param array 数组
     * @return 如果数组为空则返回空字符串
     */
    public static String join(double[] array) {
        return join(array, StringUtil.COMMA);
    }

    // endregion


    // region 类型相互转换

    /**
     * Character[]转为char[]
     *
     * @param array Character array
     * @return char array
     * @throws NullPointerException 如果Character[]存在{@code null}则抛出该异常
     */
    public static char[] toPrimitive(final Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            //may be null
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Character[]转为char[]
     *
     * @param array Character array
     * @return char array
     * @throws NullPointerException 如果Character[]存在{@code null}则抛出该异常
     */
    public static char[] toPrimitive(final Character[] array, final char valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            //may be null
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * 将Long[]转为long[]
     *
     * @param array Long array
     * @return long array
     * @throws NullPointerException 如果Long[]存在{@code null}则抛出该异常
     */
    public static long[] toPrimitive(final Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            //may be null
            result[i] = array[i];
        }
        return result;
    }

    /**
     * 将Long[]转为long[]
     *
     * @param array        Long array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return long array
     */
    public static long[] toPrimitive(final Long[] array, final long valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0, length = array.length; i < length; i++) {
            result[i] = (array[i] == null) ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * 将Integer[]转为int[]
     *
     * @param array Integer array
     * @return int array
     * @throws NullPointerException 如果Long[]存在{@code null}则抛出该异常
     */
    public static int[] toPrimitive(final Integer[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int length = array.length;
        final int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * 将Integer[]转为int[]
     *
     * @param array        Integer array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return int array
     */
    public static int[] toPrimitive(final Integer[] array, final int valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int length = array.length;
        final int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = (array[i] == null) ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Short[]转为short[]
     *
     * @param array Short array
     * @return short array
     * @throws NullPointerException 如果Short[]存在{@code null}则抛出该异常
     */
    public static short[] toPrimitive(final Short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int length = array.length;
        final short[] result = new short[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Short[]转为short[]
     *
     * @param array        Short array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return short array
     */
    public static short[] toPrimitive(final Short[] array, final short valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int length = array.length;
        final short[] result = new short[length];
        for (int i = 0; i < length; i++) {
            result[i] = (array[i] == null) ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Byte[]转为byte[]
     *
     * @param array Byte array
     * @return byte array
     * @throws NullPointerException 如果Byte[]存在{@code null}则抛出该异常
     */
    public static byte[] toPrimitive(final Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        int length = array.length;
        final byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Byte[]转为byte[]
     *
     * @param array        Byte array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return byte array
     */
    public static byte[] toPrimitive(final Byte[] array, final byte valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        int length = array.length;
        final byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Double[]转为double[]
     *
     * @param array Double array
     * @return double array
     * @throws NullPointerException 如果Double[]存在{@code null}则抛出该异常
     */
    public static double[] toPrimitive(final Double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        int length = array.length;
        final double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Double[]转为double[]
     *
     * @param array        Double array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return double array
     */
    public static double[] toPrimitive(final Double[] array, final double valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        int length = array.length;
        final double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Float[]转为float[]
     *
     * @param array Float array
     * @return float array
     * @throws NullPointerException 如果Float[]存在{@code null}则抛出该异常
     */
    public static float[] toPrimitive(final Float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        int length = array.length;
        final float[] result = new float[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Float[]转为float[]
     *
     * @param array        Float array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return float array
     */
    public static float[] toPrimitive(final Float[] array, final float valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        int length = array.length;
        final float[] result = new float[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * Boolean[]转为boolean[]
     *
     * @param array Boolean array
     * @return boolean array
     * @throws NullPointerException 如果Boolean[]存在{@code null}则抛出该异常
     */
    public static boolean[] toPrimitive(final Boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        int length = array.length;
        final boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Boolean[]转为boolean[]
     *
     * @param array        Boolean array
     * @param valueForNull 如果数组存在{@code null}则用该值替换
     * @return boolean array
     */
    public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        int length = array.length;
        final boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i] == null ? valueForNull : array[i];
        }
        return result;
    }

    /**
     * char[] 转为 Character[]
     *
     * @param array the char array
     * @return the Character array
     */
    public static Character[] toObject(final char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_OBJECT_ARRAY;
        }
        int length = array.length;
        final Character[] result = new Character[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * long[] 转为 Long[]
     *
     * @param array the long array
     * @return the Long array
     */
    public static Long[] toObject(final long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        int length = array.length;
        final Long[] result = new Long[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * int[] 转为 Integer[]
     *
     * @param array the int array
     * @return the Integer array
     */
    public static Integer[] toObject(final int[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        int length = array.length;
        final Integer[] result = new Integer[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * short[] 转为 Short[]
     *
     * @param array the short array
     * @return the Short array
     */
    public static Short[] toObject(final short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        int length = array.length;
        final Short[] result = new Short[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * byte[] 转为 Byte[]
     *
     * @param array the byte array
     * @return the Byte array
     */
    public static Byte[] toObject(final byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        int length = array.length;
        final Byte[] result = new Byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * double[] 转为 Double[]
     *
     * @param array the double array
     * @return the Double array
     */
    public static Double[] toObject(final double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        int length = array.length;
        final Double[] result = new Double[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * float[] 转为 Float[]
     *
     * @param array the float array
     * @return the Float array
     */
    public static Float[] toObject(final float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        int length = array.length;
        final Float[] result = new Float[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * boolean[] 转为 Boolean[]
     *
     * @param array the boolean array
     * @return the Boolean array
     */
    public static Boolean[] toObject(final boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        int length = array.length;
        final Boolean[] result = new Boolean[length];
        for (int i = 0; i < length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    //endregion


    // region 检测非空

    /**
     * 要求数组非空
     *
     * @param array 待检测的数组
     * @param <T>   泛型
     * @return 如果不为空则返回原数组
     */
    public static <T> T[] requireNotEmpty(final T[] array) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException();
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array   待检测的数组
     * @param message 为空是抛出的异常消息
     * @param <T>     泛型
     * @return 如果不为空则返回原数组
     */
    public static <T> T[] requireNotEmpty(final T[] array, final String message) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException(message);
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array 数组
     * @return 如果不为空则返回原数组
     */
    public static long[] requireNotEmpty(final long[] array) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException();
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array   数组
     * @param message 为空是抛出的异常消息
     * @return 如果不为空则返回原数组
     */
    public static long[] requireNotEmpty(final long[] array, final String message) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException(message);
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array 数组
     * @return 如果不为空则返回原数组
     */
    public static int[] requireNotEmpty(final int[] array) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException();
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array   数组
     * @param message 为空是抛出的异常消息
     * @return 如果不为空则返回原数组
     */
    public static int[] requireNotEmpty(final int[] array, final String message) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException(message);
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array 数组
     * @return 如果不为空则返回原数组
     */
    public static short[] requireNotEmpty(final short[] array) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException();
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array   数组
     * @param message 为空是抛出的异常消息
     * @return 如果不为空则返回原数组
     */
    public static short[] requireNotEmpty(final short[] array, final String message) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException(message);
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array 数组
     * @return 如果不为空则返回原数组
     */
    public static char[] requireNotEmpty(final char[] array) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException();
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array   数组
     * @param message 为空是抛出的异常消息
     * @return 如果不为空则返回原数组
     */
    public static char[] requireNotEmpty(final char[] array, final String message) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException(message);
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array 数组
     * @return 如果不为空则返回原数组
     */
    public static byte[] requireNotEmpty(final byte[] array) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException();
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array   数组
     * @param message 为空是抛出的异常消息
     * @return 如果不为空则返回原数组
     */
    public static byte[] requireNotEmpty(final byte[] array, final String message) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException(message);
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array 数组
     * @return 如果不为空则返回原数组
     */
    public static double[] requireNotEmpty(final double[] array) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException();
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array   数组
     * @param message 为空是抛出的异常消息
     * @return 如果不为空则返回原数组
     */
    public static double[] requireNotEmpty(final double[] array, final String message) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException(message);
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array 数组
     * @return 如果不为空则返回原数组
     */
    public static float[] requireNotEmpty(final float[] array) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException();
        }
        return array;
    }

    /**
     * 要求数组非空
     *
     * @param array   数组
     * @param message 为空是抛出的异常消息
     * @return 如果不为空则返回原数组
     */
    public static float[] requireNotEmpty(final float[] array, final String message) {
        if (isEmpty(array)) {
            throw new ArrayEmptyException(message);
        }
        return array;
    }

    // endregion


    // region 常见其它方法

    /**
     * 获取数组的长度,如果参数为{@code null}则返回0
     *
     * @param array 数组对象
     * @return 数组的长度
     * @throws IllegalArgumentException 如果参数不是数组，则抛出该异常
     */
    public static int getLength(final Object array) throws IllegalArgumentException {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    // endregion



}
