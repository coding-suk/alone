package com.web.personalstudy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public static void logError(String message, Exception e) {
        logger.error("{}: {}", message, e.getMessage());
    }

    public static void logInfo(String message, Object date) {
        logger.info("{}: {}", message, date);
    }

}
