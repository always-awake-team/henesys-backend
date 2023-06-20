package henesys.henesysbackend.comment.repository;

import henesys.henesysbackend.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByArticleIdOrderByCreatedAt(Long articleId);

}
