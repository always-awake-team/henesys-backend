package henesys.henesysbackend.article.controller;

import henesys.henesysbackend.article.domain.dto.ArticleDto;
import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.article.service.ArticleService;
import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
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

    @Test
    public void getArticleDtos() throws Exception {
        //given
        Member memberA = new Member("memberA", "passwordA", RoleType.USER, "nicknameA", "emailA");
        Member memberB = new Member("memberB", "passwordB", RoleType.USER, "nicknameB", "emailB");

        Article articleA = new Article(memberA, "articleA title", "articleA content", "titleImgUrlA");
        Article articleB = new Article(memberB, "articleB title", "articleB content", "titleImgUrlB");
        Article articleC = new Article(memberA, "articleC title", "articleC content", "titleImgUrlC");

        List<Article> articles = new ArrayList<>();
        articles.add(articleA);
        articles.add(articleB);
        articles.add(articleC);

        List<ArticleDto.ResponseAllArticleList> articleDtos = articles.stream()
                .map(article -> ArticleDto.ResponseAllArticleList.builder()
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

}