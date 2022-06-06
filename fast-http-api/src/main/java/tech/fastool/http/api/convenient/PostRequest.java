package tech.fastool.http.api.convenient;

import tech.fastool.http.api.constants.HttpMethod;

/**
 * Post Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class PostRequest extends BaseBodyRequest<PostRequest> {

    public PostRequest(String url) {
        this.url = url;
        this.method = HttpMethod.POST;
    }

}
