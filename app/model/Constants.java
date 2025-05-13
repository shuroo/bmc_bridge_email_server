package model;

/**
 * Class to manage and use string constants such has, properties keys etc.
 */
public final class Constants {

    public static final String GENERAL_CONFIG_PREFIX = "play.mailer";

    public static final String FROM_ADDRESS = "from";
    public static final String TO_ADDRESS = "to";
    public static final String EMAIL_SUBJECT = "subject";
    public static final String EMAIL_BODY = "body";

    public static final String HOST_PROPS_KEY = "mail.smtp.host";
    public static final String PORT_PROPS_KEY = "mail.smtp.port";
    public static final String AUTH_PROPS_KEY = "mail.smtp.auth";
    public static final String STARTTLS_PROPS_KEY = "mail.smtp.starttls.enable";

    public static final String GMAIL_VENDOR_NAME = "gmail";
    public static final String GMAIL_SUFFIX = "gmail.com";

    public static final String YAHOO_VENDOR_NAME = "yahoo";
    public static final String YAHOO_SUFFIX = "yahoo.com";

    public static final String WALLA_VENDOR_NAME = "walla";
    public static final String WALLA_SUFFIX = "walla.co.il";

    public static final String MAILING_EVENTS_FILE = "emails-log.csv";

    public static final String MAIL_PREFIX ="mail.";

    public static final String HOST_SUFFIX =".host";
    public static final String PORT_SUFFIX =".port";
    public static final String USER_SUFFIX =".user";
    public static final String PASSWORD_SUFFIX =".password";

    public static final String AUTH_SUFFIX =".auth";
    public static final String TLS_SUFFIX =".tls";

    public static final String JBEX_TRANSPORT ="jbexTransport";



    /**
     * Since I failed to fetch the hosts etc from application.conf, fetching it from class constants..
     * For security, the passwords will be fetched, however, from the env variables.
     */

  //  gmail_
}
