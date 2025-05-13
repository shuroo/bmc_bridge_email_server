package model;

/**
 * Class to represent email object with required fields - subject, body, from, to
 *
 * @author Shiri Rave
 * @date 13/05/2025
 */
public class EmailWrapper {

    private String subject = null;
    private String body = null;
    private String from = null;
    private String to = null;


    public EmailWrapper(String subject, String body, String from, String to){
        this.subject = subject;
        this.body = body;
        this.from = from;
        this.to = to;
    }


    public Long getEmailSize() {
        // Calculate the size of the email
        long size = 0;

        size += getSize(this.subject);
        size += getSize(this.body);
        size += getSize(this.from);
        size += getSize(this.to);

        return Long.valueOf(size);
    }

    /**
     * Helper private Method to Return the length of the string in bytes
     * @param str
     * @return
     */
    private long getSize(String str) {

        return str != null ? str.getBytes().length : 0;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
