package com.hb.authenticationcenter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hb.authenticationcenter.common.ResponseResult;
import com.hb.authenticationcenter.controller.response.DefaultView;
import com.hb.authenticationcenter.entity.SecurityUserDetails;
import com.hb.authenticationcenter.security.authority.Authority;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @version 1.0
 * @description User Controller.
 * @date 2022/12/30
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController {

    @GetMapping
    @JsonView(DefaultView.class)
    @PreAuthorize(Authority.Role.ADD)
    public ResponseEntity<ResponseResult> getPageUsers() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        SecurityUserDetails details = (SecurityUserDetails) authentication.getCredentials();
        return ResponseEntity.ok(ResponseResult.success(details.getUserEntity()));
    }
}
