package com.bafoly.ex.article;

import com.bafoly.ex.error.NothingFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public Article getArticleById(long id) {
    return this.articleRepository.findById(id).orElseThrow(()-> new NothingFoundException("Article Not Found"));
    }


    public Article save(Article article) {
        return this.articleRepository.save(article);
    }
}
