package me.ibore.http;

import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by Administrator on 2017/5/27.
 */

public class HttpUtils {


    private static String userAgent;

//    public static String createUrlFromParams(String url, Params params) {
//        try {
//            StringBuilder sb = new StringBuilder();
//            sb.append(url);
//            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
//            else sb.append("?");
//            for (Map.Entry<String, List<String>> urlParams : params.getParams().entrySet()) {
//                List<String> urlValues = urlParams.getValue();
//                for (String value : urlValues) {
//                    //对参数进行 utf-8 编码,防止头信息传中文
//                    String urlValue = URLEncoder.encode(value, "UTF-8");
//                    sb.append(urlParams.getKey()).append("=").append(urlValue).append("&");
//                }
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            return sb.toString();
//        } catch (UnsupportedEncodingException e) {
//
//
//        }
//        return url;
//    }


    public static void setUserAgent(String userAgent) {
        HttpUtils.userAgent = userAgent;
    }

    /**
     * User-Agent: Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; Redmi Note 3 Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36
     */
    public static String getUserAgent() {
        if (TextUtils.isEmpty(userAgent)) {
            String webUserAgent = null;
            try {
                Class<?> sysResCls = Class.forName("com.android.internal.R$string");
                Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
                Integer resId = (Integer) webUserAgentField.get(null);
                webUserAgent = XHttp.getContext().getString(resId);
            } catch (Exception e) {
                // We have nothing to do
            }
            if (TextUtils.isEmpty(webUserAgent)) {
                webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/5.0 %sSafari/533.1";
            }

            Locale locale = Locale.getDefault();
            StringBuffer buffer = new StringBuffer();
            // Add version
            final String version = Build.VERSION.RELEASE;
            if (version.length() > 0) {
                buffer.append(version);
            } else {
                // default to "1.0"
                buffer.append("1.0");
            }
            buffer.append("; ");
            final String language = locale.getLanguage();
            if (language != null) {
                buffer.append(language.toLowerCase(locale));
                final String country = locale.getCountry();
                if (!TextUtils.isEmpty(country)) {
                    buffer.append("-");
                    buffer.append(country.toLowerCase(locale));
                }
            } else {
                // default to "en"
                buffer.append("en");
            }
            // add the model for the release build
            if ("REL".equals(Build.VERSION.CODENAME)) {
                final String model = Build.MODEL;
                if (model.length() > 0) {
                    buffer.append("; ");
                    buffer.append(model);
                }
            }
            final String id = Build.ID;
            if (id.length() > 0) {
                buffer.append(" Build/");
                buffer.append(id);
            }
            userAgent = String.format(webUserAgent, buffer, "Mobile ");
            return userAgent;
        }
        return userAgent;
    }

}
