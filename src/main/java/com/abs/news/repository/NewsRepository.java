package com.abs.news.repository;

import com.abs.news.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {

    NewsEntity findByNewsId(Integer newsId);

    List<NewsEntity> findAllByCategory(String category);

    List<NewsEntity> findAllByCategoryOrderByPostedDTimeDesc(String category);

    List<NewsEntity> findAllOrderByPostedDTimeDesc();
}
