package tech.fastool.http.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Client工厂
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class Factory {

    private static final Logger log = LoggerFactory.getLogger(Factory.class);

    private static HttpClientBuilder defaultClientBuilder;

    private static final Map<String, HttpClientBuilder> allRegistryClientBuilder = new HashMap<>();

    static {
        findClientBuilderImplClasses();
    }

    private static void findClientBuilderImplClasses() {
        ServiceLoader<HttpClientBuilder> loader = ServiceLoader.load(HttpClientBuilder.class);
        loader.forEach(clientBuilder -> {
            Class<? extends HttpClientBuilder> clazz = clientBuilder.getClass();
            String name = parseName(clazz);
            log.info("Registry a client builder {} for {}", name, clazz.getCanonicalName());
            allRegistryClientBuilder.put(name, clientBuilder);
            if (defaultClientBuilder == null) {
                defaultClientBuilder = clientBuilder;
            }
        });
        if (defaultClientBuilder == null) {
            defaultClientBuilder = new HttpClientBuilder.Default();
        }
    }

    private static String parseName(Class<? extends HttpClientBuilder> clazz) {
        HttpClientProvider name = clazz.getAnnotation(HttpClientProvider.class);
        if (name != null) {
            String nameStr = name.value();
            nameStr = nameStr.trim();
            if (!nameStr.isEmpty()) {
                return nameStr;
            }
        }
        return clazz.getName();
    }

    public static HttpClientBuilder get() {
        return defaultClientBuilder;
    }

    public static HttpClientBuilder get(String name) {
        return allRegistryClientBuilder.get(name);
    }

}
