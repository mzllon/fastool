package tech.fastool.core.id;

import tech.fastool.core.lang.Singletons;
import tech.fastool.core.lang.StringUtil;

import java.util.UUID;

/**
 * 基于{@linkplain UUID}的ID生成器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class UuidGenerator implements IdGenerator<Object> {

    /**
     * 是否忽略中划线
     */
    protected final boolean ignoreDash;

    public UuidGenerator() {
        this(false);
    }

    public UuidGenerator(boolean ignoreDash) {
        this.ignoreDash = ignoreDash;
    }

    /**
     * 返回ID
     *
     * @param ignoreObj 参数
     * @return ID
     */
    @Override
    public String get(Object ignoreObj) {
        String id = UUID.randomUUID().toString();
        return ignoreDash ? StringUtil.replace(id, StringUtil.DASH, StringUtil.EMPTY_STRING) : id;
    }

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        return get(null);
    }

    /**
     * 返回一个默认的实例
     *
     * @return UUID Generator
     */
    public static UuidGenerator getInstance() {
        return Singletons.get(UuidGenerator.class);
    }

    /**
     * 返回一个不带-的默认实例
     *
     * @return UUID Generator
     */
    public static UuidGenerator getInstanceNoDash() {
        return Singletons.get(UuidGenerator.class, true);
    }
}