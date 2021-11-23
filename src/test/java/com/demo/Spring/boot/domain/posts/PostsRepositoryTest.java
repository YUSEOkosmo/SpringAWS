package com.demo.Spring.boot.domain.posts;

import com.demo.Spring.boot.domain.posts.Posts;
import com.demo.Spring.boot.domain.posts.PostsRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository reposi;
//
//    @AfterEach
//    public void clean(){
//        reposi.deleteAll();
//    }

    @Test
    public void 쓰기_저장(){
        //given
        String title = "TITLE";
        String content = "CONTENT";
        reposi.save(Posts.builder().title(title).content(content).writer("글쓴이").build());
        //when
        List<Posts> postList = reposi.findAll();
        //then
        Posts posts = postList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void 시간(){
        //given
        LocalDateTime now = LocalDateTime.of(2021,11,15,0,0,0);
        reposi.save(Posts.builder().title("TITLE").content("CONTENT").writer("WRITER").build());
        //when
        List<Posts> postlist = reposi.findAll();
        //then
        Posts post = postlist.get(0);

        System.out.println(">>>>>>>>>>>>>CREATE DATE="+post.getCreatedDate()+", MODIFIED="+post.getModifiedDate());

        assertThat(post.getCreatedDate().isAfter(now));
        assertThat(post.getModifiedDate().isAfter(now));
    }
}
