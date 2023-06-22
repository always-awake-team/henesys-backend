package henesys.henesysbackend.article.service;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.article.repository.ArticleRepository;
import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static henesys.henesysbackend.article.domain.dto.ArticleDto.*;
import static henesys.henesysbackend.comment.domain.dto.CommentDto.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CommentService commentService;

    public Long addArticle(Article article) {
        Article savedArticle = articleRepository.save(article);
        savedArticle.addArticleToMember();

        return savedArticle.getId();
    }

    public List<ResponseArticleDto> createArticleDtos() {
        return articleRepository.findAll().stream()
                .map(ArticleService::createArticleDto)
                .toList();
    }

    public List<ResponseArticleDto> createTop3ByCreatedAtDescDtos() {
        return articleRepository.findTop3ByOrderByCreatedAtDesc().stream()
                .map(ArticleService::createArticleDto)
                .toList();
    }

    public List<ResponseArticleDto> createTop4ByMostViewDescDtos() {
        return articleRepository.findTop4ByOrderByViewCountDescCreatedAtDesc().stream()
                .map(ArticleService::createArticleDto)
                .toList();
    }

    private static ResponseArticleDto createArticleDto(Article article) {
        return ResponseArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .thumbnailImg(article.getTitleImg())
                .author(article.getMember().getNickname())
                .content(article.getContent())
                .commentCount(article.getCommentCount())
                .viewCount(article.getViewCount())
                .likeCount(article.getLikeCount())
                .modifiedAt(article.getModifiedAt())
                .build();
    }

    public ResponseArticleDetailDto createOneArticleDto(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("해당 게시물은 없는 게시물입니다."));
        return createResponseArticleDetailDto(article);
    }

    private ResponseArticleDetailDto createResponseArticleDetailDto(Article article) {
        return ResponseArticleDetailDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .author(article.getMember().getNickname())
                .content(article.getContent())
                .commentCount(article.getCommentCount())
                .viewCount(article.getViewCount())
                .likeCount(article.getLikeCount())
                .modifiedAt(article.getModifiedAt())
                .comments(commentService.createResponseCommentDtos(article.getId()))
                .build();
    }
}
