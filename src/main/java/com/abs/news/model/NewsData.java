package com.abs.news.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class NewsData {
    private Integer newsId;
    private String title;
    private String category;
    private List<String> thumbnails;
    private List<String> content;
    private Integer noOfViews;
    private Timestamp postedTime;
}
