package tech.fastool.core.id;

import tech.fastool.core.lang.Singletons;
import tech.fastool.core.lang.StringUtil;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 较为安全的UUID，采用{@linkplain SecureRandom}获取更加安全的随机码，当然其性能相对慢一些。
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class SecureUuidGenerator extends UuidGenerator {

    public SecureUuidGenerator() {
        super();
    }

    public SecureUuidGenerator(boolean ignoreDash) {
        super(ignoreDash);
    }

    @Override
    public String get(Object ignoreObj) {
        final byte[] randomBytes = new byte[16];
        Singletons.get(SecureRandom.class).nextBytes(randomBytes);
        String id = UUID.nameUUIDFromBytes(randomBytes).toString();
        return ignoreDash ? StringUtil.replace(id, StringUtil.DASH, StringUtil.EMPTY_STRING) : id;
    }

    /**
     * 返回一个默认的实例
     *
     * @return UUID Generator
     */
    public static SecureUuidGenerator getInstance() {
        return Singletons.get(SecureUuidGenerator.class);
    }

    /**
     * 返回一个不带-的默认实例
     *
     * @return UUID Generator
     */
    public static SecureUuidGenerator getInstanceNoDash() {
        return Singletons.get(SecureUuidGenerator.class, true);
    }

}
