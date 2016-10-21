package com.michael.app.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerExecutor {
	private static KafkaProducerExecutor instance;
	 
	private static final String TOPIC = "helloworld";
	private static final String BROKER_HOST = "localhost:9092";
	private Producer<String, String> producer;

	private KafkaProducerExecutor() {
		Properties props = new Properties();
		props.put("bootstrap.servers", BROKER_HOST);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
	}

	public void sendTo(String key, String value) {
		sendTo(TOPIC, key, value);
	}
	
	public void sendTo(String topic, String key, String value) {
		System.out.printf("send with topic %s, key=%s value=%s\n", topic, key, value);
		producer.send(new ProducerRecord<String, String>(topic, key, value));
	}
	

	public static KafkaProducerExecutor getInstance() {
		if (instance == null) {
			instance = new KafkaProducerExecutor();
		}
		return instance;
	}
}
