package henesys.henesysbackend.comment.controller;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.comment.service.CommentService;
import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;
    private Member memberA;
    private Member memberB;
    private Article articleA;
    private Comment commentA;
    private Comment commentB;
    private List<Comment> commentList = new ArrayList<>();

    @BeforeEach
    public void before() {
        memberA = new Member("memberA", "passwordA", RoleType.USER, "nicknameA", "emailA");
        memberB = new Member("memberB", "passwordB", RoleType.USER, "nicknameB", "emailB");

        articleA = new Article(memberA, "articleA title", "articleA content", "titleImgUrlA");
        articleA.addArticleToMember();

        commentA = new Comment(memberA, articleA, "commentA");
        commentB = new Comment(memberB, articleA, "commentB");
        commentA.addCommentToMemberAndArticle();
        commentB.addCommentToMemberAndArticle();
        commentList.add(commentA);
        commentList.add(commentB);
    }
}
