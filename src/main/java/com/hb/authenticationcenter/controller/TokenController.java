package com.hb.authenticationcenter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hb.authenticationcenter.common.ResponseResult;
import com.hb.authenticationcenter.controller.response.SimpleView;
import com.hb.authenticationcenter.controller.response.UserResponse;
import com.hb.authenticationcenter.security.authority.Authority;
import com.hb.authenticationcenter.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hb.authenticationcenter.security.httpconverter.BearerAuthenticationConverter.AUTHENTICATION_SCHEME_BEARER;
import static org.pac4j.core.context.HttpConstants.AUTHORIZATION_HEADER;
import static org.pac4j.core.context.HttpConstants.BEARER_HEADER_PREFIX;

/**
 * @author admin
 * @version 1.0
 * @description Token Controller：token refresh
 * @date 2023/1/8
 */
@Slf4j
@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/refresh")
    @JsonView(SimpleView.class)
    @PreAuthorize(Authority.Common.AUTHENTICATED)
    public ResponseEntity<ResponseResult> refreshToken(
            @RequestHeader(name = AUTHORIZATION_HEADER) String bearerToken, HttpServletResponse response) {
        String oldToken = bearerToken.substring(AUTHENTICATION_SCHEME_BEARER.length()).trim();
        UserResponse userResponse = tokenService.refreshToken(oldToken);
        response.setHeader(AUTHORIZATION_HEADER, BEARER_HEADER_PREFIX + userResponse.getToken());
        return ResponseEntity.ok(ResponseResult.success(userResponse));
    }
}
