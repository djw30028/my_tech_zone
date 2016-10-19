package com.hellokoding.springboot.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


/**
 * Run this in side eclipse and monitor termial of  : 
 * kafka-console-consumer --zookeeper localhost:2181 --topic helloworld --from-beginning

 * @author michaelwang
 *
 */
public class KafkaProducerStandalone {
    private static final String TOPIC = "helloworld";
    private static final String BROKER_HOST = "localhost:9092";
   
/*	private void process_good() throws Throwable {
		System.out.println(" KafkaProducerStandalone  ");
		
		Properties props = new Properties();
		 props.put("bootstrap.servers", BROKER_HOST);
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 Producer<String, String> producer = new KafkaProducer<>(props);
		 for(int i = 0; i < 100; i++)
		     producer.send(new ProducerRecord<String, String>(TOPIC, Integer.toString(i), Integer.toString(i)));

		 producer.close();
	}*/
	 
	private void process() throws Throwable {
		 for(int i = 0; i < 100; i++) {
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
