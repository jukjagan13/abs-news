package com.abs.news.service;

import com.abs.news.entity.NewsEntity;
import com.abs.news.exception.SystemException;
import com.abs.news.model.NewsData;
import com.abs.news.repository.NewsRepository;
import com.abs.news.utilities.CommonConstants;
import com.abs.news.utilities.CommonUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    /**
     * Pull top 'n' numbers of News desc order by news posted time
     * @return
     */
    public List<NewsData> getAllNews(Integer limit) {
        List<NewsEntity> allNewsEntity = newsRepository.findAllOrderByPostedDTimeDesc().stream().limit(limit).collect(Collectors.toList());
        return allNewsEntity.stream().map(this::constructRes).collect(Collectors.toList());
    }

    /**
     * Pull news details by news id
     * @return
     */
    public NewsData getNewsById(Integer newsId) {
        NewsEntity newsEntity = newsRepository.findByNewsId(newsId);
        return constructRes(newsEntity);
    }

    /**
     * Pull top 'n' numbers of News desc order by category
     * @return
     */
    public List<NewsData> getAllNewsByCategory(String category, Integer limit) {
        List<NewsEntity> allNewsEntity = newsRepository.findAllByCategoryOrderByPostedDTimeDesc(category).stream().limit(limit).collect(Collectors.toList());
        return allNewsEntity.stream().map(this::constructRes).collect(Collectors.toList());
    }

    /**
     * To Construct Response From Entity
     *
     * @param newsEntity
     * @return
     */
    private NewsData constructRes(NewsEntity newsEntity) {
        try {
            if(newsEntity != null) {
                NewsData response = new NewsData();
//            response.setNewsId(newsEntity.getNewsId());
//            response.setTitle(newsEntity.getTitle());
//            response.setCategory(newsEntity.getCategory());
//            response.setNewsId(newsEntity.getContent());
//            response.setPostedTime(newsEntity.getPostedTime());
                response = new ObjectMapper().convertValue(newsEntity, NewsData.class);
                String contentStr = newsEntity.getContent();
                List<String> content = new ArrayList<>();
                content = new ObjectMapper().convertValue(contentStr, ArrayList.class);
                response.setContent(content);

                return response;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("---- Error In News Service - constructRes Method ----", e);
            throw new SystemException("Unknown Error Occurred");
        }
    }
}
