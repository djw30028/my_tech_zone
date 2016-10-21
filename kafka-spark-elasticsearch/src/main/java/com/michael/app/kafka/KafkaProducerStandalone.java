package com.michael.app.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

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
    private TransportClient client;
    
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
	
	/**
	 * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-multi-get.html
	 * http://localhost:9200/_plugin/head/
	 * 
	 * 9300 is correct value, meaning connect to 9200 curl -XPUT
	 * 'http://localhost:9200/twitter/tweet/1' -d '{"user" :
	 * "kimchy","post_date" : "2009-11-15T14:12:12","message" : "trying out
	 * Elastic Search"}' http://localhost:9200/_plugin/head/ { "_index":
	 * "twitter", "_type": "tweet", "_id": "10", "_score": 1, "_source": {
	 * "user": "michael", "post_date": "2015-11-15T14:12:12", "message": "trying
	 * out Elastic Search" } }
	 * 
	 * @throws Throwable
	 */
	private void putUserElastic() throws Throwable {
		if (client == null)
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		IndexResponse response = null;

		response = client.prepareIndex("users", "user", "1111")
				.setSource(XContentFactory.jsonBuilder().startObject().field("user", "michael wang")
						.field("postDate", new Date()).field("message", "who dont it work, michael").endObject())
				.execute().actionGet();

		System.out.println(response);

	}

	private void putUserElastic2() throws Throwable {
		if (client == null)
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		IndexResponse response = null;

		response = client.prepareIndex().setIndex("users").setType("user") 
				.setSource(XContentFactory.jsonBuilder().startObject().field("user", "cassie wang")
						.field("postDate", new Date()).field("message", "who dont it work, michael").endObject())
				.execute().actionGet();

		System.out.println("response");

	}

	
	/**
	 * x={"user":"michael
	 * wang","postDate":"2016-09-14T21:45:25.799Z","message":"who dont it work,
	 * michael"}
	 * 
	 * @throws Throwable
	 */
	private void retrieve() throws Throwable {
		if (client == null)
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		org.elasticsearch.action.get.GetResponse getresponse = client.prepareGet("users", "user", "1111")
				.setOperationThreaded(false).get();
		GetField x = getresponse.getField("user");
		String json = getresponse.getSourceAsString();

		System.out.println("x=" + json);

		System.out.println(getresponse);

	}

	private void retrieveByName() throws Throwable {
		if (client == null)
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		SearchResponse response = client.prepareSearch().setSearchType(SearchType.QUERY_AND_FETCH)
				.setFetchSource(new String[] { "user" }, null).setQuery(QueryBuilders.termQuery("user", "michael"))
				.execute().actionGet();

		for (SearchHit hit : response.getHits()) {
			Map map = hit.getSource();
			System.out.println(map.toString());
		}

		System.out.println("dd");
		return;
	}

}
