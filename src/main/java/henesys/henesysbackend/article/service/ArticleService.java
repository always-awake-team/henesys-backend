package henesys.henesysbackend.article.service;

import henesys.henesysbackend.article.domain.dto.ArticleDto;
import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ArticleDto> createArticleDtos() {
        return articleRepository.findAll().stream()
                .map(article -> new ArticleDto(article.getTitle(), article.getTitleImg(), article.getMember().getName(), article.getContent(), article.getCommentCount(), article.getViewCount(), article.getLikeCount(), article.getModifiedAt()))
                .collect(Collectors.toList());
    }
}
