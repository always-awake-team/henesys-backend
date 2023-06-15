package henesys.henesysbackend.article.domain.entity;

import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
    private String title;
    private String content;
    private String titleImg;
    private int commentCount;
    private int likeCount;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    public Article(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public Article(Member member, String title, String content, String titleImg) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.titleImg = titleImg;
    }
}
