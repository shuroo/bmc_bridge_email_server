package configuration;

import com.typesafe.config.Config;
import model.AbstractVendor;
import model.Constants;

import java.util.Properties;

/**
 * Class to fetch email configurations from the application.conf file.
 *
 * This class provides methods to retrieve various email-related configuration
 * settings such as host, port, username, and password for different vendors.
 * It builds configuration properties for the email client based on the vendor
 * specifications.
 *
 * @author Shiri Rave
 * @date 13/05/2025
 */
public class EmailConfiguration {

    private final Config configuration;

    /**
     * Constructs an EmailConfiguration instance using the provided Config.
     *
     * @param configuration the configuration object that holds email settings
     */
    public EmailConfiguration(Config configuration) {
        this.configuration = configuration;
    }

    /**
     * Builds a complete configuration key by combining a prefix and suffix.
     *
     * @param prefix the prefix for the configuration key
     * @param suffix the suffix for the configuration key
     * @return the complete configuration key
     */
    private String buildStrKey(String prefix, String suffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(suffix);
        return sb.toString();
    }

    /**
     * Retrieves the host for a given vendor configuration prefix.
     *
     * @param vendorConfigPrefix the prefix associated with the vendor
     * @return the host address as a String
     */
    public String getHost(String vendorConfigPrefix) {
        String hostConfig = buildStrKey(vendorConfigPrefix, Constants.HOST_SUFFIX);
        String host = this.configuration.getString(hostConfig);
        return host;
    }

    /**
     * Retrieves the port for a given vendor configuration prefix.
     *
     * @param vendorConfigPrefix the prefix associated with the vendor
     * @return the port number as an int
     */
    public int getPort(String vendorConfigPrefix) {
        String portConfig = buildStrKey(vendorConfigPrefix, Constants.PORT_SUFFIX);
        int port = this.configuration.getInt(portConfig);
        return port;
    }

    /**
     * Retrieves the username for a given vendor configuration prefix.
     *
     * @param vendorConfigPrefix the prefix associated with the vendor
     * @return the username as a String
     */
    public String getUser(String vendorConfigPrefix) {
        String userConfig = buildStrKey(vendorConfigPrefix, Constants.USER_SUFFIX);
        String usrName = this.configuration.getString(userConfig);
        return usrName;
    }

    /**
     * Retrieves the password for a given vendor configuration prefix.
     *
     * In a real application, this should be securely stored and fetched from
     * environment variables.
     *
     * @param vendorConfigPrefix the prefix associated with the vendor
     * @return the password as a String
     */
    public String getPassword(String vendorConfigPrefix) {
        String passwordConfig = buildStrKey(vendorConfigPrefix, Constants.PASSWORD_SUFFIX);
        String pswrd = configuration.getString(passwordConfig);
        return pswrd;
    }

    /**
     * Fetches general authentication configuration.
     *
     * @return true if authentication is required, false otherwise
     */
    private Boolean fetchGeneralAuth() {
        String authConfig = buildStrKey(Constants.GENERAL_CONFIG_PREFIX, Constants.AUTH_SUFFIX);
        return configuration.getBoolean(authConfig);
    }

    /**
     * Fetches general StartTLS configuration.
     *
     * @return true if StartTLS is enabled, false otherwise
     */
    private Boolean fetchGeneralStartTLS() {
        String tlsConfig = buildStrKey(Constants.GENERAL_CONFIG_PREFIX, Constants.TLS_SUFFIX);
        return configuration.getBoolean(tlsConfig);
    }

    /**
     * Constructs a Properties object for the email client configuration
     * based on the provided vendor prefix.
     *
     * @param vendorPrefix the prefix associated with the vendor
     * @return a Properties object containing email configurations
     */
    public Properties getProperties(String vendorPrefix) {
        Properties properties = System.getProperties();
        properties.put("mailer.mock", "true");
        properties.put(Constants.HOST_PROPS_KEY, getHost(vendorPrefix));
        properties.put(Constants.PORT_PROPS_KEY, getPort(vendorPrefix));
        properties.put(Constants.AUTH_PROPS_KEY, fetchGeneralAuth());
        properties.put(Constants.STARTTLS_PROPS_KEY, fetchGeneralStartTLS());
        return properties;
    }
}