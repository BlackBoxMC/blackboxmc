package net.ioixd.blackbox.exceptions;

public class NativeLibraryLoadException extends RuntimeException {
    public NativeLibraryLoadException(String errorMessage) {
        super(errorMessage);
    }
    public NativeLibraryLoadException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}