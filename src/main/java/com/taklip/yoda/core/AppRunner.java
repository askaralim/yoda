package com.taklip.yoda.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author askar
 */
@Component
@Order(1)
public class AppRunner implements ApplicationRunner {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) {
        logger.debug("AppRunner is started.");
    }
}