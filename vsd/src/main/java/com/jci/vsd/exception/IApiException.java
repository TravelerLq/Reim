package com.jci.vsd.exception;

import android.support.annotation.NonNull;

/**
 * Created by Victor on 10/31/2017.
 */

public class IApiException extends IException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String responseCode;
    private String errorMsg;
    private String errorCode;

    public void setErrorCode(@NonNull String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @param responseCode
     * @param errorMsg
     */
    public IApiException(String responseCode, String errorMsg) {
        super(errorMsg);
        this.responseCode = responseCode;
        this.setErrorMsg(errorMsg);
    }

    public IApiException(int responseCode, String errorMsg) {
        super(errorMsg);
        this.responseCode = String.valueOf(responseCode);
        this.setErrorMsg(errorMsg);
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
