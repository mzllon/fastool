package tech.fastool.core.id;

import tech.fastool.core.lang.Singletons;
import tech.fastool.core.lang.Strings;

import java.util.UUID;

/**
 * 基于{@linkplain UUID}的ID生成器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class UuidGenerator implements IdGenerator<String> {

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
     * @return ID
     */
    @Override
    public String get() {
        String id = UUID.randomUUID().toString();
        return ignoreDash ? Strings.replace(id, Strings.DASH, Strings.EMPTY_STRING) : id;
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
