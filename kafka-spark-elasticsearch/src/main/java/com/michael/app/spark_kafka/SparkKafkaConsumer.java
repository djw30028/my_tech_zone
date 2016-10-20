package com.michael.app.spark_kafka;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import scala.Tuple2;

/**
 * 
./bin/spark-submit --class "com.michael.app.spark_kafka.SparkKafkaConsumer" --master spark://Michaels-MBP.hsd1.ga.comcast.net:7077 /Users/michaelwang/project/my_tech_zone/kafka-spark-elasticsearch/target/kafka-spark-elasticsearch-2.0.0-jar-with-dependencies.jar
 
 Kafka 0.10.0 and Spark 2.0.0 and Java
Precondition:
Start Spark
Start zookeeper: zkserver start
Start zkserver start: kafka-server-start /usr/local/etc/kafka/server.properties

Spark console:
http://localhost:8080/

/Users/michaelwang/project/my_tech_zone/kafka-spark-elasticsearch/src/main/java/
com/michael/app/spark_kafka/SparkKafkaConsumer.java
		// zookeeper broker
		String quorum = "localhost:2181";
		String groupId = "test";

		// topic
		Map<String, Integer> topicMap = new HashMap<>();
		topicMap.put("helloworld", 1);

Step 1: in /Users/michaelwang/project/my_tech_zone/kafka-spark-elasticsearch
man clean package

Step 2: Send some messages, Kafka comes with a command line client producer 
$ kafka-console-producer --broker-list localhost:9092 --topic helloworld

Step 3: submit SparkKafkaConsumer
./bin/spark-submit --class "com.michael.app.spark_kafka.SparkKafkaConsumer" --master spark://Michaels-MBP.hsd1.ga.comcast.net:7077 /Users/michaelwang/project/my_tech_zone/kafka-spark-elasticsearch/target/kafka-spark-elasticsearch-2.0.0-jar-with-dependencies.jar


 * @author michaelwang
 *
 */
public class SparkKafkaConsumer implements Serializable {
	private static final Pattern SPACE = Pattern.compile(" ");

	public void retrieveMessage() throws Exception {

		SparkConf sparkConf = new SparkConf().setAppName("SparkKafkaConsumer");
		// Create the context with 2 seconds batch size
		JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, new Duration(2000));

		// zookeeper broker
		String quorum = "localhost:2181";
		String groupId = "test";

		// topic
		Map<String, Integer> topicMap = new HashMap<>();
		topicMap.put("helloworld", 1);

		System.out.println(" **** here 1");
		JavaPairReceiverInputDStream<String, String> messages = KafkaUtils.createStream(streamingContext, quorum,
				groupId, topicMap);

		System.out.println(" **** here 2");
		JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
			@Override
			public String call(Tuple2<String, String> tuple2) {
				System.out.println(" **** here 21");
				return tuple2._2();
			}
		});
		System.out.println(" **** here 3");
		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterator<String> call(String x) {
				return Arrays.asList(SPACE.split(x)).iterator();
			}
		});
		System.out.println(" **** here 4");
		JavaPairDStream<String, Integer> wordCounts = words.mapToPair(new PairFunction<String, String, Integer>() {
			@Override
			public Tuple2<String, Integer> call(String s) {
				return new Tuple2<>(s, 1);
			}
		}).reduceByKey(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer i1, Integer i2) {
				return i1 + i2;
			}
		});

		System.out.println(" **** here 5");

		wordCounts.print();
		streamingContext.start();
		streamingContext.awaitTermination();
	}

	public static void main(String[] args) {
		SparkKafkaConsumer sp = new SparkKafkaConsumer();
		try {
			sp.retrieveMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
