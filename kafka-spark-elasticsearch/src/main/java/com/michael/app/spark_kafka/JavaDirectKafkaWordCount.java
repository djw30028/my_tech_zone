package com.michael.app.spark_kafka;

import java.util.HashMap;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import kafka.serializer.StringDecoder;
import scala.Tuple2;

/*
 ./bin/spark-submit --class "com.michael.app.spark_kafka.JavaDirectKafkaWordCount" --master spark://Michaels-MBP.hsd1.ga.comcast.net:7077 /Users/michaelwang/project/my_tech_zone/kafka-spark-elasticsearch/target/kafka-spark-elasticsearch-2.0.0-jar-with-dependencies.jar
 Not working yet
 */
public class JavaDirectKafkaWordCount implements Serializable {
	private static final Pattern SPACE = Pattern.compile(" ");

	public void retrieveMessage() throws Exception {
		String brokers = "192.168.1.117:2181";
		String topics = "helloworld,test";

		String master = "spark://Michaels-MBP.hsd1.ga.comcast.net:7077";

		// Create context with a 2 seconds batch interval
		SparkConf sparkConf = new SparkConf().setAppName("JavaDirectKafkaWordCount").setMaster(master);
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2));

		Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("metadata.broker.list", brokers);

		System.out.println(" **** JavaDirectKafkaWordCount here 1");

		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, String> messages = KafkaUtils.createDirectStream(jssc, String.class, String.class,
				StringDecoder.class, StringDecoder.class, kafkaParams, topicsSet);

		System.out.println(" **** JavaDirectKafkaWordCount here 2");

		// Get the lines, split them into words, count the words and print
		JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
			@Override
			public String call(Tuple2<String, String> tuple2) {
				return tuple2._2();
			}
		});

		System.out.println(" **** JavaDirectKafkaWordCount here 3");

		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterator<String> call(String x) {
				return Arrays.asList(SPACE.split(x)).iterator();
			}
		});

		System.out.println(" **** JavaDirectKafkaWordCount here 4");

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
		
		System.out.println(" **** JavaDirectKafkaWordCount here 5");
		
		wordCounts.print();

		// Start the computation
		jssc.start();
		jssc.awaitTermination();
	}

	public static void main(String[] args) {
		JavaDirectKafkaWordCount jk = new JavaDirectKafkaWordCount();
		try {
			jk.retrieveMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
