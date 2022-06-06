package tech.fastool.json.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON的实现类的名，用于区分不同框架的实现别名
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface JsonProviderName {

    String value();

}
