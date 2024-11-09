package dao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolThreads {

    private ExecutorService executorService;
    private static PoolThreads instance;

    public static PoolThreads getInstance(){
        if (instance == null) {
            instance = new PoolThreads();
        }
        return instance;
    }

    public PoolThreads(){
        executorService = Executors.newCachedThreadPool();
    }
    
    
    public ExecutorService getExecutorService(){
        return executorService;
    }
    

    public void close() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            instance = null;
        }
    }

}
