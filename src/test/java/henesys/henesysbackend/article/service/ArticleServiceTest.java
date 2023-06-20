package henesys.henesysbackend.article.service;

import henesys.henesysbackend.article.domain.dto.ArticleDto;
import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import henesys.henesysbackend.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static henesys.henesysbackend.article.domain.dto.ArticleDto.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
public class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Autowired
    MemberRepository memberRepository;

    private Member memberA;
    private Member memberB;
    private Article articleA;
    private Article articleB;
    private Article articleC;

    @BeforeEach
    public void before() {
        memberA = new Member("memberA", "password", RoleType.USER, "nicknameA", "emailA");
        memberB = new Member("memberB", "password", RoleType.USER, "nicknameB", "emailB");
        memberRepository.save(memberA);
        memberRepository.save(memberB);


        articleA = new Article(memberA, "articleA title", "articleA content", "titleImgUrlA");
        articleB = new Article(memberB, "articleB title", "articleB content", "titleImgUrlB");
        articleC = new Article(memberA, "articleC title", "articleC content", "titleImgUrlC");
    }

    @Test
    public void addArticleTest() throws Exception {
        //given
        Article targetArticle = articleA;

        //when
        Long addId = articleService.addArticle(targetArticle);

        //then
        assertThat(addId).isEqualTo(targetArticle.getId());
        assertThat(targetArticle.getMember().getArticles().get(0)).isEqualTo(targetArticle);
        log.info("targetArticleId={}", targetArticle.getId());
        log.info("addId={}", addId);
    }

    @Test
    public void createArticleDtosTest() throws Exception {
        //given
        articleService.addArticle(articleA);
        articleService.addArticle(articleB);
        articleService.addArticle(articleC);

        //when
        List<ResponseArticleDto> findDtos = articleService.createArticleDtos();

        //then
        assertThat(findDtos.size()).isEqualTo(3);
        assertThat(findDtos.get(0).getId()).isEqualTo(articleA.getId());
        assertThat(findDtos.get(0).getTitle()).isEqualTo(articleA.getTitle());
        assertThat(findDtos.get(0).getContent()).isEqualTo(articleA.getContent());
        assertThat(findDtos.get(0).getAuthor()).isEqualTo(articleA.getMember().getName());
        assertThat(findDtos.get(0).getModifiedAt()).isEqualTo(articleA.getModifiedAt());
        assertThat(findDtos.get(0).getThumbnailImg()).isEqualTo(articleA.getTitleImg());
        assertThat(findDtos.get(0).getLikeCount()).isEqualTo(articleA.getLikeCount());
        assertThat(findDtos.get(0).getViewCount()).isEqualTo(articleA.getViewCount());
    }

    @Test
    public void createTop3ByCreatedAtDescDtosTest() throws Exception {
        //given
        articleService.addArticle(articleA);
        articleService.addArticle(articleB);
        articleService.addArticle(articleC);
        articleService.addArticle(new Article(memberB, "articleD title", "articleD content", "titleImgUrlD"));
        articleService.addArticle(new Article(memberA, "articleE title", "articleE content", "titleImgUrlE"));

        //when
        List<ResponseArticleDto> findDtos = articleService.createTop3ByCreatedAtDescDtos();

        //then
        assertThat(findDtos.size()).isEqualTo(3);
        assertThat(findDtos.get(0).getModifiedAt()).isAfter(findDtos.get(1).getModifiedAt());
        assertThat(findDtos.get(1).getModifiedAt()).isAfter(findDtos.get(2).getModifiedAt());
    }

    @Test
    public void createTop4ByMostViewDescDtosTest() throws Exception {
        //given
        articleService.addArticle(articleA);
        articleService.addArticle(articleB);
        articleService.addArticle(articleC);
        articleService.addArticle(new Article(memberB, "articleD title", "articleD content", "titleImgUrlD"));
        articleService.addArticle(new Article(memberA, "articleE title", "articleE content", "titleImgUrlE"));

        //when
        List<ResponseArticleDto> findDtos = articleService.createTop4ByMostViewDescDtos();

        //then
        assertThat(findDtos.size()).isEqualTo(4);
    }
    
    @Test
    public void createOneArticleDtoTest() throws Exception {
        //given
        articleService.addArticle(articleA);
        articleService.addArticle(articleB);
        Comment commentA = new Comment(memberB, articleA, "contentA");
        Comment commentB = new Comment(memberA, articleA, "contentB");
        articleA.getComments().add(commentA);
        articleA.getComments().add(commentB);
        Long ArticleId = articleA.getId();

        //when
        ResponseArticleDetailDto findDto = articleService.createOneArticleDto(ArticleId);
        
        //then
        assertThat(findDto.getId()).isEqualTo(articleA.getId());
        assertThat(findDto.getComments().size()).isEqualTo(2);
    }

    @Test
    public void createOneArticleDtoFailTest() throws Exception {
        //given
        articleService.addArticle(articleA);
        Long ArticleId = articleB.getId();

        //when, then
        assertThatThrownBy(() -> articleService.createOneArticleDto(ArticleId))
                .isInstanceOf(RuntimeException.class);
    }
    
//
//    @Test
//    public void deleteArticleDtosTest() throws Exception {
//        //given
//        Long articleAId = articleService.addArticle(articleA);
//        Long articleBId = articleService.addArticle(articleB);
//        Long articleCId = articleService.addArticle(articleC);
//
//        //when
//        List<ArticleDto> findDtos = articleService.deleteArticleDtos();
//
//        //then
//        assertThat(findDtos.size()).isEqualTo(3);
//        assertThat(findDtos.get(0).getTitle()).isEqualTo(articleA.getTitle());
//        assertThat(findDtos.get(0).getContent()).isEqualTo(articleA.getContent());
//        assertThat(findDtos.get(0).getAuthor()).isEqualTo(articleA.getMember().getName());
//        assertThat(findDtos.get(0).getModifiedAt()).isEqualTo(articleA.getModifiedAt());
//        assertThat(findDtos.get(0).getThumbnailImg()).isEqualTo(articleA.getTitleImg());
//        assertThat(findDtos.get(0).getLikeCount()).isEqualTo(articleA.getLikeCount());
//        assertThat(findDtos.get(0).getViewCount()).isEqualTo(articleA.getViewCount());
//    }
}
