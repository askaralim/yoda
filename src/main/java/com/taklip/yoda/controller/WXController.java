package com.taklip.yoda.controller;

import com.taklip.yoda.service.WXMessageService;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/wx")
public class WXController {
    private final Logger logger = LoggerFactory.getLogger(WXController.class);

    @Autowired
    WXMessageService wxMessageService;

    @GetMapping
    public ResponseEntity<String> echo(
            @RequestParam String signature,
            @RequestParam String timestamp,
            @RequestParam String nonce,
            @RequestParam String echostr) {
        if (wxMessageService.checkSignature(signature, timestamp, nonce)) {
            logger.info("WeChat checkSignature success.");

            return new ResponseEntity<>(echostr, HttpStatus.OK);
        }

        logger.warn("WeChat checkSignature fail.");

        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<String> handleMsg(HttpServletRequest request) {
        String result = wxMessageService.process(request);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
