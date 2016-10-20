package com.michael.app.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerExecutor {
	private static final String BROKER_HOST = "localhost:9092";
	private KafkaConsumer<String, String> consumer;
	
	public KafkaConsumerExecutor(String topic) {
		 Properties props = new Properties();
	     props.put("bootstrap.servers", BROKER_HOST);
	     props.put("group.id", "test");
	     //props.put("group.id", TOPIC);
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     
	     consumer = new KafkaConsumer<>(props);
	     consumer.subscribe(Arrays.asList(topic));
	}
	
	public void retrieveMessage() {
		 while (true) {
	         ConsumerRecords<String, String> records = consumer.poll(100);
	        // System.out.println(" **** ");
	         for (ConsumerRecord<String, String> record : records)
	             System.out.printf("****** offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
	     }	
	}
}
