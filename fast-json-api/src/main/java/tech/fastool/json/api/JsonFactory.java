package tech.fastool.json.api;

import lombok.extern.slf4j.Slf4j;
import tech.fastool.core.lang.Strings;
import tech.fastool.json.api.annotation.JsonProviderName;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JSON Factory
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@Slf4j
public class JsonFactory {

    private static JsonHandler defaultJsonHandler;

    private static final Map<String, JsonHandler> ALL = new ConcurrentHashMap<>(4);

    static {
        findJsonHandlerImplClasses();
    }

    private static void findJsonHandlerImplClasses() {
        ServiceLoader<JsonHandler> loader = ServiceLoader.load(JsonHandler.class);
        for (JsonHandler jsonHandler : loader) {
            Class<? extends JsonHandler> clazz = jsonHandler.getClass();
            JsonProviderName jsonProviderName = clazz.getAnnotation(JsonProviderName.class);
            if (jsonProviderName == null) {
                log.warn("JSON Handler " + clazz + " JsonProviderName annotation is missing");
                continue;
            }
            String name = jsonProviderName.value();
            ALL.put(name, jsonHandler);
        }

        String jsonProviderValue = null;
        InputStream in = JsonHandler.class.getClassLoader().getResourceAsStream("fast-json-api.properties");
        if (in != null) {
            Properties properties = new Properties();
            try {
                properties.load(in);
                jsonProviderValue = properties.getProperty(JsonProviderName.class.getName());
            } catch (IOException e) {
                log.warn("Loading Properties[fast-json-api.properties] failed", e);
            }
        }
        if (Strings.isBlank(jsonProviderValue)) {
            TreeMap<Integer, String> treeMap = new TreeMap<>();
            ALL.values().forEach(item -> {
                JsonProviderName jsonProviderName = item.getClass().getAnnotation(JsonProviderName.class);
                treeMap.put(jsonProviderName.index(), jsonProviderName.value());
            });
            jsonProviderValue = treeMap.firstEntry().getValue();
        }
        if (Strings.isNotBlank(jsonProviderValue)) {
            defaultJsonHandler = ALL.get(jsonProviderValue);
        }
    }

    /**
     * ???????????????{@linkplain  JsonHandler}
     * ???????????????classpath?????????????????????fast-json-api.properties
     * ????????????????????????{@linkplain  JsonHandler}???????????????{@linkplain  JsonProviderName#index()},?????????????????????????????????
     *
     * @return {@linkplain JsonHandler}
     */
    public static JsonHandler defaultJsonHandler() {
        assert defaultJsonHandler != null;
        return defaultJsonHandler;
    }

    /**
     * ???????????????JSON???????????????,???????????????????????????{@linkplain  #defaultJsonHandler}
     *
     * @param jsonProviderValue JSON????????????
     * @return {@linkplain JsonHandler}
     */
    public static JsonHandler jsonHandler(String jsonProviderValue) {
        return ALL.getOrDefault(jsonProviderValue, defaultJsonHandler());
    }

}
