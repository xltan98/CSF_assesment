package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.utils.NewsUtility;

@Repository
public class NewsRepository {
	@Autowired
	MongoTemplate template;

	// TODO: Task 1 
	// Write the native Mongo query in the comment above the method
	
	// db.articles.insertOne({
	// 	_id:123455,
	// 	title: 'hello',
	// 	description: 'testing',
	// 	image: '12n344',
	// 	postDate: 12344345,
	// 	tags: ['hello', 'bye']
	// 	});
	public News saveNews(String payload,String imgId){
	News news=NewsUtility.payloadToNews(payload,imgId);
		String newsId=UUID.randomUUID().toString().substring(0,8);
		news.setId(newsId);

		Document d = new Document()
		.append("_id", news.getId())
		.append("title",news.getTitle())
		.append("description",news.getDescription())
		.append("image",news.getImage())
		.append("postDate",news.getPostDate())
		.append("tags",news.getTags());

		template.insert(d,"articles");
		

		return news;
		
	}


	

	// db.articles.aggregate([{
	// 	$match:{postDate:{$gte:1234}}
	// },
	//  {$unwind:"$tags"},
	//  { $group: { _id: '$tags',count:{ $sum: 1 }}},
	//  {$sort:{postDate:-1}},
	//	{$limit:10}
	//  ])
	 

	// TODO: Task 2 
	// Write the native Mongo query in the comment above the method
	public List<TagCount> showTags(Integer time){
		long current=System.currentTimeMillis();
		long timeInMillie= time*60000;
		
		MatchOperation matchtime=Aggregation.match(Criteria.where("postDate").gte(current-timeInMillie).lte(current+timeInMillie));

		UnwindOperation unwindTags= Aggregation.unwind("tags");
		GroupOperation groupByTags=Aggregation.group("tags").count().as("count");
		SortOperation sortByTagsCount=Aggregation.sort(Sort.by(Direction.DESC,"count"));
		
		Aggregation pipeline= Aggregation.newAggregation(matchtime,unwindTags,groupByTags,sortByTagsCount);
		AggregationResults<Document> results= template.aggregate(pipeline,"articles",Document.class);

		List<Document>docs=results.getMappedResults();

		System.currentTimeMillis(); //use match operation <3000

		List<TagCount> tagList=NewsUtility.docToTagCounts(docs);

		return tagList;
		

	}


	// TODO: Task 3
	// Write the native Mongo query in the comment above the method

	// db.articles.aggregate([{
    //     $match:$and:[{"tags":{$in:["hello"]}},
    //     {postDate:{$gte:1234}}]
    //     }
	//     ])

	public List<News> showNews(Integer time,String tag){

		
		long current=System.currentTimeMillis();
		long timeInMillie= time*60000;

		Criteria c = null;
		c= new Criteria().andOperator(
			 Criteria.where("tags").in(tag),
			Criteria.where("postDate").gte(current-timeInMillie).lte(current+timeInMillie)
		);

		
		MatchOperation matchtime=Aggregation.match(c);
		
		Aggregation pipeline= Aggregation.newAggregation(matchtime);
		
		AggregationResults<Document> results= template.aggregate(pipeline,"articles",Document.class);

		return NewsUtility.docToNews(results.getMappedResults());
	}

	// public List<News> showNews(Integer time,String tag){
	// 	Criteria c = Criteria.where("title").is("hello");

	// 	Query query=Query.query(c);

    //     List<Document>results=template.find(query,Document.class,"articles");
		
    //     return NewsUtility.docToNews(results);

	// }


}
