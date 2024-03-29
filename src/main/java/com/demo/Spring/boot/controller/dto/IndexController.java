package com.demo.Spring.boot.controller.dto;

import com.demo.Spring.boot.config.auth.LoginUser;
import com.demo.Spring.boot.config.auth.dto.SessionUser;
import com.demo.Spring.boot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService service;
    //private final HttpSession session;
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser sUser){
        model.addAttribute("posts",service.findAllDesc()); 
        //findAllDesc를 통해서 불러온 data를 posts라는 이름으로 index에 맵핑

        //SessionUser sUser = (SessionUser) session.getAttribute("user");

        if(sUser != null){
            model.addAttribute("userName",sUser);
            System.out.println("NAME: " + sUser.getName() + "EMAIL: " + sUser.getEmail());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model md){
        PostsResponseDTO dto = service.findById(id);
        md.addAttribute("post",dto);
        return "posts-update";
    }

}
