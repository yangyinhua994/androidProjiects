package com.example.urldemo.utils;

import java.util.concurrent.Executor;

/**
 * @author yangyinhua
 */
public class ExecutorUtl {

    private static Executor executor = ExecutorUtil.getExecutor();

    public static Executor getExecutor(){
        return executor;
    }

}
