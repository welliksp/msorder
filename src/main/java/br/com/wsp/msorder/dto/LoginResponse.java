package br.com.wsp.msorder.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
