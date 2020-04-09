package mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息的消费者
 *
 */
public class Consumer{
	
	public static void main(String[] args) {
		ConnectionFactory connFactory = new ActiveMQConnectionFactory(
				//ActiveMQConnectionFactory.DEFAULT_USER,
				//ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"u1","u1",
				"tcp://localhost:61616");
		try {
			Connection conn = connFactory.createConnection();
			conn.start();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination dest = session.createQueue("hello"); 
			MessageConsumer consumer = session.createConsumer(dest);
			
			//设置消息的监听
			consumer.setMessageListener(new MessageListener(){

				@Override
				public void onMessage(Message message) {
					
					//这里可以利用线程池处理任务，把每个消息封装成一个Task
					if (message == null) {
						return;
					}
					if (message instanceof TextMessage) {//获取字符串Message
						TextMessage msg = (TextMessage)message;
						System.out.println("consumer receive msg "+ msg);
					}
					if (message instanceof MapMessage) {//获取MapMessage
						MapMessage msg = (MapMessage)message;
						try {
							System.out.println("map msg "+ "key name value: "+msg.getString("name"));
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
			});
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
