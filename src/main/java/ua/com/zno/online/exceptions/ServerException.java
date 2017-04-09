package ua.com.zno.online.exceptions;

/**
 * Created by quento on 26.03.17.
 */
public class ServerException extends Exception {

    private String message;
    private Throwable cause;
    private boolean enableSuppression;
    private boolean writableStackTrace;

    public ServerException() {
        super();
    }

    public ServerException(String message) {
        super(message);
        this.message = message;
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public ServerException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public ServerException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
        this.cause = cause;
        this.enableSuppression = enableSuppression;
        this.writableStackTrace = writableStackTrace;
    }

    @Override
    public String toString() {
        return "UserException{" +
                "message='" + message + '\'' +
                ", cause=" + cause +
                ", enableSuppression=" + enableSuppression +
                ", writableStackTrace=" + writableStackTrace +
                '}';
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public boolean isEnableSuppression() {
        return enableSuppression;
    }

    public void setEnableSuppression(boolean enableSuppression) {
        this.enableSuppression = enableSuppression;
    }

    public boolean isWritableStackTrace() {
        return writableStackTrace;
    }

    public void setWritableStackTrace(boolean writableStackTrace) {
        this.writableStackTrace = writableStackTrace;
    }
}
