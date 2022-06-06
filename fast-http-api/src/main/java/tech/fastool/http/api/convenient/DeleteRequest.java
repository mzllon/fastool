package tech.fastool.http.api.convenient;

import tech.fastool.http.api.HttpRequestBody;
import tech.fastool.http.api.constants.HttpMethod;

/**
 * Deletre Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class DeleteRequest extends AbstractBaseRequest<DeleteRequest> {

    public DeleteRequest(String url) {
        this.url = url;
        this.method = HttpMethod.DELETE;
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
