package tech.fastool.json.provider.gson;

import tech.fastool.json.api.BaseJsonBuilder;
import tech.fastool.json.api.JsonAdapter;
import tech.fastool.json.api.annotation.JsonProviderName;

/**
 * 基于{@code Gson}的JSON构建器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@JsonProviderName(value = "gson", index = 20)
public class GsonJsonBuilder extends BaseJsonBuilder {

    public GsonJsonBuilder() {
    }

    /**
     * 构建对象
     *
     * @return {@linkplain JsonAdapter}
     */
    @Override
    public JsonAdapter build() {
        GsonJsonHandler gsonHandler = new GsonJsonHandler();
        JsonAdapter json = new JsonAdapter();
        json.setHandler(gsonHandler);
        return json;
    }

}
