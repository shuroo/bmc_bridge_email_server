package configuration;

import com.typesafe.config.Config;
import model.AbstractVendor;
import model.Constants;

import java.util.Properties;

public class EmailConfiguration {

    private final Config configuration;

    public EmailConfiguration(Config configuration) {
        this.configuration = configuration;
    }

    private String buildStrKey(String prefix, String suffix){
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(suffix);
        return sb.toString();
    }
    public String getHost(String vendorConfigPrefix){
        String hostConfig = buildStrKey(vendorConfigPrefix, Constants.HOST_SUFFIX);
        String host = this.configuration.getString(hostConfig);
        return  host;
    }

    public int getPort(String vendorConfigPrefix){
        String portConfig = buildStrKey(vendorConfigPrefix, Constants.PORT_SUFFIX);
        int port = this.configuration.getInt(portConfig);
        return port;
    }

    public String getUser(String vendorConfigPrefix){
        String userConfig = buildStrKey(vendorConfigPrefix, Constants.USER_SUFFIX);
        String usrName = this.configuration.getString(userConfig);
        return usrName;
    }

    /**
     * In a real application, this better should be stored and fetched from env variables..
     * @return
     */
    public String getPassword(String vendorConfigPrefix){
        // I prefferred to exrtract this from env variables. not sure i'll manage to make the time..
        String passwordConfig = buildStrKey(vendorConfigPrefix, Constants.PASSWORD_SUFFIX);
        String pswrd = configuration.getString(passwordConfig);
        return pswrd;
    }

    // ToDo: move to general config fetch class, maybe
    private Boolean fetchGeneralAuth(){
        String authConfig = buildStrKey(Constants.GENERAL_CONFIG_PREFIX, Constants.AUTH_SUFFIX);

        Boolean auth = configuration.getBoolean(authConfig);
        return  auth;
    }

    private Boolean fetchGeneralStartTLS(){
        String tlsConfig = buildStrKey(Constants.GENERAL_CONFIG_PREFIX, Constants.TLS_SUFFIX);
        Boolean tls = configuration.getBoolean(tlsConfig);
        return  tls;
    }
    public Properties getProperties(String vendorPrefix){
        Properties properties = System.getProperties();
        properties.put("mailer.mock", "true");
        properties.put(Constants.HOST_PROPS_KEY, getHost(vendorPrefix));
        properties.put(Constants.PORT_PROPS_KEY, getPort(vendorPrefix));
        properties.put(Constants.AUTH_PROPS_KEY, fetchGeneralAuth());
        properties.put(Constants.STARTTLS_PROPS_KEY, fetchGeneralStartTLS());
        return properties;
    }
}
