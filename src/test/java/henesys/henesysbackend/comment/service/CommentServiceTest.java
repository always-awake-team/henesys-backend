package henesys.henesysbackend.comment.service;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.article.repository.ArticleRepository;
import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.comment.repository.CommentRepository;
import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import henesys.henesysbackend.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static henesys.henesysbackend.comment.domain.dto.CommentDto.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentRepository commentRepository;

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
    public void createCommentDtosTest() throws Exception {
        // given, when
        List<ResponseCommentDto> commentDtos = commentService.createResponseCommentDtos(articleA.getId());

        //then
        assertThat(commentDtos.size()).isEqualTo(2);
        assertThat(commentDtos.get(0).getAuthor()).isEqualTo(memberA.getNickname());
    }

}
