package com.ps.vh.executor;

import java.util.concurrent.Executor;

public class ChExecutor implements Executor {

    private static ChExecutor singletonExecutor;

    public static ChExecutor getInstance(){
        if (singletonExecutor == null){
            singletonExecutor = new ChExecutor();
        }
        return singletonExecutor;
    }

    private void ChExecutor(){

    }

    @Override
    public void execute(Runnable runnable) {
        new Thread(runnable).start();
    }


}
