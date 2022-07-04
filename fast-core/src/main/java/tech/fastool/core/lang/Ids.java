package tech.fastool.core.lang;

import lombok.experimental.UtilityClass;
import tech.fastool.core.id.Base64IdGenerator;
import tech.fastool.core.id.DateIdGenerator;
import tech.fastool.core.id.SecureUuidGenerator;
import tech.fastool.core.id.UuidGenerator;
import tech.fastool.core.id.snowflake.FastSnowflake;

/**
 * ID生成器工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@UtilityClass
public final class Ids {

    /**
     * 返回UUID
     *
     * @return UUID
     */
    public static String uuid() {
        return UuidGenerator.getInstance().get();
    }

    /**
     * 返回不带-的UUID
     *
     * @return UUID No Dash
     */
    public static String uuidNoDash() {
        return UuidGenerator.getInstanceNoDash().get();
    }

    /**
     * 返回UUID
     *
     * @return UUID
     */
    public static String secureUuid() {
        return SecureUuidGenerator.getInstance().get();
    }

    /**
     * 返回不带-的UUID
     *
     * @return UUID No Dash
     */
    public static String secureUuidNoDash() {
        return SecureUuidGenerator.getInstanceNoDash().get();
    }

    /**
     * 返回带有日期标识的随机ID
     *
     * @return id contains date
     */
    public static String dateId() {
        return DateIdGenerator.getInstance().get();
    }

    /**
     * 返回一个BASE64的ID
     *
     * @return id With BASE64
     */
    public static String base64Id() {
        return Base64IdGenerator.getInstance().get();
    }

    /**
     * 返回一个雪花算法生成的ID
     *
     * @return id with snowflake
     */
    public static long snowflakeId() {
        return Singletons.get(FastSnowflake.class).nextId();
    }

    /**
     * 返回一个雪花算法生成的ID
     *
     * @return id with snowflake
     */
    public static String snowflakeIdStr() {
        return String.valueOf(snowflakeId());
    }

}
