package com.petProject.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class TokenDto {
    String accessToken;

    String refreshToken;
}
