package tech.fastool.json.provider.jackson;

import tech.fastool.json.api.BaseJsonBuilder;
import tech.fastool.json.api.JsonAdapter;
import tech.fastool.json.api.annotation.JsonProviderName;

/**
 * 基于{@code Jackson}的JSON构建器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@JsonProviderName(value = "jackson", index = 10)
public class JacksonJsonBuilder extends BaseJsonBuilder {

    /**
     * 构建对象
     *
     * @return {@linkplain JsonAdapter}
     */
    @Override
    public JsonAdapter build() {

        JacksonHandler jacksonHandler = new JacksonHandler();
        JsonAdapter json = new JsonAdapter();
        json.setHandler(jacksonHandler);

        return json;
    }

}
