package com.hellokoding.springboot.elastic;

import java.net.InetAddress;
import java.util.Date;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.hellokoding.springboot.User;

public class ElasticClientAccessor {
	private volatile static ElasticClientAccessor instance = null;
	private static TransportClient client;

	public static ElasticClientAccessor getInstance() throws Throwable {
		if (instance == null)
			synchronized (ElasticClientAccessor.class) {
				if (instance == null) {
					instance = new ElasticClientAccessor();

					client = TransportClient.builder().build()
							.addTransportAddress(
									new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))
							.addTransportAddress(
									new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

				}
			}

		return instance;
	}

	public void saveUser(User user) throws Throwable {
		System.out.println("--- start saveUser ---");

		IndexResponse response = client.prepareIndex()  
				.setIndex("myuserindex").setType("myusertype")
				.setSource(XContentFactory.jsonBuilder().startObject()
				.field("firstName", user.getFirstName())
				.field("lastName", user.getLastName())
				.field("postDate", new Date())
				.endObject())
				.execute().actionGet();
		
		//System.out.println(response);
		System.out.println("--- end saveUser ---");
	}
	
	public User retrieveUserByFirstName(String firstName) {
		SearchResponse response = client.prepareSearch().setSearchType(SearchType.QUERY_AND_FETCH)
				.setFetchSource(new String[] { "id", "firstName", "lastName", "score" }, null)
				.setQuery(QueryBuilders.termQuery("firstName", firstName))
				.execute().actionGet();
		
		//{firstName=michael, lastName=wang}
		for (SearchHit hit : response.getHits()) {
			Map map = hit.getSource();
			User user = new User(map.get("firstName").toString(), map.get("lastName").toString());
			user.setId(hit.getId());
			
			System.out.println(hit.toString());
			
			return user;
			 
		}
		
		return null;
	}
}
