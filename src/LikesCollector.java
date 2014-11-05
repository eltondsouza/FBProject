import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.BSON;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;


public class LikesCollector {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( "localhost" );
		DB db = mongoClient.getDB( "facebook" );
		DBCollection pagesColl = db.getCollection("pages");
		DBCollection likedColl = db.getCollection("likes");
		//DBCollection friendsList = db.getCollection("newFriendsList");	
		int count = (int) pagesColl.getCount();
		
		DBObject query = new BasicDBObject("isCore",true);
		DBCursor pagesCollCursor = pagesColl.find();
		
		FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBANNSUin44V9IND1sECJ21WndNxXXjxNo5tFVlZAAmyNeJ8gdv6Gank1iQxRwTz69juRcGeugDlK1MEnlvBZB5mXxJoL0xClUHhi3IBd9DaijEW2ODsmEh3rc4ROJsWcmPUQi4GBf5xUUeELRMbEbtJjBUQGkAdY2rTj5zlMZA8ZC6rdxdHHH4vgxS1PdPOZBZCohfAd437");
		
	while(pagesCollCursor.hasNext())
	{
				
		JsonObject likes = facebookClient.fetchObject((String)pagesCollCursor.next().get("id")+"/likes", JsonObject.class,Parameter.with("limit", 1000));
		
		
		ArrayList<JsonObject> pagesLiked = new ArrayList<JsonObject>();
		/*for(int i=0;i<likes.getJsonArray("data").length();i++)
		{
			pagesLiked.add(likes.getJsonArray("data").getJsonObject(i));
		}
		*/
		
		
			
		
			String jsonAsString = likes.toString();
			System.out.println(jsonAsString);
			DBObject doc = new BasicDBObject("id",(String)pagesCollCursor.curr().get("id")).append("name", (String)pagesCollCursor.curr().get("name")).append( "likeData",(DBObject) JSON.parse(jsonAsString));
			try{
				likedColl.insert(doc);
			}
			catch(MongoException e)
			{
				System.out.println(e.getMessage());
			}
		}
		//System.out.println(firstPageName);
		}
	
}
