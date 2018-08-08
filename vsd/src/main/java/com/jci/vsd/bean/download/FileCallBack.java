package com.jci.vsd.bean.download;

import android.util.Log;

import com.jci.vsd.exception.IApiException;
import com.jci.vsd.utils.Loger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import okhttp3.ResponseBody;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by miya95 on 2016/12/5.
 */
public abstract class FileCallBack<T> {

    private String destFileDir;
    private String destFileName;
    private boolean isDownloadError;

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        subscribeLoadProgress();
    }

    public abstract void onSuccess(T t);

    public abstract void progress(long progress, long total);

    public abstract void onStart();

    public abstract void onCompleted();

    public abstract void onError(Throwable e);

    public void saveFile(ResponseBody body) throws Exception {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if (file.exists())
                file.delete();
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }

                fos.flush();
            unsubscribe();
            //onCompleted();
        } catch (FileNotFoundException e) {
            Loger.i("FileNotFoundException = ");
            e.printStackTrace();
            isDownloadError = true;
            throw new IApiException("dkkd", "没有发现下载文件");
        } catch (SocketException e) {
            Loger.i("FileNotFoundException = ");
            e.printStackTrace();
            isDownloadError = true;
            throw new IApiException("dkkd", "网络超时");
        } catch (IOException e) {
            e.printStackTrace();
            Loger.i("IOException = ");
            isDownloadError = true;
            throw new IApiException("dkkd", "网络读取异常");
        } catch (Exception e) {
            Loger.i("Exception = ");
            e.printStackTrace();
            isDownloadError = true;
            throw new IApiException("dkkd", "下载失败");

        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Loger.e("saveFile"+e.getMessage());
            }
        }
    }

    public void saveFileStream(ResponseBody body) throws Exception {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;

        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if (file.exists())
                file.delete();
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            unsubscribe();
            //onCompleted();
        } catch (FileNotFoundException e) {
            Loger.i("FileNotFoundException = ");
            e.printStackTrace();
            isDownloadError = true;
            throw new IApiException("dkkd", "没有发现下载文件");
        } catch (SocketException e) {
            Loger.i("FileNotFoundException = ");
            e.printStackTrace();
            isDownloadError = true;
            throw new IApiException("dkkd", "网络超时");
        } catch (IOException e) {
            e.printStackTrace();
            Loger.i("IOException = ");
            isDownloadError = true;
            throw new IApiException("dkkd", "网络读取异常");
        } catch (Exception e) {
            Loger.i("Exception = ");
            e.printStackTrace();
            isDownloadError = true;
            throw new IApiException("dkkd", "下载失败");

        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Loger.e("saveFile"+e.getMessage());
            }
        }
    }

    /**
     * 订阅加载的进度条
     */
    public void subscribeLoadProgress() {
        Subscription subscription = RxBus.getInstance().doSubscribe(FileLoadEvent.class, new Action1<FileLoadEvent>() {
            @Override
            public void call(FileLoadEvent fileLoadEvent) {
                progress(fileLoadEvent.getBytesLoaded(), fileLoadEvent.getTotal());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //TODO 对异常的处理
                Loger.i("subscribeLoadProgress");
                throwable.printStackTrace();
            }
        });
        RxBus.getInstance().addSubscription(this, subscription);
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    public void unsubscribe() {
        Loger.i("unsubscribe = unsubscribe");
        RxBus.getInstance().unSubscribe(this);
    }

    public boolean isDownloadError() {
        return isDownloadError;
    }

    public void setDownloadError(boolean downloadError) {
        isDownloadError = downloadError;
    }
}
