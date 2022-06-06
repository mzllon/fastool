package tech.fastool.http.api.convenient;

import tech.fastool.http.api.HttpRequestBody;
import tech.fastool.http.api.constants.HttpMethod;

/**
 * Head Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HeadRequest extends AbstractBaseRequest<HeadRequest> {

    public HeadRequest(String url) {
        this.url = url;
        this.method = HttpMethod.HEAD;
    }

    /**
     * 构建{@linkplain HttpRequestBody}，根据不同的请求类型
     *
     * @return {@linkplain HttpRequestBody}
     */
    @Override
    protected HttpRequestBody generateRequestBody() {
        return null;
    }

}
