package com.poupix.poupix.configs;

import lombok.Builder;

@Builder
public record JWTUserData (
        Long userId,
        String email
){
}
