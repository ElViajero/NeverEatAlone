package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.logger.helper;

import java.util.List;
import java.util.Map;

public class LoggerHelper {

	public static void printrequestMap(Map<String,String[]> request){
		System.out.println("************************");
		System.out.println("request map is : ");

		for(Map.Entry<String, String[]> entry : request.entrySet()){

			System.out.println("key : " + entry.getKey());
			System.out.println("values : ");

			for(int i=0;i<entry.getValue().length;i++){
				System.out.println(entry.getValue()[i]);
			}

			System.out.println("-------------------------------------------");

		}
		System.out.println("************************");

	}

	public static void printresultMap(List<Map<String,String>> result){

		for( Map<String, String> map:result){
			System.out.println("************************");
			System.out.println("RESULT map is : ");

			for(Map.Entry<String, String> entry : map.entrySet()){

				System.out.println("key : " + entry.getKey());
				System.out.println("value : "+ entry.getValue());

				System.out.println("-------------------------------------------");

			}
			System.out.println("************************");
		}

	}

}
