import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
/**
 * https://nilhcem.github.io/FakeSMTP/index.html
 * http://www.spamsoap.com/how-to-manually-send-an-email-message-via-telnet-to-port-25/
 * @author oracle
 *
 */
public class JavaMailTest {

	public static void main(String[] args){
		String to = "bart@fox.tv";
		String from = "dave45678@gmail.com";
		String subject = "hello";
		String body = "<h1>test email</h1>";
		boolean bodyIsHTML = true;
		
		try {
			JavaMailTest.sendMail(to,from,subject,body,bodyIsHTML);
		} catch (MessagingException e) {
			System.out.println("Something Bad Happened!");
			e.printStackTrace();
		}
		
	}
    public static void sendMail(String to, String from,
            String subject, String body, boolean bodyIsHTML)
            throws MessagingException {
        
        // 1 - get a mail session
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", 8081);
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.quitwait", "false");
        //NOTE: Session object is part of javax.mail.Session
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props);
        session.setDebug(true);

        // 2 - create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }

        // 3 - address the message
        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 4 - send the message
        Transport transport = session.getTransport();
        transport.connect();
        //transport.connect("johnsmith@gmail.com", "sesame");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}