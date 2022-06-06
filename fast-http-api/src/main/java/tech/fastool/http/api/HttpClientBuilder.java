package tech.fastool.http.api;

import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.CloneSupport;

/**
 * HttpClient构造器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public abstract class HttpClientBuilder extends CloneSupport<HttpClientBuilder> {

    public abstract HttpClient build(@Nullable HttpOptions httpOptions);

    static class Default extends HttpClientBuilder {

        @Override
        public HttpClient build(HttpOptions httpOptions) {
            return new HttpClient.DefaultHttpClient();
        }

    }

}
