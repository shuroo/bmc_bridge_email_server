package exceptions;

/**
 * Custom exception class to handle the case of an unknown vendor.
 *
 * This exception is thrown when an operation involves a vendor that cannot be identified
 * within the application context, such as when attempting to send an email to an unsupported
 * or unrecognized vendor.
 *
 * @author Shiri Rave
 * @date 13/05/2025
 */
public class NoSuchVendorException extends Exception {

    /**
     * Constructs a new NoSuchVendorException with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval
     *                by the {@link #getMessage()} method
     */
    public NoSuchVendorException(String message) {
        super(message);
    }

    /**
     * Constructs a new NoSuchVendorException with the specified detail message and cause.
     *
     * @param message the detail message, which is saved for later retrieval
     *                by the {@link #getMessage()} method
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public NoSuchVendorException(String message, Throwable cause) {
        super(message, cause);
    }
}