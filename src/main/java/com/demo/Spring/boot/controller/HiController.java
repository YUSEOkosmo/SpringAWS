package com.demo.Spring.boot.controller;

import com.demo.Spring.boot.controller.dto.HiResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @GetMapping("/Hi")
    public String hi(){
        return "hi";
    }
    @GetMapping("/Hi/dto")
    public HiResponseDTO hidto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HiResponseDTO(name, amount);
    }
}
