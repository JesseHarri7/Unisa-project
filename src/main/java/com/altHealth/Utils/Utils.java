package com.altHealth.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.ManagedBean;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import com.altHealth.entity.SysParameters;
import com.altHealth.mappings.ModelMappings;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

@ManagedBean
public class Utils {

	@Autowired
	ServiceHelper service;

	public Double calcVAT(Double value, Integer vatPerc) {
		Double vat = vatPerc.doubleValue() / 100;

		Double vatCost = value * vat;
		Double costIncl = value + vatCost;

		return costIncl;
	}

	public File writeTempHTMLFile(String invNum, String html, String css) throws IOException {

		html = html.replaceAll("../res", "../unisa-project/src/main/webapp/res");
		String toWrite = css + html;
		File tmpFile = File.createTempFile(invNum, ".html");
		FileWriter writer = new FileWriter(tmpFile);
		writer.write(toWrite);
		writer.close();

		return tmpFile;
	}

	public String generatePDFFromHTML(String invNum, File file) throws IOException {
		// IO
		File htmlSource = file;
		File pdfDest = new File("src/output/" + invNum + ".pdf");

		PdfWriter writer = new PdfWriter(pdfDest);
		PdfDocument pdfDocument = new PdfDocument(writer);
		pdfDocument.setDefaultPageSize(new PageSize(1000f, 1450f));

		// pdfHTML specific code
		ConverterProperties converterProperties = new ConverterProperties();
		HtmlConverter.convertToPdf(new FileInputStream(htmlSource), pdfDocument, converterProperties);

		return pdfDest.getAbsolutePath();
	}

	/**
	 * Outgoing Mail (SMTP) Server requires TLS or SSL: smtp.gmail.com (use
	 * authentication) Use Authentication: Yes Port for SSL: 465
	 */
	public void sendEmailAtt(String toEmail, String attPath, String subject) {
		toEmail = "jesse.harri@gmail.com";
		// Recipient's email ID needs to be mentioned.
		String to = toEmail;

		// Sender's email ID needs to be mentioned
		SysParameters settings = service.getSysParaService().readById(ModelMappings.COMPANY_ID);
		String from = settings.getEmail();

		final String username = from;// change accordingly
		final String password = settings.getEmailPass();// change accordingly

		System.out.println("SSLEmail Start");

		Properties props = new Properties(); 
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host 
//		props.put("mail.smtp.socketFactory.port", "465"); // SSL Port 
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
		props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
		props.put("mail.smtp.starttls.enable","true");
//		props.put("mail.smtp.port", "465"); // SMTP Port
//		props.put("mail.smtp.ssl.enable", "true");
		 

		// Assuming you are sending email through relay.jangosmtp.net
		/*
		 * String host = "relay.jangosmtp.net";
		 * 
		 * Properties props = new Properties(); props.put("mail.smtp.auth", "true");
		 * props.put("mail.smtp.starttls.enable", "true"); props.put("mail.smtp.host",
		 * host); props.put("mail.smtp.port", "25");
		 */

		/*
		 * Authenticator auth = new Authenticator() { //override the
		 * getPasswordAuthentication method protected PasswordAuthentication
		 * getPasswordAuthentication() { return new PasswordAuthentication(from,
		 * password); } };
		 * 
		 * Session session = Session.getDefaultInstance(props, auth);
		 */
/*
		//Get the Session object. 
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		System.out.println("Session created");
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText("");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attPath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attPath);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport trans= session.getTransport("smtp");
	        trans.connect("smtp.gmail.com", username, password);
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
*/
	}

}
