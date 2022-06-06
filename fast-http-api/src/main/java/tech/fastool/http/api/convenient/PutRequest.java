package tech.fastool.http.api.convenient;

import tech.fastool.http.api.constants.HttpMethod;

/**
 * Put Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class PutRequest extends BaseBodyRequest<PutRequest> {

    public PutRequest(String url) {
        this.url = url;
        this.method = HttpMethod.PUT;
    }

}
