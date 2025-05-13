package exceptions;

/**
 * Class to handle the case of an unknown vendor
 */
public class NoSuchVendorException extends Exception{
    public NoSuchVendorException(String message) {
        super(message);
    }

    public NoSuchVendorException(String message, Throwable cause) {
        super(message, cause);
    }
}
