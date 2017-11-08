package com.qslib.soap;

/**
 * Created by Dang on 6/2/2016.
 */
public class SoapResponse {
    // KEY
    public static final String RESULT = "Result";
    public static final String IS_ERROR = "isError";
    public static final String ERROR_MESSAGE = "ErrorMessage";
    public static final String ERROR_STACK_TRACE = "ErrorStackTrace";
    public static final String ID = "ID";
    public static final String STATUS = "Status";

    private String result;
    private String isError;
    private String errorMessage;
    private String errorStackTrace;
    private String id;
    private String status;

    public SoapResponse() {
    }

    public SoapResponse(String result, String isError, String errorMessage, String errorStackTrace, String id, String status) {
        this.result = result;
        this.isError = isError;
        this.errorMessage = errorMessage;
        this.errorStackTrace = errorStackTrace;
        this.id = id;
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorStackTrace() {
        return errorStackTrace;
    }

    public void setErrorStackTrace(String errorStackTrace) {
        this.errorStackTrace = errorStackTrace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SoapResponse{" +
                "result='" + result + '\'' +
                ", isError='" + isError + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorStackTrace='" + errorStackTrace + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
