/**
 * link reffered : http://www.coderpanda.com/jms-tutorial-sending-object-as-message-in-jms/
 */
package com.test.second.sendobject;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.test.common.EventMessage;

/**
 * 
 * @author Deepak.Keswani
 *
 */
public class Producer {

	private ConnectionFactory factory = null;
	private Connection connection = null;

	private Session session = null;

	private Destination destination = null;

	private MessageProducer producer = null;

	public static void main(String args[]) {
		Producer sender = new Producer();
		sender.sendMessage();
	}	

	public void sendMessage() {
		try {
			factory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("SAMPLEQUEUE");
			producer = session.createProducer(destination);
			EventMessage eventMessage = new EventMessage(1,"Send event object");
			//TextMessage message = session.createTextMessage();
			ObjectMessage objectMessage =  session.createObjectMessage(eventMessage);
			//message.setText("Hello ...This is a sample message..sending from FirstClient");
			objectMessage.setObject(eventMessage);
			//producer.send(message);
			producer.send(objectMessage);
			//System.out.println("Sent: " + message.getText());
			System.out.println("Sent: ");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				session.close();
				connection.close();
			}catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
