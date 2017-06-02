package me.ibore.http.request;

import org.json.JSONArray;
import org.json.JSONObject;

import me.ibore.http.params.HttpParams;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/27.
 */

public abstract class BaseBodyRequest extends BaseRequest {

    protected MediaType mediaType;      //上传的MIME类型
    protected String content;           //上传的文本内容
    protected byte[] bytes;                //上传的字节数据

    protected RequestBody requestBody;

    public BaseBodyRequest(String url) {
        super(url);
    }


    public BaseBodyRequest string(String content) {
        return string(content, HttpParams.MEDIA_TYPE_PLAIN);
    }

    public BaseBodyRequest string(String content, MediaType mediaType) {
        this.content = content;
        this.mediaType = mediaType;
        return this;
    }

    public BaseBodyRequest json(String json) {
        return string(json, HttpParams.MEDIA_TYPE_JSON);
    }

    public BaseBodyRequest json(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    public BaseBodyRequest json(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    public BaseBodyRequest bytes(byte[] bytes) {
        this.bytes = bytes;
        this.mediaType = HttpParams.MEDIA_TYPE_STREAM;
        return this;
    }

    public RequestBody generateRequestBody() {
        if (requestBody != null) return requestBody;                                                //自定义的请求体
        if (content != null && mediaType != null) return RequestBody.create(mediaType, content);    //post上传字符串数据
        if (bytes != null && mediaType != null) return RequestBody.create(mediaType, bytes);              //post上传字节数组
//        return HttpUtils.generateMultipartRequestBody(params, isMultipart);
        return null;
    }

}
