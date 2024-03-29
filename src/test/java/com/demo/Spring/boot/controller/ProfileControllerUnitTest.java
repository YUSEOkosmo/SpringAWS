package com.demo.Spring.boot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import static org.assertj.core.api.Assertions.*;

public class ProfileControllerUnitTest {
    @Test
    public void real_profile조회(){
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");
        ProfileController controller = new ProfileController(env);
        //when
        String profile = controller.profile();
        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void real_profile이없으면_1번째조회(){
        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);
        //when
        String profile = controller.profile();
        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile이없으면_defafult조회(){
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);
        //when
        String profile = controller.profile();
        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
