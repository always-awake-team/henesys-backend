package henesys.henesysbackend.article.service;

import henesys.henesysbackend.article.domain.dto.ArticleDto;
import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Long addArticle(Article article) {
        Article savedArticle = articleRepository.save(article);
        savedArticle.addArticleToMember();

        return savedArticle.getId();
    }

    public List<ArticleDto.ResponseArticleDto> createArticleDtos() {
        return articleRepository.findAll().stream()
                .map(article -> ArticleDto.ResponseArticleDto.builder()
                        .title(article.getTitle())
                        .thumbnailImg(article.getTitleImg())
                        .author(article.getMember().getName())
                        .content(article.getContent())
                        .commentCount(article.getCommentCount())
                        .viewCount(article.getViewCount())
                        .likeCount(article.getLikeCount())
                        .modifiedAt(article.getModifiedAt())
                        .build())
                .toList();
    }

    public List<ArticleDto.ResponseArticleDto> createTop3ByCreatedAtDescDtos() {
        return articleRepository.findTop3ByOrderByCreatedAtDesc().stream()
                .map(article -> ArticleDto.ResponseArticleDto.builder()
                        .title(article.getTitle())
                        .thumbnailImg(article.getTitleImg())
                        .author(article.getMember().getName())
                        .content(article.getContent())
                        .commentCount(article.getCommentCount())
                        .viewCount(article.getViewCount())
                        .likeCount(article.getLikeCount())
                        .modifiedAt(article.getModifiedAt())
                        .build())
                .toList();
    }

}
