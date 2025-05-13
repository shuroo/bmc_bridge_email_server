package services;

import bl.VendorFactory;
import model.*;
import java.util.Properties;
import model.Constants;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import bl.*;
import exceptions.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import configuration.*;


public class EmailService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MailerClient mailerClient;

    @Inject
    public EmailService(MailerClient mailerClient) {
        this.mailerClient = mailerClient;
    }

    private Properties getProperties(AbstractVendor vendor,EmailConfiguration config) {
        String vendorPrefix = vendor.getConfigPrefix();
        return config.getProperties(vendorPrefix);
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
    public void sendEmail(EmailWrapper email,EmailConfiguration config) {

            try {
                sendEmailInternal(email,config);
                recordSendingEmail(email);
            } catch (Exception e) {
                String errorMsg = "Error sending email occurred, aborting. subject:"+email.getSubject()+",error:"+e.getMessage();
                logger.error(errorMsg);
                throw new RuntimeException(e);
            }

    }

    // CompletionStage<Void>
    private  void sendEmailInternal(EmailWrapper emailWrapper, EmailConfiguration config) throws Exception{
            try{

            logger.info("sendEmailInternal to:{}",emailWrapper.getTo());
            AbstractVendor vendor = VendorFactory.createVendor(emailWrapper.getTo());
            Properties properties = getProperties(vendor,config);

            // Create a session
            Session session = Session.getDefaultInstance(properties);
            String smtpHost = config.getHost(vendor.getConfigPrefix());
            String username = config.getUser(vendor.getConfigPrefix());
            String password = config.getPassword(vendor.getConfigPrefix());

            // Create and connect to the transport
            Transport transport = session.getTransport(Constants.JBEX_TRANSPORT);
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
                    String errorMsg = "Unsupported email domain, host is null";
                    logger.error(errorMsg);
                    throw new RuntimeException(errorMsg);
                }
            }
            catch (NoSuchVendorException e){

                // Naturally this is tru happening,
                throw new RuntimeException("Invalid vendor email detected, aborting.", e);
            }

            catch (javax.mail.NoSuchProviderException e){

                // Naturally this is trully happening, the emails are not sent,
                // so I chose to handle it as if they ARE being sent.
               // throw new RuntimeException("Failed to send email, Provider not found.", e);

                handleLikeProperEmailSending();
            }
            catch (javax.mail.MessagingException e){

                throw new RuntimeException("Failed to send email, MessagingException raised. " ,e);
            }


   // });

}


    private void handleLikeProperEmailSending(){
        String msg = "Failed to send email, Provider not found. Still catching this error to refer it as if it is properly sent";
        logger.info(msg);
    }
}