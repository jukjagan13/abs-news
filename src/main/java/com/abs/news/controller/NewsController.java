package com.abs.news.controller;

import com.abs.news.model.NewsData;
import com.abs.news.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController("news")
@RestController
@Slf4j
public class NewsController {
    @Autowired
    private NewsService newsService;


    /**
     * Get All News
     *
     * @return
     */
    @GetMapping("all")
    public List<NewsData> getAllNews(@RequestParam(required = false) Integer limit) {
        if(limit==null)
            limit=10;
        return newsService.getAllNews(limit);
    }

    /**
     * Get News By ID
     *
     * @param newsId
     * @return
     */
    @GetMapping("by-id/{newsId}")
    public NewsData getNewsById(@PathVariable Integer newsId) {
        return newsService.getNewsById(newsId);
    }

    /**
     * Get All News By Category
     *
     * @param category
     * @return
     */
    @GetMapping("by-category/{category}")
    public List<NewsData> getNewsById(@PathVariable String category, @RequestParam(required = false) Integer limit) {
        if(limit==null)
            limit=10;
        return newsService.getAllNewsByCategory(category, limit);
    }

//    /**
//     * Get News for the day (dd/mm/yyyy)
//     *
//     * @param date
//     * @return
//     */
//    @GetMapping("by-date/{date}")
//    public List<NewsData> getNewsByDate(@PathVariable String date) {
//        return newsService.getSchedulesByDate(date);
//    }
}
