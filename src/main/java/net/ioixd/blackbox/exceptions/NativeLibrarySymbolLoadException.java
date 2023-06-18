package net.ioixd.blackbox.exceptions;

public class NativeLibrarySymbolLoadException extends RuntimeException {
    public NativeLibrarySymbolLoadException(String errorMessage) {
        super(errorMessage);
    }
    public NativeLibrarySymbolLoadException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}