package com.michael.app.elastic;

public class ElasticSearchTest {

	public static void main(String[] args)   {
		System.out.println(" start ElasticSearchTest ");
		User tUser;
		try {
			tUser = ElasticClientAccessor.getInstance().retrieveUserByFirstName("michael");
			System.out.println("tUser="+tUser);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(" end ElasticSearchTest ");
	}

}
