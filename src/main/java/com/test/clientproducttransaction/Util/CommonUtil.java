package com.test.clientproducttransaction.Util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import static java.lang.Thread.sleep;

@Slf4j
public class CommonUtil {
    public static StopWatch stopWatch = new StopWatch();

    public static void delay(long delayMilliSeconds) {
        try{
            sleep(delayMilliSeconds);
        }catch(Exception r){log.info("Exception is"+r.getMessage());}
    }


    public static String transForm(String s) {
        CommonUtil.delay(500);
        return s.toUpperCase();
    }

    public static void startTimer(){
        stopWatch.start();
    }

    public static void timeTaken(){
        stopWatch.stop();
        log.info("Total Time Taken : " +stopWatch.getTime());
    }

    public static void stopWatchReset(){
        stopWatch.reset();
    }

    public static  int noOfCores(){
        return Runtime.getRuntime().availableProcessors();
    }
}
