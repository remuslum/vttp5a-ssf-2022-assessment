package sg.nus.edu.iss.vttp5a_ssf_2022_assessment.model;

import java.time.LocalDate;
import java.util.List;

public class Article {
    private Integer id;
    private LocalDate publishedOn;
    private String title;
    private String url;
    private String imageurl;
    private String body;
    private String tags;
    private List<String> categories;
    
    public Article() {
    }

    public Article(String body, List<String> categories, Integer id, String imageurl, LocalDate publishedOn, String tags, String title, String url) {
        this.body = body;
        this.categories = categories;
        this.id = id;
        this.imageurl = imageurl;
        this.publishedOn = publishedOn;
        this.tags = tags;
        this.title = title;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    
}
