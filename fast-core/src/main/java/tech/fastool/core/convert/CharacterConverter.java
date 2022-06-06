package tech.fastool.core.convert;

import tech.fastool.core.lang.Singletons;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.core.utils.BooleanUtil;

/**
 * 字符转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class CharacterConverter extends AbstractConverter<Character> {

    private static final long serialVersionUID = 2022L;

    @Override
    protected Character handleInternal(Object value) {
        if (value instanceof Boolean) {
            return BooleanUtil.toCharacter((Boolean) value);
        }
        final String valueStr = execToStr(value);
        if (StringUtil.hasText(valueStr)) {
            return valueStr.charAt(0);
        }
        return null;
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Character> getTargetClass() {
        return Character.class;
    }

    /**
     * 返回{@linkplain  CharacterConverter}实例对象
     *
     * @return {@linkplain  CharacterConverter}
     */
    public static CharacterConverter getInstance() {
        return Singletons.get(CharacterConverter.class);
    }

}
