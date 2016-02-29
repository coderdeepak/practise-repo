package com.test.third.topic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.test.common.EventMessage;

public class Subscriber2 {

	private TopicConnectionFactory factory = null;
	private TopicConnection topicConnection = null;
	private TopicSession topicSession = null;
	private Destination destination = null;
	 private TopicSubscriber subscriber = null;
	private Topic topic = null;

	public void receiveMessage() {
		try {
			factory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_BROKER_URL);
			topicConnection = factory.createTopicConnection();
			topicSession = topicConnection.createTopicSession(false,  TopicSession.AUTO_ACKNOWLEDGE);
			topic = topicSession.createTopic("SAMPLETOPIC1");
			subscriber = topicSession.createSubscriber(topic);
			topicConnection.start();
			System.out.println("b4 receive");
			Message message = subscriber.receive();
			System.out.println("after receive");
			if (message instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage)message;
				EventMessage eventMessage =  (EventMessage)objectMessage.getObject();
				System.out.println("Received message @ sub2 is ----eventMessage.getMessageId()-------> " + eventMessage.getMessageId() + "-------eventMessage.getMessageText()--------" + eventMessage.getMessageText());
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				topicSession.close();
				topicConnection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Subscriber2 sub2 = new Subscriber2();
		sub2.receiveMessage();
	}

}
