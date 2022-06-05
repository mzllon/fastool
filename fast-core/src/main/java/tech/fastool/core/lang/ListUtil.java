package tech.fastool.core.lang;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * List集合工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-03-23
 */
@UtilityClass
public class ListUtil extends CollectionUtil {

    /**
     * 返回不可变的空集合
     *
     * @param <T> 集合元素类型
     * @return 空集合，不可变
     */
    public static <T> List<T> immutableEmptyList() {
        return Collections.emptyList();
    }

    /**
     * 返回空集合，可进行增删改查的
     *
     * @param <T> 集合元素类型
     * @return 空集合，可变的
     * @see #immutableEmptyList()
     */
    public static <T> List<T> emptyList() {
        return new ArrayList<>(0);
    }

    /**
     * 新建一个ArrayList
     *
     * @param <T>    集合元素类型
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        return (ArrayList<T>) list(false, values);
    }

    /**
     * 新建一个List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param values   数组
     * @return List对象
     */
    public static <T> List<T> list(boolean isLinked, T... values) {
        if (ArrayUtil.isEmpty(values)) {
            return list(isLinked);
        }
        final List<T> list = isLinked ? new LinkedList<>() : new ArrayList<>(values.length);
        Collections.addAll(list, values);
        return list;
    }

    /**
     * 新建一个空List
     *
     * @param <T>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @return List对象
     */
    public static <T> List<T> list(boolean isLinked) {
        return isLinked ? new LinkedList<>() : new ArrayList<>();
    }


}
