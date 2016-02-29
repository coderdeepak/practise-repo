/**
 * Site reffered:
 * http://www.coderpanda.com/jms-domains-publish-subscribe-jms-messaging-domain/
 */
package com.test.third.topic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.test.common.EventMessage;

public class Publisher {

	private TopicConnectionFactory factory = null;
	private TopicConnection topicConnection = null;
	private TopicSession topicSession = null;
	private TopicPublisher publisher = null;
	private Topic topic = null;

	public void sendMessage(){
		try {
			factory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_BROKER_URL);
			topicConnection = factory.createTopicConnection();
			topicConnection.start();
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = topicSession.createTopic("SAMPLETOPIC1");
			publisher = topicSession.createPublisher(topic);
			EventMessage eventMessage = new EventMessage(1,"Send event object");
			//TextMessage message = session.createTextMessage();
			ObjectMessage objectMessage =  topicSession.createObjectMessage(eventMessage);
			//message.setText("Hello ...This is a sample message..sending from FirstClient");
			objectMessage.setObject(eventMessage);
			//producer.send(message);
			publisher.publish(objectMessage);
			//System.out.println("Sent: " + message.getText());
			System.out.println("Sent: ");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				topicSession.close();
				topicConnection.close();
			}catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Publisher publisher = new Publisher();
		publisher.sendMessage();

	}

}
