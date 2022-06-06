package tech.fastool.json.api;

/**
 * 无JSON适配器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class NonJsonBuilder extends BaseJsonBuilder {
    /**
     * 构建对象
     *
     * @return {@linkplain BaseJsonBuilder}
     */
    @Override
    public JsonAdapter build() {
        return null;
    }

}
