package com.demo.Spring.boot.controller;

import com.demo.Spring.boot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.is;
@ExtendWith(SpringExtension.class) //Runwith 대신 ExtendsWith 사용
@WebMvcTest(controllers = HiController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
public class HiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void Hi리턴() throws Exception{
        String hi = "hi";
        mvc.perform(get("/Hi")).andExpect(status().isOk()).andExpect(content().string(hi));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void dto리턴() throws Exception{
        String name = "HELLO";
        int amount = 1000;
        mvc.perform(get("/Hi/dto").param("name",name).param("amount",String.valueOf(amount)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name",is(name)))
                .andExpect(status().isOk()).andExpect(jsonPath("amount",is(amount)));
    }

}
