package henesys.henesysbackend.comment.service;

import henesys.henesysbackend.comment.domain.entity.Comment;
import henesys.henesysbackend.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static henesys.henesysbackend.comment.domain.dto.CommentDto.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public List<ResponseCommentDto> createResponseCommentDtos(Long articleId) {
        List<Comment> findComments = commentRepository.findCommentByArticleIdOrderByCreatedAt(articleId);
        return findComments.stream().map(comment -> ResponseCommentDto.builder()
                        .id(comment.getId())
                        .author(comment.getMember().getNickname())
                        .content(comment.getContent())
                        .modifiedAt(comment.getModifiedAt())
                        .build())
                .toList();
    }
}
