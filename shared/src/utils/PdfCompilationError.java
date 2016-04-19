package utils;

public class PdfCompilationError extends Exception {
    // TODO : see why java does not use the constructor from Exception
    public PdfCompilationError(String message, Throwable cause) {
        super(message, cause);
    }
}
