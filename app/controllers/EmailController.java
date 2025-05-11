package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.mailer.Email;
import play.mvc.*;
import services.EmailService;
import java.io.File;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.*;
import bl.*;

public class EmailController extends Controller {


    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EmailService emailService;

    @Inject
    public EmailController(EmailService emailService) {
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
    public CompletionStage<Result> sendEmail(Http.Request request) {

        JsonNode json = request.body().asJson();
        if (json == null) {
            return CompletableFuture.completedFuture(badRequest("Expecting JSON data"));
        }
        String from = json.findPath("from").textValue();
        String to = json.findPath("to").textValue();
        String subject = json.findPath("subject").textValue();
        String body = json.findPath("body").textValue();

        if (from == null || to == null || subject == null || body == null) {
            return CompletableFuture.completedFuture(badRequest("Missing parameters"));
        }
        try {
            logger.info("Attempt sending mail to:{}",to);
            EmailWrapper email = new EmailWrapper(subject, body,from, to);
            return emailService.sendEmail(email)
                            .thenApply(done ->
                                    ok(Json.toJson("Email from:"+email.getFrom()+" was sent successfully"))).exceptionally(ex -> internalServerError("Error sending email: " + ex.getMessage()));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(internalServerError("Failed to send email, Error sending email: " + e.getMessage()));
        }
    }
}