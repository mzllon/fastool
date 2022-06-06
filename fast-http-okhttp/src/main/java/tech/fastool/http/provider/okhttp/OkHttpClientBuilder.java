package tech.fastool.http.provider.okhttp;

import tech.fastool.http.api.HttpClient;
import tech.fastool.http.api.HttpClientBuilder;
import tech.fastool.http.api.HttpOptions;

/**
 * HttpClient构建
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class OkHttpClientBuilder extends HttpClientBuilder {

    @Override
    public HttpClient build(HttpOptions options) {
        return new OkHttpClient(options);
    }

}
