package com.jci.vsd.network.control;

/**
 * Created by heisenberg on 2017/9/4.
 */

public interface UploadProgressListener {
    /**
     * 上传进度
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(long currentBytesCount, long totalBytesCount);
}