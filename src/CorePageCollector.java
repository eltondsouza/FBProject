import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.BSON;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;


public class CorePageCollector {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( "localhost" );
		DB db = mongoClient.getDB( "facebook" );
		DBCollection pagesColl = db.getCollection("pages");
		//DBCollection friendsList = db.getCollection("newFriendsList");	
	
	FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBANNSUin44V9IND1sECJ21WndNxXXjxNo5tFVlZAAmyNeJ8gdv6Gank1iQxRwTz69juRcGeugDlK1MEnlvBZB5mXxJoL0xClUHhi3IBd9DaijEW2ODsmEh3rc4ROJsWcmPUQi4GBf5xUUeELRMbEbtJjBUQGkAdY2rTj5zlMZA8ZC6rdxdHHH4vgxS1PdPOZBZCohfAd437");
	
	
	JsonObject likes = facebookClient.fetchObject("845558332163362/likes", JsonObject.class,Parameter.with("limit", 100));
	
	ArrayList<JsonObject> pagesLiked = new ArrayList<JsonObject>();
	
	
	for(int i=0;i<likes.getJsonArray("data").length();i++)
	{
		pagesLiked.add(likes.getJsonArray("data").getJsonObject(i));
	}
	
	for (JsonObject jsonObject : pagesLiked) {
		String jsonAsString = jsonObject.toString();
		DBObject doc = (DBObject) JSON.parse(jsonAsString);
		pagesColl.insert(doc);
	}
	//System.out.println(firstPageName);
	}
	
}
