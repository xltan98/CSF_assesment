package vttp2023.batch3.csf.assessment.cnserver.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.Payload;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;
import vttp2023.batch3.csf.assessment.cnserver.utils.NewsUtility;

@RestController
@RequestMapping(path="/api")
public class NewsController {

	@Autowired
	NewsService nSvc;

	// TODO: Task 1
	
	@PostMapping(path="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>uploadNews(@RequestPart String news,@RequestPart MultipartFile imgFile){

		try{
			String mediaType=imgFile.getContentType();
            InputStream is=imgFile.getInputStream();

            String newsId=nSvc.postNews(imgFile, news);
            
            JsonObject resp=Json.createObjectBuilder().add("newsId",newsId).build();
            return ResponseEntity.ok(resp.toString());
 
		}catch(IOException ex){
            JsonObject resp=Json.createObjectBuilder()
            .add("error",ex.getMessage()).build();

            return ResponseEntity.status(500).body(resp.toString());
         
        }
	}
	// TODO: Task 2

	@GetMapping(path="/")
	public ResponseEntity<String>landingPage(@RequestParam Integer time){

		List<TagCount>list=nSvc.getTags(time);

		return ResponseEntity.ok(NewsUtility.tcToJson(list));

		
	}

	// TODO: Task 3

	@GetMapping(path="/time")
	public ResponseEntity<String>getArticles(@RequestParam Integer time,@RequestParam String tag){
		List<News> list= nSvc.getNewsByTag(time,tag);
		//System.out.println(list);

		return ResponseEntity.ok(NewsUtility.newsToJson(list));
	}



	


	

}
