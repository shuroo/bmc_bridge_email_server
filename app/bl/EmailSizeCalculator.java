package bl;

public class EmailSizeCalculator {
    public static long calculateEmailSize(String message, String body, String from, String to, long attachmentSize) {
        // Calculate the size of the email
        long size = 0;

        size += getSize(message);
        size += getSize(body);
        size += getSize(from);
        size += getSize(to);

        // Add the size of the attachment, accounting for base64 encoding (approximately 33% larger)
        size += attachmentSize + (long) (attachmentSize * 0.33);

        return size;
    }

    private static long getSize(String str) {
        // Return the length of the string in bytes
        return str != null ? str.getBytes().length : 0;
    }
}
