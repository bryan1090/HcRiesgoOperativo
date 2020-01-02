package com.hc.ro.commons;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.annotation.Resource;

@WebServlet(value = "/mail")
public class SendMail extends HttpServlet {
	@Resource(mappedName = "java:jboss/mail/Default")
	private Session mailSession;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		{

			PrintWriter out = response.getWriter();
			try {
				MimeMessage m = new MimeMessage(mailSession);
				Address from = new InternetAddress(
						"imbaqcas21@gmail.com");
				Address[] to = new InternetAddress[] { new InternetAddress(
						"imbaqcas_21@hotmail.com")};

				m.setFrom(from);
				m.setRecipients(Message.RecipientType.TO, to);
				m.setSubject("JBoss AS 7 Mail");
				m.setSentDate(new java.util.Date());
				m.setContent("Mail sent from JBoss hhhhAS 7", "text/plain");
				Transport.send(m);
				out.println("Mail sent!");
			} catch (javax.mail.MessagingException e) {
				e.printStackTrace();
				out.println("Error in Sending Mail: " + e);
			}
		}
	}
}