package model;

/**
 * Class to manage and use string constants such has, properties keys etc.
 */
public final class Constants {

    public static final String GENERAL_CONFIG_PREFIX = "play.mailer";

    public static final String HOST_PROPS_KEY = "mail.smtp.host";
    public static final String PORT_PROPS_KEY = "mail.smtp.port";
    public static final String AUTH_PROPS_KEY = "mail.smtp.auth";
    public static final String STARTTLS_PROPS_KEY = "mail.smtp.starttls.enable";

    public static final String GMAIL_VENDOR_NAME = "gmail";


    public static final String MAILING_EVENTS_FILE = "emails-log.csv";


    /**
     * Since I failed to fetch the hosts etc from application.conf, fetching it from class constants..
     * For security, the passwords will be fetched, however, from the env variables.
     */

  //  gmail_
}
