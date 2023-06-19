package henesys.henesysbackend.article.controller;

import henesys.henesysbackend.article.domain.dto.ArticleDto;
import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.article.service.ArticleService;
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
    }

    @Test
    public void getArticleDtos() throws Exception {
        //given
        List<ArticleDto.ResponseArticleDto> articleDtos = articleList.stream()
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

        List<ArticleDto.ResponseArticleDto> articleDtos = top3List.stream()
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

        //when
        when(articleService.createTop3ByCreatedAtDescDtos()).thenReturn(articleDtos);

        //then
        mockMvc.perform(get("/articles/new"))
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

}