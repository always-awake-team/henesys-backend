package henesys.henesysbackend.article.domain.entity;

import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class ArticleTest {

    private Member memberA;
    private Article articleA;

    @BeforeEach
    public void before() {
        memberA = new Member("memberA", "password", RoleType.USER, "nicknameA", "emailA");

        articleA = new Article(memberA, "articleA title", "articleA content", "titleImgUrlA");
    }

    @Test
    public void addArticleToMember() throws Exception {
        //when
        articleA.addArticleToMember();

        //then
        assertThat(articleA.getMember()).isEqualTo(memberA);
        assertThat(memberA.getArticles().get(0)).isEqualTo(articleA);
    }

    @Test
    public void deleteArticleToMember() throws Exception {
        //given
        articleA.addArticleToMember();

        log.info("before member.articles.size()={}", memberA.getArticles().size());
        log.info("member.articles.title={}", memberA.getArticles().get(0).getTitle());

        //when
        articleA.deleteArticleToMember();
        log.info("member.articles.size() after deleteArticleToMember={}", memberA.getArticles().size());
        //then
        assertThat(memberA.getArticles().size()).isEqualTo(0);
    }

}