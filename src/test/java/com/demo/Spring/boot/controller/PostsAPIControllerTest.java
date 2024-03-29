package com.demo.Spring.boot.controller;

import com.demo.Spring.boot.controller.dto.PostsSaveRequestDTO;
import com.demo.Spring.boot.controller.dto.PostsUpdateRequestDTO;
import com.demo.Spring.boot.domain.posts.Posts;
import com.demo.Spring.boot.domain.posts.PostsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsAPIControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemp;

    @Autowired
    private PostsRepository reposi;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

//    @AfterEach
////    public void clean() throws Exception{
////        reposi.deleteAll();
////    }

    @Test
    @WithMockUser(roles = "USER")
    public void Post등록() throws Exception{
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDTO dto = PostsSaveRequestDTO.builder()
                .title(title)
                .content(content)
                .writer("작성자")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";
        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = reposi.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 수정() throws Exception{
        //given
        Posts savepost = reposi.save(Posts.builder()
                .title("제목").content("내용").writer("글쓴이").build());
        Long updatedID = savepost.getId();
        String expectTitle = "title";
        String expectContenet = "content";

        PostsUpdateRequestDTO dto = PostsUpdateRequestDTO.builder()
                .title(expectTitle)
                .content(expectContenet)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedID;
        HttpEntity<PostsUpdateRequestDTO> requestEntity = new HttpEntity<>(dto);
        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = reposi.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectContenet);
    }
}
