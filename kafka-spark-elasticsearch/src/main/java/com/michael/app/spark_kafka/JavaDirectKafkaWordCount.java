package com.michael.app.spark_kafka;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.spark_project.guava.collect.ImmutableList;
import org.spark_project.guava.collect.ImmutableMap;

import kafka.serializer.StringDecoder;

/*
 ./bin/spark-submit --class "com.michael.app.spark_kafka.JavaDirectKafkaWordCount" --master spark://Michaels-MBP.hsd1.ga.comcast.net:7077 /Users/michaelwang/project/my_tech_zone/kafka-spark-elasticsearch/target/kafka-spark-elasticsearch-2.0.0-jar-with-dependencies.jar
  
  kafka-console-producer --broker-list localhost:9092 --topic helloworld
 
  
  ====================================
  Works local with eclipse
   
  Kafka producer:
  mvn exec:java -Dexec.mainClass="com.michael.app.kafka.KafkaProducerStandalone"
  
   
 */
public class JavaDirectKafkaWordCount implements Serializable {
	private static final Pattern SPACE = Pattern.compile(" ");
	int index = 0;

	public void retrieveMessage() throws Exception {
		String brokers = "localhost:9092";
		String topics = "helloworld";

		// Create context with a 2 seconds batch interval
		SparkConf sparkConf = new SparkConf().setMaster("local[2]").setAppName("JavaDirectKafkaWordCount");// .setMaster("local");//.setMaster(master);
		sparkConf.set("es.nodes", "localhost:9200");
		sparkConf.set("es.index.auto.create", "true");
		sparkConf.set("spark.driver.allowMultipleContexts", "true");

		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2));

		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("metadata.broker.list", brokers);
		Set<String> topicsSet = Collections.singleton(topics); // new
																// HashSet<>(Arrays.asList(topics.split(",")));

		System.out.println(" **** JavaDirectKafkaWordCount here 1");

		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(jssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topicsSet);

		System.out.println(" **** JavaDirectKafkaWordCount here 2");

		directKafkaStream.foreachRDD(rdd -> {
			System.out.println("******--- New RDD with " + rdd.partitions().size() + " partitions and " + rdd.count()
					+ " records");
			if (rdd.count() > 0) {
				index++;

				System.out.println(" xxx insdie--- " + index);

				Map<String, String> number = rdd.collectAsMap();

				JavaRDD<Map<String, ?>> javaRDD = jssc.sparkContext().parallelize(ImmutableList.of(number));

				JavaEsSpark.saveToEs(javaRDD, "myspark/java16");
			}
		});

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
