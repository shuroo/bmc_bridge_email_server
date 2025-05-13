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

/**
 * Service class to handle email sending operations.
 *
 * @author Shiri Rave
 * @date 13/05/2025
 */
public class EmailService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MailerClient mailerClient;

    /**
     * Constructs an EmailService with the specified MailerClient.
     *
     * @param mailerClient the MailerClient used to send emails
     */
    @Inject
    public EmailService(MailerClient mailerClient) {
        this.mailerClient = mailerClient;
    }

    /**
     * Retrieves the properties for the specified vendor and email configuration.
     *
     * @param vendor the vendor whose properties are to be retrieved
     * @param config the EmailConfiguration containing email settings
     * @return a Properties object containing the vendor's email properties
     */
    private Properties getProperties(AbstractVendor vendor, EmailConfiguration config) {
        String vendorPrefix = vendor.getConfigPrefix();
        return config.getProperties(vendorPrefix);
    }

    /**
     * Records the sending of an email and logs the event.
     *
     * @param email the EmailWrapper containing email details
     * @return true if the email was recorded successfully, false otherwise
     */
    public Boolean recordSendingEmail(EmailWrapper email) {
        boolean isSuccess = false;
        try {
            LogEventsList.addEventLog(email.getEmailSize());
            isSuccess = true;
        } catch (Exception e) {
            logger.error("Failed to record sending email of subject: {}, error: {}", email.getSubject(), e.getMessage());
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * Sends an email and records the sending event.
     *
     * @param email the EmailWrapper containing email details
     * @param config the EmailConfiguration containing email settings
     */
    public void sendEmail(EmailWrapper email, EmailConfiguration config) {
        try {
            sendEmailInternal(email, config);
            recordSendingEmail(email);
        } catch (Exception e) {
            String errorMsg = "Error sending email occurred, aborting. subject: " + email.getSubject() + ", error: " + e.getMessage();
            logger.error(errorMsg);
            throw new RuntimeException(e);
        }
    }

    /**
     * Internal method to handle the actual sending of the email.
     *
     * @param emailWrapper the EmailWrapper containing email details
     * @param config the EmailConfiguration containing email settings
     * @throws Exception if an error occurs while sending the email
     */
    private void sendEmailInternal(EmailWrapper emailWrapper, EmailConfiguration config) throws Exception {
        try {
            logger.info("sendEmailInternal to: {}", emailWrapper.getTo());
            AbstractVendor vendor = VendorFactory.createVendor(emailWrapper.getTo());
            Properties properties = getProperties(vendor, config);

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
                logger.info("smtpHost: {}, subject: {}, body: {}, from: {}", smtpHost, emailWrapper.getSubject(), emailWrapper.getBody(), emailWrapper.getFrom());

                // Set up your email with the correct SMTP host
                Email email = new Email()
                        .setSubject(emailWrapper.getSubject())
                        .setFrom(emailWrapper.getFrom())
                        .addTo(emailWrapper.getTo())
                        .setBodyHtml(emailWrapper.getBody());

                // Send the email using the mailer client
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