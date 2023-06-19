package henesys.henesysbackend.article.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ArticleDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ResponseAllArticleList {

        private String title;
        private String thumbnailImg;
        private String author;
        private String content;
        private int commentCount;
        private int viewCount;
        private int likeCount;
        private LocalDateTime modifiedAt;

    }

}
