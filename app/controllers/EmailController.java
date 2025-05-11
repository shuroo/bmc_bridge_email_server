package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;
import services.EmailService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import play.api.Play;

public class EmailController extends Controller {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EmailService emailService;

    @Inject
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
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
            //    Play.current.configuration.getString("db.driver");
          //  EmailConfig config = new EmailConfig(to);
            logger.info("Attempt sending mail to:{}",to);
            return emailService.sendEmail(from, to, subject, body)
                    .thenApply(done -> ok(Json.toJson("Email sent successfully")))
                    .exceptionally(ex -> internalServerError("Error sending email: " + ex.getMessage()));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(internalServerError("Failed to send email, Error sending email: " + e.getMessage()));
        }
    }
}