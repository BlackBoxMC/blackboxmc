package net.ioixd.blackbox.exceptions;

public class MissingFunctionException extends RuntimeException {
    public MissingFunctionException(String errorMessage) {
        super(errorMessage);
    }

    public MissingFunctionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
