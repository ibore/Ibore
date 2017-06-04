package me.ibore.http.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/2.
 */

public class GetRequest extends Request<GetRequest> {

    public GetRequest(String url) {
        super(url, "GET");
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    @Override
    protected okhttp3.Request generateRequest(RequestBody requestBody) {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        builder.get().url(generateUrl(getUrl(), getParams().getParamsMap())).tag(getTag()).headers(generateHeaders());
        return builder.build();
    }

    private String generateUrl(String url, LinkedHashMap<String, List<String>> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
            else sb.append("?");
            for (Map.Entry<String, List<String>> urlParams : params.entrySet()) {
                List<String> urlValues = urlParams.getValue();
                for (String value : urlValues) {
                    //对参数进行 utf-8 编码,防止头信息传中文
                    String urlValue = URLEncoder.encode(value, "UTF-8");
                    sb.append(urlParams.getKey()).append("=").append(urlValue).append("&");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}
