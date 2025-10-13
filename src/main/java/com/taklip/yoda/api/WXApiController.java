package com.taklip.yoda.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.taklip.yoda.service.WXMessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/v1/wx")
@Slf4j
public class WXApiController {

    @Autowired
    WXMessageService wxMessageService;

    @GetMapping
    public ResponseEntity<String> echo(@RequestParam String signature,
            @RequestParam String timestamp, @RequestParam String nonce,
            @RequestParam String echostr) {
        if (wxMessageService.checkSignature(signature, timestamp, nonce)) {
            log.info("WeChat checkSignature success.");

            return new ResponseEntity<>(echostr, HttpStatus.OK);
        }

        log.warn("WeChat checkSignature fail.");

        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<String> handleMsg(HttpServletRequest request) {
        String result = wxMessageService.process(request);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
