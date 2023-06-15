package henesys.henesysbackend.member.domain.entity;

import henesys.henesysbackend.article.domain.entity.Article;
import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String password;
    private RoleType roleType;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    public Member(String name, String password, RoleType roleType, String nickname, String email) {
        this.name = name;
        this.password = password;
        this.roleType = roleType;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public void changeArticle(Article article) {
        this.articles.add(article);
    }

    public void deleteArticle(Article article) {
        this.articles.remove(article);
    }

    public void changeComment(Comment comment) {
        this.comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
    }
}
