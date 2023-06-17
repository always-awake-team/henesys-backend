package henesys.henesysbackend.article.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArticleDto {

    private String title;
    private String thumbnailImg;
    private String author;
    private String content;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private LocalDateTime modifiedAt;

}
