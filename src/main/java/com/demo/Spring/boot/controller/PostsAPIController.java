package com.demo.Spring.boot.controller;

import com.demo.Spring.boot.controller.dto.PostsResponseDTO;
import com.demo.Spring.boot.controller.dto.PostsSaveRequestDTO;
import com.demo.Spring.boot.controller.dto.PostsUpdateRequestDTO;
import com.demo.Spring.boot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsAPIController {

    private final PostsService service;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDTO dto){
        return service.save(dto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDTO dto){
        return service.update(id, dto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDTO findById(@PathVariable Long id){
        return service.findById(id);
    }
}
