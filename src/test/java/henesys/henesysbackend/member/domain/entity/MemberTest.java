package henesys.henesysbackend.member.domain.entity;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class MemberTest {

    private Member memberA;
    private Member memberB;
    private Article articleA;
    private Article articleB;
    private Comment commentA;
    private Comment commentB;

    @BeforeEach
    public void before() {
        memberA = new Member("memberA", "passwordA", RoleType.USER, "nicknameA", "emailA");
        memberB = new Member("memberB", "passwordB", RoleType.USER, "nicknameB", "emailB");
        articleA = new Article(memberA, "titleA", "contentA", "urlA");
        articleB = new Article(memberB, "titleB", "contentB", "urlB");
        commentA = new Comment(memberA, articleA, "contentA");
        commentB = new Comment(memberB, articleB, "contentB");
    }

    @Test
    public void changeArticleTest() throws Exception {
        //when
        memberA.changeArticle(articleA);
        log.info("memberA.getArticles.getTitle={}", memberA.getArticles().get(0).getTitle());

        //then
        assertThat(memberA.getArticles().size()).isEqualTo(1);
    }

    @Test
    public void deleteArticleTest() throws Exception {
        //given
        memberA.changeArticle(articleA);
        log.info("memberA.getArticles.getTitle={}", memberA.getArticles().get(0).getTitle());
        log.info("memberA.getArticles.beforeSize={}", memberA.getArticles().size());

        //when
        memberA.deleteArticle(articleA);
        log.info("memberA.getArticles.afterSize={}", memberA.getArticles().size());

        //then
        assertThat(memberA.getArticles().size()).isEqualTo(0);
    }

    @Test
    public void changeCommentTest() throws Exception {
        //when
        memberA.changeComment(commentA);
        memberA.changeComment(commentB);
        log.debug("memberA.getComments().getTitle={}", memberA.getComments().get(0).getContent());
        log.debug("memberA.getComments().getTitle={}", memberA.getComments().get(1).getContent());

        //then
        assertThat(memberA.getComments().size()).isEqualTo(2);
    }

    @Test
    public void deleteCommentTest() throws Exception {
        //given
        memberA.changeComment(commentA);
        log.info("memberA.getComments().getTitle={}", memberA.getComments().get(0).getContent());
        log.info("memberA.getComments().beforeSize={}", memberA.getComments().size());

        //when
        memberA.deleteComment(commentA);
        log.info("memberA.getComment.size={}", memberA.getComments().size());
        //then
        assertThat(memberA.getComments().size()).isEqualTo(0);
    }
}
