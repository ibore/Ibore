package me.ibore.http.request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import me.ibore.http.HttpParams;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static me.ibore.http.HttpParams.MEDIA_TYPE_STREAM;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-06-05 00:33
 * website: ibore.me
 */

public abstract class BodyRequest<R extends BodyRequest> extends Request<R> {

    protected MediaType mediaType;      //上传的MIME类型
    protected String content;           //上传的文本内容
    protected byte[] bytes;             //上传的字节数据
    private RequestBody requestBody;

    public BodyRequest(String url, String method) {
        super(url, method);
    }

    public R upFile(File file) {
        this.requestBody = RequestBody.create(MEDIA_TYPE_STREAM, file);
        return (R) this;
    }

    public R requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return (R) this;
    }

    public R params(String key, File file) {
        return (R) this;
    }

    public R upString(String content) {
        this.content = content;
        this.mediaType = HttpParams.MEDIA_TYPE_PLAIN;
        return (R) this;
    }

    public R upString(String content, MediaType mediaType) {
        this.content = content;
        this.mediaType = mediaType;
        return (R) this;
    }

    public R upJson(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return (R) this;
    }

    public R upJson(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return (R) this;
    }

    public R upBytes(byte[] bytes) {
        this.bytes = bytes;
        this.mediaType = MEDIA_TYPE_STREAM;
        return (R) this;
    }

    @Override
    protected RequestBody generateRequestBody() {
        if (null != requestBody) return requestBody;
        if (null != content && null != mediaType) return RequestBody.create(mediaType, content);
        if (null != bytes && null != mediaType) return RequestBody.create(mediaType, bytes);
        return generateMultipartRequestBody(getParams());
    }

    private RequestBody generateMultipartRequestBody(HttpParams params) {

        return null;
//        if (params.fileParamsMap.isEmpty()) {
//            //表单提交，没有文件
//            FormBody.Builder bodyBuilder = new FormBody.Builder();
//            for (String key : params.urlParamsMap.keySet()) {
//                List<String> urlValues = params.urlParamsMap.get(key);
//                for (String value : urlValues) {
//                    bodyBuilder.add(key, value);
//                }
//            }
//            return bodyBuilder.build();
//        } else {
            //表单提交，有文件
//            MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//            //拼接键值对
//            if (!params.urlParamsMap.isEmpty()) {
//                for (Map.Entry<String, List<String>> entry : params.urlParamsMap.entrySet()) {
//                    List<String> urlValues = entry.getValue();
//                    for (String value : urlValues) {
//                        multipartBodybuilder.addFormDataPart(entry.getKey(), value);
//                    }
//                }
//            }
//            //拼接文件
//            for (Map.Entry<String, List<HttpParams.FileWrapper>> entry : params.fileParamsMap.entrySet()) {
//                List<HttpParams.FileWrapper> fileValues = entry.getValue();
//                for (HttpParams.FileWrapper fileWrapper : fileValues) {
//                    RequestBody fileBody = RequestBody.create(fileWrapper.contentType, fileWrapper.file);
//                    multipartBodybuilder.addFormDataPart(entry.getKey(), fileWrapper.fileName, fileBody);
//                }
//            }
//            return multipartBodybuilder.build();
//        }
    }


}
