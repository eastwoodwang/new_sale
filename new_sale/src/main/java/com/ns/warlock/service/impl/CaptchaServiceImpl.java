package com.ns.warlock.service.impl;

import com.ns.warlock.service.CaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

/**
 *
 */
@Service("captchaServiceImpl")
public class CaptchaServiceImpl implements CaptchaService {

    @Resource(name = "imageCaptchaService")
    private com.octo.captcha.service.CaptchaService imageCaptchaService;

    @Override
    public BufferedImage buildImage(String captchaId) {
        return (BufferedImage) imageCaptchaService.getChallengeForID(captchaId);
    }

    @Override
    public boolean isValid(String captchaId, String captcha) {
        boolean result = false;
        if (StringUtils.isNotEmpty(captchaId) && StringUtils.isNotEmpty(captcha)) {
            try {
                result = imageCaptchaService.validateResponseForID(captchaId, captcha.toUpperCase());
            } catch (Exception e) {
                return result;
            }
        } else {
            return result;
        }
        return result;
    }
}
