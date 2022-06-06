package tech.fastool.http.provider.apachehttpclient;

import org.jetbrains.annotations.Nullable;
import tech.fastool.http.api.HttpClient;
import tech.fastool.http.api.HttpClientBuilder;
import tech.fastool.http.api.HttpOptions;

/**
 * HttpClient构造
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ApacheHttpClientBuilder extends HttpClientBuilder {

    @Override
    public HttpClient build(@Nullable HttpOptions options) {
        return new HttpComponentsHttpClient(options);
    }

}
