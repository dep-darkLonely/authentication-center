package com.hb.authenticationcenter.service.impl;

import com.hb.authenticationcenter.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author admin
 * @version 1.0
 * @description captcha service impl
 * @date 2022/12/30
 */
@Slf4j
@Service
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
public class CaptchaServiceImpl implements CaptchaService {
}
