package vttp2023.batch3.csf.assessment.cnserver.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;

@Component
public class NewsUtility {

    public static News payloadToNews(String payload,String imgId){

        JsonObject o=Json.createReader(new StringReader(payload)).readObject();

		News n= new News();

        n.setTitle(o.getString("title"));
		n.setDescription(o.getString("description"));
		n.setImage(imgId);
		n.setPostDate(System.currentTimeMillis());
		
        JsonArray jTags=o.getJsonArray("tags");

        List<String> tags= new ArrayList<>();

        for(JsonValue j:jTags){
            JsonObject tagObj=(JsonObject)j;
           tags.add(tagObj.getString("tag"));
        }
        n.setTags(tags);
		

		return n;

    }

    public static List<News> docToNews(List<Document>docs){
        List<News> newList= new ArrayList<>();



        for(Document d:docs){
            News n = new News();
            //n.setId(d.getString("_id"));
            n.setDescription(d.getString("description"));
            n.setImage("image");
            //n.setPostDate(d.getLong("postDate"));
            List<String> tags=d.getList("tags",String.class);
            n.setTags(tags);
            n.setTitle(d.getString("title"));
            newList.add(n);
            
            
        }

        return newList;
    }

    public static String newsToJson(List<News> newslist){
      JsonArrayBuilder ab= Json.createArrayBuilder();

        
      for(News n: newslist){
        
      JsonArrayBuilder jtag= Json.createArrayBuilder();
        for(String t:n.getTags()){
            jtag.add(t);
        }
        ab.add(Json.createObjectBuilder()
       //.add("id",n.getId())
        .add("description",n.getDescription())
        .add("image",n.getImage())
        //.add("postDate",n.getPostDate())
        .add("title",n.getTitle())
        .add("tags",jtag)).build();

        
      }

      return ab.build().toString();
    
    }

    public static List<TagCount> docToTagCounts(List<Document>docs){
        List<TagCount> tagList= new ArrayList<>();

        for(Document d:docs){
            TagCount t= new TagCount(d.getString("_id"), d.getInteger("count"));
            tagList.add(t);
        }
        return tagList;

    }

    public static String tcToJson(List<TagCount> tcList){

        JsonArrayBuilder ab = Json.createArrayBuilder();

        for(TagCount tc:tcList){
            ab.add(Json.createObjectBuilder()
            .add("tag",tc.tag())
            .add("count",tc.count()));
        }

        return ab.build().toString();
        


    }
    
}
