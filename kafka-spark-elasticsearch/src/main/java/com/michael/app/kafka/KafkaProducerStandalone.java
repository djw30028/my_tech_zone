package com.michael.app.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


/**
 * Run this in side eclipse and check terminal of  : 
 * kafka-console-consumer --zookeeper localhost:2181 --topic helloworld --from-beginning

mvn exec:java -Dexec.mainClass="com.michael.app.kafka.KafkaProducerStandalone"
 * @author michaelwang
 *
 */
public class KafkaProducerStandalone {
    private static final String TOPIC = "helloworld";
    private static final String BROKER_HOST = "localhost:9092";
	 
	private void process() throws Throwable {
		 for(int i = 0; i < 20; i++) {
		   KafkaProducerExecutor.getInstance().sendTo(Integer.toString(i), Integer.toString(i));
		   try {
			   Thread.sleep(1000);
		   }
		   catch (InterruptedException e) {
			   e.printStackTrace();
		   }
		 }
	}
	public static void main(String[] args) {
		 try {
			new KafkaProducerStandalone().process();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
