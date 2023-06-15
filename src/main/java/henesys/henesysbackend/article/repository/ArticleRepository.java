package henesys.henesysbackend.article.repository;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByMember(Member member);

}
