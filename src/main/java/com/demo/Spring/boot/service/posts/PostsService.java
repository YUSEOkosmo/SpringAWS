package com.demo.Spring.boot.service.posts;

import com.demo.Spring.boot.controller.dto.PostsResponseDTO;
import com.demo.Spring.boot.controller.dto.PostsSaveRequestDTO;
import com.demo.Spring.boot.controller.dto.PostsUpdateRequestDTO;
import com.demo.Spring.boot.domain.posts.Posts;
import com.demo.Spring.boot.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository reposi;

    @Transactional
    public Long save(PostsSaveRequestDTO dto){
        return reposi.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDTO dto){
        Posts post = reposi.findById(id).orElseThrow(() -> new IllegalArgumentException("글이없음 ID: " + id));
        post.update(dto.getTitle(), dto.getContent());
        return id;
    }
    public PostsResponseDTO findById(Long id){
        Posts entity = reposi.findById(id).orElseThrow(()->new IllegalArgumentException("글이없음 ID: " + id));
        return new PostsResponseDTO(entity);
    }
}
