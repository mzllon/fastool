package tech.fastool.json.api;

import lombok.extern.slf4j.Slf4j;
import tech.fastool.json.api.annotation.JsonProviderName;

import java.util.HashMap;
import java.util.Map;
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

    private static Class<? extends BaseJsonBuilder> defaultJsonBuilderClass;

    private static final Map<String, Class<? extends BaseJsonBuilder>> ALL_REGISTRY_JSON_BUILDER = new HashMap<>();


    static {
        findJsonBuilderImplClasses();
    }

    private static void findJsonBuilderImplClasses() {
        ServiceLoader<BaseJsonBuilder> loader = ServiceLoader.load(BaseJsonBuilder.class);
        loader.forEach(jsonBuilder -> {
            Class<? extends BaseJsonBuilder> jsonBuilderClass = jsonBuilder.getClass();
            String name = parseName(jsonBuilderClass);
            log.info("Registry a json builder {} for {}", jsonBuilderClass.getCanonicalName(), name);
            ALL_REGISTRY_JSON_BUILDER.put(name, jsonBuilderClass);
            if (defaultJsonBuilderClass == null) {
                // 多个注册的区匹配的第一个
                defaultJsonBuilderClass = jsonBuilderClass;
            }
        });
    }

    private static String parseName(Class<? extends BaseJsonBuilder> clazz) {
        JsonProviderName jsonProviderNameAnnotation = clazz.getAnnotation(JsonProviderName.class);
        if (jsonProviderNameAnnotation != null) {
            String name = jsonProviderNameAnnotation.value();
            name = name.trim();
            if (!name.isEmpty()) {
                return name;
            }
        }
        return clazz.getName();
    }

    public static BaseJsonBuilder create() {
        return create(defaultJsonBuilderClass);
    }

    public static BaseJsonBuilder create(String jsonProvider) {
        if (jsonProvider == null) {
            return create();
        }
        Class<? extends BaseJsonBuilder> clazz = ALL_REGISTRY_JSON_BUILDER.get(jsonProvider);
        return create(clazz);
    }

    private static BaseJsonBuilder create(Class<? extends BaseJsonBuilder> jsonBuilderClass) {
        if (jsonBuilderClass != null) {
            try {
                return jsonBuilderClass.newInstance();
            } catch (Exception e) {
                log.error("Can't create a default json builder, " + defaultJsonBuilderClass.getCanonicalName(), e);
            }
        }
//        throw new RuntimeException("Can't find any supported JSON libraries : [gson, jackson, fastjson], check you classpath has one of these jar pairs: [fastjson, easyjson-fastjson], [gson, easyjson-gson], [jackson, easyjson-jackson]");
        return new NonJsonBuilder();
    }

}
