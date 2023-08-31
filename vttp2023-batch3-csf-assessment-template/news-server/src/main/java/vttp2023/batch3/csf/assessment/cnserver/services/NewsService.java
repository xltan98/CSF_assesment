package vttp2023.batch3.csf.assessment.cnserver.services;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.repositories.ImageRepository;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
@Service
public class NewsService {
	@Autowired
	ImageRepository iRepo;

	@Autowired
	NewsRepository nRepo;
	
	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	public String postNews(MultipartFile uploadFile,String payload) {
		
		String id=iRepo.saveImage(uploadFile);

		News n=nRepo.saveNews(payload, id);



		return n.getId();
	}
	 
	// TODO: Task 2
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of tags and their associated count
	public List<TagCount> getTags(Integer time) {
		

		return nRepo.showTags(time);
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(Integer time,String tag) {
		
		return nRepo.showNews(time,tag);
	}
	
}
