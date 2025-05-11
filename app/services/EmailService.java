package services;

import bl.VendorFactory;
import model.*;
import java.util.Properties;
import model.Constants;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import bl.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EmailService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MailerClient mailerClient;
   // private final Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

    @Inject
    public EmailService(MailerClient mailerClient){//,Configuration configuration) {
        this.mailerClient = mailerClient;
//        Play.application.configuration.getString("your.key");
     //   this.configuration = configuration;
    }


    public String getHost(String vendorConfigPrefix){
        String hostConfig = vendorConfigPrefix+".host";
        String host = "TBDDDD!";//this.configuration.getString(hostConfig);
        return  host;
    }

    public int getPort(String vendorConfigPrefix){
        String portConfig = vendorConfigPrefix+".port";
        int port = 0;//this.configuration.getInt(portConfig);
        return port;
    }

    public String getUser(String vendorConfigPrefix){
        String userConfig = vendorConfigPrefix+".user";
        String usrName = "TBDDDD!";//this.configuration.getString(userConfig);
        return usrName;
    }

    /**
     * In a real application, this better should be stored and fetched from env variables..
     * @return
     */
    public String getPassword(String vendorConfigPrefix){
        String passwordConfig = vendorConfigPrefix+".password";
        String pswrd = "TBDDDD!";//configuration.getString(passwordConfig);
        return pswrd;
    }


    private EmailConfig getConfig(AbstractVendor vendor){
        String vendorConfigPrefix = vendor.getConfigPrefix();
        String host = getHost(vendor.getConfigPrefix());
        int port = getPort(vendor.getConfigPrefix());
        String user = getUser(vendor.getConfigPrefix());
        String password = getPassword(vendor.getConfigPrefix());
        return new EmailConfig(host,port,user,password);

    }


    // ToDo: move to general config fetch class, maybe
    private Boolean fetchGeneralAuth(){
        String authConfig = Constants.GENERAL_CONFIG_PREFIX+".auth";
        Boolean auth = true;//configuration.getBoolean(authConfig);
        return  auth;
    }

    private Boolean fetchGeneralStartTLS(){
        String tlsConfig = Constants.GENERAL_CONFIG_PREFIX+".tls";
        Boolean tls = true;//configuration.getBoolean(tlsConfig);
        return  tls;
    }
    private Properties getProperties(AbstractVendor vendor){
        String vendorSuffix = vendor.getConfigPrefix();
        Properties properties = System.getProperties();
        properties.put(Constants.HOST_PROPS_KEY, getHost(vendorSuffix));
        properties.put(Constants.PORT_PROPS_KEY, getPort(vendorSuffix));
        properties.put(Constants.AUTH_PROPS_KEY, fetchGeneralAuth());
        properties.put(Constants.STARTTLS_PROPS_KEY, fetchGeneralStartTLS());
        return properties;
    }

    public Boolean recordSendingEmail(EmailWrapper email){
        boolean isSuccess = false;
        try {
            LogEventsList.addEventLog(email.getEmailSize());
            isSuccess = true;
        }catch(Exception e){
            logger.error("Failed to record sending email of subject:{}",email.getSubject(),",error:{}",e.getMessage());
            isSuccess = false;
        }

        return isSuccess;
    }

    //
    public CompletableFuture<Void> sendEmail(EmailWrapper email) throws Exception {
         return CompletableFuture.runAsync(() -> {
            try {
                sendEmailInternal(email);
                recordSendingEmail(email);
            } catch (Exception e) {
                logger.error("Error sending email occurred, aborting. subject:{}",email.getSubject(),",error:{}",e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    // CompletionStage<Void>
    private  CompletableFuture<Void> sendEmailInternal(EmailWrapper emailWrapper) throws Exception{
        return CompletableFuture.runAsync(() -> {
            try{

            logger.info("sendEmailInternal to:{}",emailWrapper.getTo());
            AbstractVendor vendor = VendorFactory.createVendor(emailWrapper.getTo());

            Properties properties = getProperties(vendor);

            // Create a session
            Session session = Session.getDefaultInstance(properties);
            String smtpHost = getHost(vendor.getConfigPrefix());
            String username = getUser(vendor.getConfigPrefix());
            String password = getHost(vendor.getConfigPrefix());


            // Create and connect to the transport
            Transport transport = session.getTransport("jbexTransport");
            transport.connect(smtpHost, username, password);

                // Create a default MimeMessage object
                Message mimeMessage = new MimeMessage(session);

                // Set From, To, Subject, and the message body
                mimeMessage.setFrom(new InternetAddress(emailWrapper.getFrom()));

                if (smtpHost != null) {

                    logger.info("smtpHost:{}",smtpHost,", subject:{}",emailWrapper.getSubject(),",body:{}",emailWrapper.getBody(),",from:",emailWrapper.getFrom());
                    // Set up your email with the correct SMTP host
                    Email email = new Email()
                            .setSubject(emailWrapper.getSubject())
                            .setFrom(emailWrapper.getFrom())
                            .addTo(emailWrapper.getTo())
                            .setBodyHtml(emailWrapper.getBody());

                    // This is where you would configure the mailerClient if needed
                    mailerClient.send(email);
                } else {
                    System.out.println("Unsupported email domain.");
                }
            } catch (javax.mail.NoSuchProviderException e){

                throw new RuntimeException("Failed to send email, Provider not found.", e);
            }
            catch (javax.mail.MessagingException e){

                throw new RuntimeException("Failed to send email, MessagingException raised. " ,e);
            }


    });
}
}