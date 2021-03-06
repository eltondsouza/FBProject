import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.NewBSONDecoder;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class GephiNWCreator {
	public static void main(String[] args) throws UnknownHostException {
	
		
			MongoClient mongoClient = new MongoClient( "localhost" );
			DB db = mongoClient.getDB( "facebook" );
			DBCollection likedColl = db.getCollection("likes");
			DBCollection gephi = db.getCollection("gephi");
			DBCollection pages = db.getCollection("pages");
			
			DBCursor likesCursor = likedColl.find();
			
			while(likesCursor.hasNext())
			{
				
				
				BasicDBList data =(BasicDBList) ((DBObject)(likesCursor.next().get("likeData"))).get("data");
				
				
				
				for (Object object : data) {
					DBObject doc = new BasicDBObject("source",(String)likesCursor.curr().get("id"))
					.append("source_name", (String)likesCursor.curr().get("name"))					
					.append("target",((String)((DBObject) object).get("id")))
					.append("target_name", ((String)((DBObject) object).get("name")));
					
					DBObject src = pages.findOne(new BasicDBObject("id",(String)likesCursor.curr().get("id")));
					DBObject dest = pages.findOne(new BasicDBObject("id",((String)((DBObject) object).get("id"))));
					//System.out.println(object);
					if(src!=null && dest!=null)
					gephi.insert(doc);
				}
				
				
				/*System.out.println(foo.size());
				System.out.println(foo);*/
			}
	}
}
