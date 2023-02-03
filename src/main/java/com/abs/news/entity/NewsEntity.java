package com.abs.news.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "abs_news")
@Data
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsId;
//    @Lob
    private String title;
    private String category;
    private String thumbnails;
//    @Lob
    private String content;
    private Timestamp postedTime;
}
