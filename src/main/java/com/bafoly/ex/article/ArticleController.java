package com.bafoly.ex.article;

import com.bafoly.ex.error.AppError;
import com.bafoly.ex.error.NothingFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable long id){
    return this.articleService.getArticleById(id);
    }
//
//    @ExceptionHandler(NothingFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public AppError handleNothingFoundException(NothingFoundException exception, HttpServletRequest request){
//        AppError error = new AppError(404, exception.getMessage(), request.getServletPath(), "Article Not Found");
//        return error;
//    }

    @PostMapping("/article")
    public Article createArticle(@Valid @RequestBody Article article){
        return this.articleService.save(article);
 }
}
