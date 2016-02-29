package com.test.second.sendobject;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.test.common.EventMessage;

public class Consumer {
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
    private MessageConsumer consumer = null;
	public void receiveMessage() {
		try {
			factory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("SAMPLEQUEUE");
			consumer = session.createConsumer(destination);
			System.out.println("b4 receive");
			Message message = consumer.receive();
			System.out.println("after receive");
			if(message instanceof ObjectMessage){
				ObjectMessage objectMessage = (ObjectMessage)message;
				EventMessage eventMessage =  (EventMessage)objectMessage.getObject();
				System.out.println("Received message is ----eventMessage.getMessageId()-------> " + eventMessage.getMessageId() + "-------eventMessage.getMessageText()--------" + eventMessage.getMessageText());
			}
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
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Consumer consumer = new Consumer();
		consumer.receiveMessage();

	}

}
