package com.demo.Spring.boot.controller;

import com.demo.Spring.boot.controller.dto.PostsSaveRequestDTO;
import com.demo.Spring.boot.controller.dto.PostsUpdateRequestDTO;
import com.demo.Spring.boot.domain.posts.Posts;
import com.demo.Spring.boot.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsAPIControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemp;

    @Autowired
    private PostsRepository reposi;

//    @AfterEach
////    public void clean() throws Exception{
////        reposi.deleteAll();
////    }

    @Test
    public void Post등록() throws Exception{
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDTO dto = PostsSaveRequestDTO.builder()
                .title(title)
                .content(content)
                .writer("작성자")
                .build();
        String URL = "http://localhost:" + port + "/api/v1/posts";
        //when
        ResponseEntity<Long> entity = restTemp.postForEntity(URL, dto, Long.class);
        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isGreaterThan(0L);
        List<Posts> all = reposi.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void 수정() throws Exception{
        //given
        Posts savepost = reposi.save(Posts.builder()
                .title("제목").content("내용").writer("글쓴이").build());
        Long updatedID = savepost.getId();
        String expectTitle = "제목2";
        String expectContenet = "내용2";

        PostsUpdateRequestDTO dto = PostsUpdateRequestDTO.builder()
                .title(expectTitle)
                .content(expectContenet)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedID;
        HttpEntity<PostsUpdateRequestDTO> requestEntity = new HttpEntity<>(dto);
        //when
        ResponseEntity<Long> responseEntity = restTemp.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> all = reposi.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectContenet);
    }
}
