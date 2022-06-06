package tech.fastool.http.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HttpClient Provider
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface HttpClientProvider {

    String value();

}
