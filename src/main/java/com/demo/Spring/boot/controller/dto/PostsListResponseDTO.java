package com.demo.Spring.boot.controller.dto;

import com.demo.Spring.boot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDTO {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime modifiedDate;

    public PostsListResponseDTO(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.writer = entity.getWriter();
        this.modifiedDate = entity.getModifiedDate();
    }
}
