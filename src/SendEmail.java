import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Scanner;


public class SendEmail {
    public static void main(String[] args) {
        String sendrmailid = "send email.com";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Destination mail: ");
        String destmailid = sc.next();
        final String uname = "your email adress";
        final String pwd = "pwd";



        String smtphost = "smtp.gmail.com";
        //Set properties and their values
        Properties propvls = new Properties();
        propvls.setProperty("mail.transport.protocol","smtp");
        propvls.put("mail.smtp.auth", "true");
        propvls.put("mail.smtp.starttls.enable", "true");
        propvls.put("mail.smtp.host", smtphost);
        propvls.put("mail.smtp.port", "465");
        propvls.put("mail.smtp.socketFactory.port","smtp.gmail.com");
        propvls.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        propvls.put("mail.smtp.socketFactory.fallback", "false");
        propvls.setProperty("mail.smtp.quitwait", "false");

        //Create a Session object & authenticate uid and pwd
        Session sessionobj = Session.getInstance(propvls,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(uname, pwd);
                    }
                });

        try {

            //Create MimeMessage object & set values
            Message messageobj = new MimeMessage(sessionobj);
            messageobj.setFrom(new InternetAddress(sendrmailid));
            messageobj.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destmailid));


            messageobj.setSubject("This is test Subject");
            messageobj.setText("Checking sending emails by using JavaMail API");
            Multipart multipart = null;

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("This is message body");

            multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String filename = "/home/radixxx/file.txt";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            messageobj.setContent(multipart);

            //Now send the message
            Transport.send(messageobj);
            System.out.println("Your email sent successfully....");


        } catch (MessagingException exp) {
            throw new RuntimeException(exp);
        }
    }

}
