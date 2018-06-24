package com.xwb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTestDemo {
    private static Logger logger = LoggerFactory.getLogger(Log4jTestDemo.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println("This is println message.");

        // ��¼debug�������Ϣ
        logger.debug("This is debug message.");
        // ��¼info�������Ϣ
        logger.info("This is info message.");
        // ��¼error�������Ϣ
        logger.error("This is error message.");
    }


}