package tech.fastool.json.api;

import lombok.extern.slf4j.Slf4j;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.json.api.annotation.JsonProviderName;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * JSON Factory
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@Slf4j
public class JsonFactory {

    private static JsonAdapter defaultJsonAdapter;

    private static final List<JsonBuilderWrapper> ALL_REGISTRY_JSON_BUILDER = new ArrayList<>();


    static {
        findJsonBuilderImplClasses();
    }

    private static void findJsonBuilderImplClasses() {
        ServiceLoader<BaseJsonBuilder> loader = ServiceLoader.load(BaseJsonBuilder.class);
        loader.forEach(jsonBuilder -> {
            Class<? extends BaseJsonBuilder> jsonBuilderClass = jsonBuilder.getClass();
            JsonProviderName jsonProviderName = jsonBuilderClass.getAnnotation(JsonProviderName.class);
            if (jsonProviderName == null) {
                log.warn("JSON Builder " + jsonBuilderClass.getName() + " JsonProviderName annotation is missing");
                return;
            }
            String name = jsonProviderName.value();
            if (StringUtil.isEmpty(name)) {
                name = jsonBuilderClass.getName();
            }

            log.info("Registry a json builder {} for {}", jsonBuilderClass.getCanonicalName(), name);
            ALL_REGISTRY_JSON_BUILDER.add(new JsonBuilderWrapper(jsonProviderName.index(), name, jsonBuilder));
        });
        for (JsonBuilderWrapper wrapper : ALL_REGISTRY_JSON_BUILDER) {
            try {
                defaultJsonAdapter = wrapper.getJsonBuilder().build();
                break;
            } catch (Exception e) {
                log.info("Cannot build JsonAdapter " + wrapper.getProviderName());
            }
        }
    }


    public static JsonAdapter get() {
        return defaultJsonAdapter;
    }

}
