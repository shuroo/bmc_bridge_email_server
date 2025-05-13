package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;
import services.EmailService;
import java.io.File;

import com.typesafe.config.Config;
import javax.inject.Inject;
import play.mvc.Controller;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.*;
import configuration.*;
import bl.*;

public class EmailController extends Controller {

    private final Config configuration;
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EmailService emailService;

    @Inject
    public EmailController(Config configuration,EmailService emailService) {
        this.configuration = configuration;
        this.emailService = emailService;
    }

    public Result downloadCsv() {
        String filePath = Constants.MAILING_EVENTS_FILE;
        String csvFilePath = LogEventsList.exportEventsToCsv(filePath);

        if (csvFilePath != null) {
            return ok(new File(csvFilePath));
        } else {
            return internalServerError("Failed to create CSV file.");
        }
    }
    public Result sendEmail(Http.Request request) {

        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest("Expecting JSON data");
        }
        String from = json.findPath(Constants.FROM_ADDRESS).textValue();
        String to = json.findPath(Constants.TO_ADDRESS).textValue();
        String subject = json.findPath(Constants.EMAIL_SUBJECT).textValue();
        String body = json.findPath(Constants.EMAIL_BODY).textValue();

        if (from == null || to == null || subject == null || body == null) {
            return badRequest("Missing parameters");
        }
        try {
            String myValue = this.configuration.getString("mail.gmail.host");
            System.out.println("*****myValue:" + myValue);

            EmailConfiguration emailConfig = new EmailConfiguration(this.configuration);
            logger.info("Attempt sending mail to:{}",to);
            EmailWrapper email = new EmailWrapper(subject, body,from, to);
            emailService.sendEmail(email,emailConfig);
             //        .thenApply(done ->
            return ok(Json.toJson("Email from:"+email.getFrom()+" to:+"+email.getFrom()+" was sent successfully"));//).exceptionally(ex -> internalServerError("Error sending email: " + ex.getMessage()));
        } catch (Exception e) {
                       return badRequest("Failed to send email, Error sending email: " + e.getMessage());
        }
    }
}