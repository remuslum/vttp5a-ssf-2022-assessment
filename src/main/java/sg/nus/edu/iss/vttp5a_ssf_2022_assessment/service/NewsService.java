package sg.nus.edu.iss.vttp5a_ssf_2022_assessment.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.model.Article;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.model.LocalDateConverter;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.repo.MapRepo;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.util.Utility;

@Service
public class NewsService {
    @Autowired
    MapRepo mapRepo;

    public List<Article> getArticles(){
        List<Article> articleList = new ArrayList<>();
        String articleData = getCyrptoData().getBody();
        JsonReader jsonReader = Json.createReader(new StringReader(articleData));
        JsonArray jsonArrayData = jsonReader.readObject().getJsonArray("Data");

        for(int i = 0; i < jsonArrayData.size(); i++){
            JsonObject jsonObject = jsonArrayData.getJsonObject(i);
            articleList.add(convertJSONToArticle(jsonObject));
        }
        return articleList;
    }

    public void saveArticles(String redisKey, String articleID){
        System.out.println(articleID);
        List<Article> articleList = getArticles();
        List<Article> articleFiltered = articleList.stream().filter(x -> Objects.equals(x.getId(), Integer.valueOf(articleID))).collect(Collectors.toList());
        for(Article article:articleFiltered){
            mapRepo.create(redisKey, articleID, convertArticleToJSONString(article));
        }
    }

    public String getArticleString(String redisKey, String articleID){
        return mapRepo.get(redisKey, articleID);
    }

    private Article convertJSONToArticle(JsonObject jsonObject){
        LocalDateConverter localDateConverter = new LocalDateConverter();
        JsonArray jsonArrayCategory = jsonObject.getJsonArray("CATEGORY_DATA");
        List<String> categories = new ArrayList<>();
        for(int i = 0; i < jsonArrayCategory.size(); i++){
            JsonObject object = jsonArrayCategory.getJsonObject(i);
            categories.add(object.getString("CATEGORY"));
        }

        return new Article(jsonObject.getString("BODY"), categories, jsonObject.getInt("ID"), 
        jsonObject.getString("IMAGE_URL"), 
        localDateConverter.convert(jsonObject.getJsonNumber("PUBLISHED_ON").longValue()), 
        jsonObject.getString("KEYWORDS"), jsonObject.getString("TITLE"), 
        jsonObject.getString("URL"));
    }
    
    private ResponseEntity<String> getCyrptoData(){
        RequestEntity<Void> request = RequestEntity.get(Utility.NEWSARTICLE)
        .header("Authorization", "Bearer " + Utility.API_KEY)
        .accept(MediaType.APPLICATION_JSON).build();

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(request, String.class);
    }

    private String convertArticleToJSONString(Article article){
        LocalDateConverter localDateConverter = new LocalDateConverter();
        StringBuilder sb = new StringBuilder();
        for(String category:article.getCategories()){
            sb.append(category);
            sb.append(",");
        }

        JsonObject jsonObject = Json.createObjectBuilder().add("ID", article.getId()).add("PUBLISHED_ON", Long.parseLong(localDateConverter.convertStringDateToLong(article.getPublishedOn().toString())))
        .add("TITLE", article.getTitle()).add("URL", article.getUrl()).add("IMAGE_URL", article.getImageurl())
        .add("BODY", article.getBody()).add("KEYWORDS", article.getTags()).add("CATEGORIES",sb.toString()).build();
        return jsonObject.toString();
    }
}
