package henesys.henesysbackend.article.controller;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.article.service.ArticleService;
import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static henesys.henesysbackend.article.domain.dto.ArticleDto.*;
import static henesys.henesysbackend.comment.domain.dto.CommentDto.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private Member memberA;
    private Member memberB;
    private Article articleA;
    private Article articleB;
    private Article articleC;
    private Comment commentA;
    private List<Article> articleList = new ArrayList<>();

    @BeforeEach
    public void before() {
        memberA = new Member("memberA", "passwordA", RoleType.USER, "nicknameA", "emailA");
        memberB = new Member("memberB", "passwordB", RoleType.USER, "nicknameB", "emailB");

        articleA = new Article(memberA, "articleA title", "articleA content", "titleImgUrlA");
        articleB = new Article(memberB, "articleB title", "articleB content", "titleImgUrlB");
        articleC = new Article(memberA, "articleC title", "articleC content", "titleImgUrlC");

        articleList.add(articleA);
        articleList.add(articleB);
        articleList.add(articleC);

        commentA = new Comment(memberA, articleA, "commentA");
        memberA.getComments().add(commentA);
        articleA.getComments().add(commentA);
    }

    @Test
    public void getArticleDtos() throws Exception {
        //given
        List<ResponseArticleDto> articleDtos = articleList.stream()
                .map(article -> ResponseArticleDto.builder()
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

        //when
        when(articleService.createArticleDtos()).thenReturn(articleDtos);

        //then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].title").exists())
                .andExpect(jsonPath("$.data[0].thumbnailImg").exists())
                .andExpect(jsonPath("$.data[0].author").exists())
                .andExpect(jsonPath("$.data[0].author").value("memberA"))
                .andExpect(jsonPath("$.data[0].content").exists())
                .andExpect(jsonPath("$.data[0].commentCount").exists())
                .andExpect(jsonPath("$.data[0].viewCount").exists())
                .andExpect(jsonPath("$.data[0].likeCount").exists())
                .andExpect(jsonPath("$.data[0].modifiedAt").exists());
    }

    @Test
    public void getNewTop3ArticleDtos() throws Exception {
        //given
        List<Article> top3List = new ArrayList<>();

        top3List.add(new Article(memberB, "articleE title", "articleE content", "titleImgUrlE"));
        top3List.add(new Article(memberA, "articleD title", "articleD content", "titleImgUrlD"));
        top3List.add(articleC);

        List<ResponseArticleDto> articleDtos = top3List.stream()
                .map(article -> ResponseArticleDto.builder()
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

        //when
        when(articleService.createTop3ByCreatedAtDescDtos()).thenReturn(articleDtos);

        //then
        mockMvc.perform(get("/articles/latest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].title").exists())
                .andExpect(jsonPath("$.data[0].thumbnailImg").exists())
                .andExpect(jsonPath("$.data[0].author").exists())
                .andExpect(jsonPath("$.data[0].author").value("memberB"))
                .andExpect(jsonPath("$.data[0].content").exists())
                .andExpect(jsonPath("$.data[0].commentCount").exists())
                .andExpect(jsonPath("$.data[0].viewCount").exists())
                .andExpect(jsonPath("$.data[0].likeCount").exists())
                .andExpect(jsonPath("$.data[0].modifiedAt").exists());
    }

    @Test
    public void createMostViewTop4ArticleDtos() throws Exception {
        //given
        List<Article> top4List = new ArrayList<>();

        top4List.add(new Article(memberB, "articleE title", "articleE content", "titleImgUrlE"));
        top4List.add(new Article(memberA, "articleD title", "articleD content", "titleImgUrlD"));
        top4List.add(articleC);
        top4List.add(articleB);

        List<ResponseArticleDto> articleDtos = top4List.stream()
                .map(article -> ResponseArticleDto.builder()
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

        //when
        when(articleService.createTop4ByMostViewDescDtos()).thenReturn(articleDtos);

        //then
        mockMvc.perform(get("/articles/most-view"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(4)))
                .andExpect(jsonPath("$.data[0].title").exists())
                .andExpect(jsonPath("$.data[0].thumbnailImg").exists())
                .andExpect(jsonPath("$.data[0].author").exists())
                .andExpect(jsonPath("$.data[0].author").value("memberB"))
                .andExpect(jsonPath("$.data[1].author").value("memberA"))
                .andExpect(jsonPath("$.data[2].author").value("memberA"))
                .andExpect(jsonPath("$.data[3].author").value("memberB"))
                .andExpect(jsonPath("$.data[0].content").exists())
                .andExpect(jsonPath("$.data[0].commentCount").exists())
                .andExpect(jsonPath("$.data[0].viewCount").exists())
                .andExpect(jsonPath("$.data[0].likeCount").exists())
                .andExpect(jsonPath("$.data[0].modifiedAt").exists());
    }

    @Test
    public void getArticleById() throws Exception {
        //given
        Long commentAId = 1L;
        Long articleAId = 2L;

        ResponseCommentDto commentDto = ResponseCommentDto.builder()
                .id(commentAId)
                .author(commentA.getMember().getNickname())
                .content(commentA.getContent())
                .modifiedAt(commentA.getModifiedAt())
                .build();

        List<ResponseCommentDto> comments = new ArrayList<>();
        comments.add(commentDto);

        ResponseArticleDetailDto findArticle = ResponseArticleDetailDto.builder()
                .id(articleAId)
                .title(articleA.getTitle())
                .author(articleA.getMember().getName())
                .content(articleA.getContent())
                .commentCount(articleA.getCommentCount())
                .viewCount(articleA.getViewCount())
                .likeCount(articleA.getLikeCount())
                .modifiedAt(articleA.getModifiedAt())
                .comments(comments)
                .build();

        //when
        when(articleService.createOneArticleDto(articleAId)).thenReturn(findArticle);

        //then
        mockMvc.perform(get("/articles/" + articleAId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.title").exists())
                .andExpect(jsonPath("$.data.content").exists())
                .andExpect(jsonPath("$.data.commentCount").exists())
                .andExpect(jsonPath("$.data.viewCount").exists())
                .andExpect(jsonPath("$.data.likeCount").exists())
                .andExpect(jsonPath("$.data.modifiedAt").exists())
                .andExpect(jsonPath("$.data.comments", hasSize(1)))
                .andExpect(jsonPath("$.data.comments[0].id").exists())
                .andExpect(jsonPath("$.data.comments[0].author").exists())
                .andExpect(jsonPath("$.data.comments[0].content").exists())
                .andExpect(jsonPath("$.data.comments[0].modifiedAt").exists());

    }

}