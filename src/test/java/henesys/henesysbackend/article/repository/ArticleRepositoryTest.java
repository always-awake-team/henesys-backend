package henesys.henesysbackend.article.repository;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import henesys.henesysbackend.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
public class ArticleRepositoryTest {


    @Autowired
    ArticleRepository articleRepository;

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
    public void saveTest() throws Exception {
        //when
        Article savedArticle = articleRepository.save(articleA);

        log.debug("savedArticle={}", savedArticle);

        //then
        assertThat(savedArticle.getId()).isEqualTo(articleA.getId());
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        articleRepository.save(articleA);
        articleRepository.save(articleB);

        //when
        List<Article> findArticles = articleRepository.findAll();

        //then
        assertThat(findArticles.size()).isEqualTo(2);
    }

    @Test
    public void findByIdTest() throws Exception {
        //given
        articleRepository.save(articleA);
        Long findId = articleA.getId();
        //when
        Article findArticle = articleRepository.findById(findId).get();

        //then
        assertThat(findArticle.getId()).isEqualTo(findId);
    }

    @Test
    public void findAllByMemberTest() throws Exception {
        //given
        Member memberC = new Member("memberC", "password", RoleType.USER, "nicknameC", "emailC");
        memberRepository.save(memberC);

        articleRepository.save(articleA);
        articleRepository.save(articleC);

        //when
        List<Article> findArticles = articleRepository.findAllByMember(memberA);
        List<Article> notExistsArticles = articleRepository.findAllByMember(memberC);

        //then
        assertThat(findArticles.get(0).getMember()).isEqualTo(memberA);
        assertThat(findArticles.size()).isEqualTo(2);

        assertThat(notExistsArticles).isEmpty();
    }

    @Test
    @DisplayName("최근 등록 게시물 3개 표시")
    public void findTop3ByOrderByCreatedAtDescTest() throws Exception {
        //given
        articleRepository.save(articleA);
        articleRepository.save(articleB);
        Article firstSaveArticle = articleRepository.save(articleC);
        Article middleSaveArticle = articleRepository.save(new Article(memberA, "titleD", "contentD", "titleImgD"));
        Article lastSaveArticle = articleRepository.save(new Article(memberA, "titleE", "contentE", "titleImgE"));

        //when
        List<Article> findArticles = articleRepository.findTop3ByOrderByCreatedAtDesc();

        //then
        assertThat(findArticles.size()).isEqualTo(3);
        assertThat(findArticles.get(0)).isEqualTo(lastSaveArticle);
        assertThat(findArticles.get(1)).isEqualTo(middleSaveArticle);
        assertThat(findArticles.get(2)).isEqualTo(firstSaveArticle);
        assertThat(findArticles.get(0).getCreatedAt()).isAfter(findArticles.get(1).getCreatedAt());
        assertThat(findArticles.get(1).getCreatedAt()).isAfter(findArticles.get(2).getCreatedAt());
    }

}
