package sg.nus.edu.iss.vttp5a_ssf_2022_assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.model.Article;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.service.NewsService;
import sg.nus.edu.iss.vttp5a_ssf_2022_assessment.util.Utility;

@Controller
@RequestMapping("/news")
public class NewsController {
    
    @Autowired
    NewsService newsService;

    @GetMapping
    public ModelAndView getArticles(){
        ModelAndView mav = new ModelAndView();
        List<Article> articleList = newsService.getArticles();
        mav.addObject("articles", articleList);
        mav.setViewName("newsarticlelist");
        return mav;
    }

    @PostMapping()
    public ModelAndView saveArticle(@RequestBody MultiValueMap<String, String> params){
        ModelAndView mav = new ModelAndView();
        for(String key:params.keySet()){
            newsService.saveArticles(Utility.REDISKEY, key);
        }
        mav.setViewName("redirect:/news");
        return mav;
    }
}
