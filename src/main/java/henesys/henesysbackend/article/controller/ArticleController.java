package henesys.henesysbackend.article.controller;

import henesys.henesysbackend.article.domain.dto.ArticleDto;
import henesys.henesysbackend.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static henesys.henesysbackend.article.domain.dto.ArticleDto.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    public ResponseResult<List<ResponseArticleDto>> getArticles() {
        List<ResponseArticleDto> articleDtos = articleService.createArticleDtos();
        return new ResponseResult<>(articleDtos);
    }


    @GetMapping("/articles/latest")
    public ResponseResult<List<ArticleDto.ResponseArticleDto>> getTop3NewCreatedArticles() {
        List<ArticleDto.ResponseArticleDto> articleDtos = articleService.createTop3ByCreatedAtDescDtos();
        return new ResponseResult<>(articleDtos);
    }

    @GetMapping("/articles/most-view")
    public ResponseResult<List<ArticleDto.ResponseArticleDto>> getTop4MostViewArticles() {
        List<ArticleDto.ResponseArticleDto> articleDtos = articleService.createTop4ByMostViewDescDtos();
        return new ResponseResult<>(articleDtos);
    }

    @GetMapping("/articles/{id}")
    public ResponseResult<ResponseArticleDetailDto> getDetailArticle(@PathVariable Long id) {
        ResponseArticleDetailDto articleDto = articleService.createOneArticleDto(id);
        return new ResponseResult<>(articleDto);
    }

}
