import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.BSON;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;


public class ExtendedPageCollector {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( "localhost" );
		DB db = mongoClient.getDB( "facebook" );
		DBCollection pagesColl = db.getCollection("pages");
		//DBCollection friendsList = db.getCollection("newFriendsList");	
		int count = (int) pagesColl.getCount();
		DBCursor pagesCollCursor = pagesColl.find();
		
		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBANNSUin44V9IND1sECJ21WndNxXXjxNo5tFVlZAAmyNeJ8gdv6Gank1iQxRwTz69juRcGeugDlK1MEnlvBZB5mXxJoL0xClUHhi3IBd9DaijEW2ODsmEh3rc4ROJsWcmPUQi4GBf5xUUeELRMbEbtJjBUQGkAdY2rTj5zlMZA8ZC6rdxdHHH4vgxS1PdPOZBZCohfAd437");
		
	for(int j=0;j<count;j++)
	{
				
		JsonObject likes = facebookClient.fetchObject(pagesCollCursor.next().get("id")+"/likes", JsonObject.class,Parameter.with("limit", 1000));
		ArrayList<JsonObject> pagesLiked = new ArrayList<JsonObject>();
		
		
		for(int i=0;i<likes.getJsonArray("data").length();i++)
		{
			pagesLiked.add(likes.getJsonArray("data").getJsonObject(i));
		}
		
		for (JsonObject jsonObject : pagesLiked) {
			String jsonAsString = jsonObject.toString();
			DBObject doc = (DBObject) JSON.parse(jsonAsString);
			try{
				pagesColl.insert(doc);
			}
			catch(MongoException e)
			{
				System.out.println(e.getMessage());
			}
		}
		//System.out.println(firstPageName);
		}
	}
}
