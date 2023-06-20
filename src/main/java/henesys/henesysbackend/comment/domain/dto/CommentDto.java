package henesys.henesysbackend.comment.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

public class CommentDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = PROTECTED)
    @AllArgsConstructor(access = PROTECTED)
    public static class ResponseCommentDto {

        private Long id;
        private String author;
        private String content;
        private LocalDateTime modifiedAt;

    }

}
