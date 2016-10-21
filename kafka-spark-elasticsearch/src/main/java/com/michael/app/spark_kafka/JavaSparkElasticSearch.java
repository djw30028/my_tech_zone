package com.michael.app.spark_kafka;

import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.spark_project.guava.collect.ImmutableList;
import org.spark_project.guava.collect.ImmutableMap;

/**
 * Working fine, save to http://127.0.0.1:9200/_plugin/head/
 * 

 {
  "_index": "myspark",
  "_type": "java",
  "_id": "AVfkkxP09nPnE6QR7vUG",
  "_score": 1,
  "_source": {
    "OTP": "Otopeni",
    "SFO": "San Fran"
  }
}
,
{
  "_index": "myspark",
  "_type": "java",
  "_id": "AVfkkxQI9nPnE6QR7vUH",
  "_score": 1,
  "_source": {
    "one": 1,
    "two": 2
  }
}

 */
public class JavaSparkElasticSearch {

	public void saveToES() throws Exception {
		// Create context with a 2 seconds batch interval
		System.out.println(" **** JavaSparkElasticSearch here 1");
		
		SparkConf sparkConf = new SparkConf().setMaster("local[2]").setAppName("JavaDirectKafkaWordCount");// .setMaster("local");//.setMaster(master);
		sparkConf.set("es.nodes", "localhost:9200");
		sparkConf.set("es.index.auto.create", "true");

		JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		System.out.println(" **** JavaSparkElasticSearch here 2");

		Map<String, ?> numbers = ImmutableMap.of("one", 1, "two", 2);
		Map<String, ?> airports = ImmutableMap.of("OTP", "Otopeni", "SFO", "San Fran");

		JavaRDD<Map<String, ?>> javaRDD = jsc.parallelize(ImmutableList.of(numbers, airports));
		JavaEsSpark.saveToEs(javaRDD, "myspark/java12");
		System.out.println(" **** JavaSparkElasticSearch here 3");

	}

	public static void main(String[] args) {
		try {
			new JavaSparkElasticSearch().saveToES();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
