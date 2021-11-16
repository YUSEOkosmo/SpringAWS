package com.demo.Spring.boot.controller.dto;

import com.demo.Spring.boot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDTO {
    private String title;
    private String content;
    private String writer;

    @Builder
    public PostsSaveRequestDTO(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
    public Posts toEntity(){
        return Posts.builder().title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
