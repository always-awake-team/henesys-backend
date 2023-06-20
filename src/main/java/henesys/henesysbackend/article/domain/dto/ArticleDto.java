package henesys.henesysbackend.article.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static henesys.henesysbackend.comment.domain.dto.CommentDto.*;
import static lombok.AccessLevel.*;

public class ArticleDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = PROTECTED)
    @AllArgsConstructor(access = PROTECTED)
    public static class ResponseArticleDto {

        private Long id;
        private String title;
        private String thumbnailImg;
        private String author;
        private String content;
        private int commentCount;
        private int viewCount;
        private int likeCount;
        private LocalDateTime modifiedAt;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = PROTECTED)
    @AllArgsConstructor(access = PROTECTED)
    public static class ResponseArticleDetailDto {

        private Long id;
        private String title;
        private String author;
        private String content;
        private int commentCount;
        private int viewCount;
        private int likeCount;
        private LocalDateTime modifiedAt;
        private List<ResponseCommentDto> comments;

    }

}
