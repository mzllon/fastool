package tech.fastool.json.provider.fastjson;

import tech.fastool.json.api.BaseJsonBuilder;
import tech.fastool.json.api.JsonAdapter;
import tech.fastool.json.api.annotation.JsonProviderName;

/**
 * 基于{@code Fastjson}的JSON构建
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@JsonProviderName(value = "fastjson",index = 30)
public class FastJsonJsonBuilder extends BaseJsonBuilder {

    public FastJsonJsonBuilder() {
        super();
    }

    /**
     * 构建对象
     *
     * @return {@linkplain JsonAdapter}
     */
    @Override
    public JsonAdapter build() {
        JsonAdapter json = new JsonAdapter();
        json.setHandler(new FastJsonHandler());
        return json;
    }

}
