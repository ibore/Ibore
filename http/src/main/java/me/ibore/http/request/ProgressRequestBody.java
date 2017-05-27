package me.ibore.http.request;

import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Administrator on 2017/5/27.
 */

public class ProgressRequestBody extends RequestBody {

    protected RequestBody requestBody;
    protected Listener listener;
    private CountingSink countingSink;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ProgressRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public ProgressRequestBody(RequestBody requestBody, Listener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();  //必须调用flush，否则最后一部分数据可能不会被写入
    }

    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;   //当前写入字节数
        private long contentLength = 0;  //总字节长度，避免多次调用contentLength()方法
        private long lastRefreshUiTime;  //最后一次刷新的时间
        private long lastWriteBytes;     //最后一次写入字节数据

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (contentLength <= 0) contentLength = contentLength(); //获得contentLength的值，后续不再调用
            bytesWritten += byteCount;

            long curTime = System.currentTimeMillis();
            //每100毫秒刷新一次数据
            if (curTime - lastRefreshUiTime >= OkGo.REFRESH_TIME || bytesWritten == contentLength) {
                //计算下载速度
                long diffTime = (curTime - lastRefreshUiTime) / 1000;
                if (diffTime == 0) diffTime += 1;
                long diffBytes = bytesWritten - lastWriteBytes;
                long networkSpeed = diffBytes / diffTime;
                if (listener != null) listener.onRequestProgress(bytesWritten, contentLength, networkSpeed);

                lastRefreshUiTime = System.currentTimeMillis();
                lastWriteBytes = bytesWritten;
            }
        }
    }
    public interface Listener {
        void onRequestProgress(long bytesWritten, long contentLength, long networkSpeed);
    }
}
