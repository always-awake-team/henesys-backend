package henesys.henesysbackend.article.controller;

import henesys.henesysbackend.article.domain.dto.ArticleDto;
import henesys.henesysbackend.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    public ResponseResult<List<ArticleDto.ResponseAllArticleList>> getArticles() {
        List<ArticleDto.ResponseAllArticleList> articleDtos = articleService.createArticleDtos();
        return new ResponseResult<>(articleDtos);
    }

}
