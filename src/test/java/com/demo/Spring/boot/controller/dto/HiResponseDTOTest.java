package com.demo.Spring.boot.controller.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HiResponseDTOTest {
    @Test
    public void 롬복기능(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HiResponseDTO dto = new HiResponseDTO(name, amount);
        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
