package com.petProject.demo.dto;

import lombok.Builder;

@Builder
public class TokenDto {
    String accessToken;

    String refreshToken;
}
