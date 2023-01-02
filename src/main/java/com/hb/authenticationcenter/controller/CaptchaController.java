package com.hb.authenticationcenter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hb.authenticationcenter.service.CaptchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @version 1.0
 * @description captcha controller
 * @date 2022/12/30
 */
@RestController
@RequestMapping("/public/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping
    public ResponseEntity<?> generateCaptcha() {
        return ResponseEntity.ok("avcd");
    }
}
