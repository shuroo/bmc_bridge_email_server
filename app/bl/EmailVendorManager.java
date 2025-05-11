//package bl;
//
//
//import play.libs.mailer.Email;
//import play.libs.mailer.MailerClient;
//
//public class EmailVendorManager {
//
//    private final MailerClient mailerClient;
//
//
//    @Inject
//    public EmailVendorManager(MailerClient mailerClient, Configuration configuration) {
//        this.mailerClient = mailerClient;
//        this.configuration = configuration;
//    }
//
//    public void sendEmail(String from, String to, String subject, String body) {
//
//        AbstractVendor vendor = VendorFactory.createVendor(to);
//
//        Properties properties = vendor.getProperties();
//
//        // Get the Session object
//        Session session = Session.getInstance(properties,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(vendor.getUser(), vendor.getPassword());
//                    }
//                });
//
//        try {
//            // Create a default MimeMessage object
//            Message mimeMessage = new MimeMessage(session);
//
//            // Set From, To, Subject, and the message body
//            mimeMessage.setFrom(new InternetAddress(from));
//
//        String smtpHost = determineSmtpHost(to);
//        if (smtpHost != null) {
//            // Set up your email with the correct SMTP host
//            Email email = new Email()
//                    .setSubject(subject)
//                    .setFrom(from)
//                    .addTo(to)
//                    .setBodyHtml(body);
//
//            // This is where you would configure the mailerClient if needed
//            mailerClient.send(email);
//        } else {
//            System.out.println("Unsupported email domain.");
//        }
//    }
//
//
//}
