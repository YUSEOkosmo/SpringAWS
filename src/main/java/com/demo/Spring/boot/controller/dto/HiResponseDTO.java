package com.demo.Spring.boot.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HiResponseDTO {
    private final String name;
    private final int amount;
}
