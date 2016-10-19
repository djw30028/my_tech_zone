package com.hellokoding.springboot.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * 
 * @author michaelwang
 *
 */
public class KafkaConsumerStandalone {
	
	private static final String TOPIC = "helloworld";
	private static final String BROKER_HOST = "localhost:9092";
	 
	private void process() throws Throwable {
		System.out.println(" KafkaConsumerStandalone  ");
		
		 Properties props = new Properties();
	     props.put("bootstrap.servers", BROKER_HOST);
	     props.put("group.id", "test");
	     //props.put("group.id", TOPIC);
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     
	     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList(TOPIC));
	     
	     while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("****** offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
	     }
	     
	}
	
	public static void main(String[] args) {
		 try {
			new KafkaConsumerStandalone().process();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
