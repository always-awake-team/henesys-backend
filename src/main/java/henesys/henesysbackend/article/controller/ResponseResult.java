package henesys.henesysbackend.article.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseResult<T> {

    private T data;

}
