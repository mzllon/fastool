package tech.fastool.json.api;

import lombok.*;
import org.jetbrains.annotations.NotNull;

/**
 * JSON构造器的包装类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JsonBuilderWrapper implements Comparable<JsonBuilderWrapper> {

    private int index;

    private String providerName;

    private BaseJsonBuilder jsonBuilder;

    @Override
    public int compareTo(@NotNull JsonBuilderWrapper o) {
        return this.index - o.index;
    }

}
