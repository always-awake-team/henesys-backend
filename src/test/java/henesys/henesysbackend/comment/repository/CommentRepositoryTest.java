package henesys.henesysbackend.comment.repository;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.article.repository.ArticleRepository;
import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import henesys.henesysbackend.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArticleRepository articleRepository;
    private Member memberA;
    private Member memberB;
    private Article articleA;
    private Comment commentA;
    private Comment commentB;

    @BeforeEach
    public void before() {
        memberA = new Member("memberA", "password", RoleType.USER, "nicknameA", "emailA");
        memberB = new Member("memberB", "password", RoleType.USER, "nicknameB", "emailB");
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        articleA = new Article(memberA, "articleA title", "articleA content", "titleImgUrlA");
        articleRepository.save(articleA);

        commentA = new Comment(memberA, articleA, "commentA");
        commentB = new Comment(memberB, articleA, "commentB");
        commentRepository.save(commentA);
        commentRepository.save(commentB);
        commentA.addCommentToMemberAndArticle();
        commentB.addCommentToMemberAndArticle();
    }

    @Test
    public void findCommentByArticleIdOrderByCreatedAtDescTest() throws Exception {
        //given, when
        List<Comment> findComments = commentRepository.findCommentByArticleIdOrderByCreatedAt(articleA.getId());

        //then
        assertThat(articleA.getComments().size()).isEqualTo(2);
        assertThat(findComments.size()).isEqualTo(2);
        assertThat(findComments.get(0)).isEqualTo(commentA);
        assertThat(findComments.get(1)).isEqualTo(commentB);
    }
}
