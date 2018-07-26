package com.jci.vsd.network.control;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jci.vsd.utils.Loger;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/**
 * Created by caroline on 2017/9/4.
 */

class ProgressRequestBody extends RequestBody {
    //进度回调接口
    private UploadProgressListener mProgressListener;
    private File file;
    private MediaType contentType;
    private long contentLength;
    private RequestBody requestBodyNow;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;
    public ProgressRequestBody(@NonNull final File file) {
        requestBodyNow = RequestBody.create(MediaType.parse("image/*"),file);
    }

    public  ProgressRequestBody create(@Nullable final MediaType contentType, @NonNull final File file, @NonNull UploadProgressListener progressListener) {
        ProgressRequestBody requestBody = new ProgressRequestBody(file);
        requestBody.contentType = contentType;
        requestBody.file = file;
        requestBody.mProgressListener = progressListener;
        return requestBody;
    }

    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return contentType;
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        contentLength = file.length();
        return contentLength;
    }

    /**
     * 重写进行写入
     *
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (null == bufferedSink) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBodyNow.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private class ProgressSource implements Source {
        private Source mSource;

        private long byteCountAll = 0;

        ProgressSource(Source source) {
            this.mSource = source;
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            Loger.i("ProgressSource = "+byteCount+",=");
            byteCountAll += byteCount;
            //mProgressListener.onProgress(byteCountAll, contentLength);
            return mSource.read(sink, byteCount);
        }

        @Override
        public Timeout timeout() {
            return mSource.timeout();
        }

        @Override
        public void close() throws IOException {
            mSource.close();
        }
    }

    /**
     * 写入，回调进度接口
     *
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                Loger.i("ProgressSource = "+byteCount+",="+contentLength);
                //回调
                mProgressListener.onProgress(bytesWritten, contentLength);
            }
        };
    }
}
