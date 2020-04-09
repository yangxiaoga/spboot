package mq.activemq;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息生产者
 *
 */
public class Producer {
	
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	
	public Producer() {
		connectionFactory = new ActiveMQConnectionFactory(
				//ActiveMQConnectionFactory.DEFAULT_USER,
				//ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"u1","u1",
				"tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Producer producer = new Producer();
		producer.send();
	}
	
	public void send() {
		try {
			
			Destination dest = this.getSession().createQueue("hello"); //目标队列
			MessageProducer msgProducer = this.getSession().createProducer(null);
			
			for(int i = 0; i < 20; i++) {
				//创建字符串消息
				TextMessage message = this.getSession().createTextMessage("我是消息 " + i);
				msgProducer.send(dest, message);
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			//创建MAP message
			MapMessage mapMsg = this.getSession().createMapMessage();
			mapMsg.setString("name", "张三");
			msgProducer.send(dest, mapMsg);
			
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (this.getConnection() != null) {
				try {
					this.getConnection().close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
}
