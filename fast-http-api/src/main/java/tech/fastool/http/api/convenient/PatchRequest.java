package tech.fastool.http.api.convenient;

import tech.fastool.http.api.constants.HttpMethod;

/**
 * Patch Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class PatchRequest extends BaseBodyRequest<PatchRequest> {

    public PatchRequest(String url) {
        this.url = url;
        this.method = HttpMethod.PATCH;
    }

}
