package sg.nus.edu.iss.vttp5a_ssf_2022_assessment.controller.restcontroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.service.NewsService;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.util.Utility;

@RestController
@RequestMapping("/articles")
public class NewsRESTController {
    @Autowired
    NewsService newsService;
    
    @GetMapping("/{id}")
    public ResponseEntity<String> viewArticle(@PathVariable("id") String id){
        JsonObject jsonObject = Json.createObjectBuilder().add("error","Cannot find news article " + id).build();
        Optional<String> article = Optional.ofNullable(newsService.getArticleString(Utility.REDISKEY, id));
        
        return article.map((value) -> ResponseEntity.status(HttpStatusCode.valueOf(200)).body(value))
        .orElseGet(() -> ResponseEntity.status(404).body(jsonObject.toString()));
    }
}
