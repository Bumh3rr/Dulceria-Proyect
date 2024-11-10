package dao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * PoolThreads es una clase singleton que gestiona un pool de hilos usando ExecutorService.
 */
public class PoolThreads {

    private ExecutorService executorService;
    private static PoolThreads instance;

    /**
     * Devuelve la instancia singleton de PoolThreads.
     *
     * @return la instancia singleton de PoolThreads
     */
    public static PoolThreads getInstance(){
        if (instance == null) {
            instance = new PoolThreads();
        }
        return instance;
    }

    /**
     * Construye un objeto PoolThreads e inicializa el ExecutorService con un pool de hilos en caché.
     */
    public PoolThreads(){
        executorService = Executors.newCachedThreadPool();
    }

    /**
     * Obtiene la instancia de ExecutorService.
     *
     * @return la instancia de ExecutorService
     */
    public ExecutorService getExecutorService(){
        return executorService;
    }

    /**
     * Cierra el ExecutorService y establece la instancia a null.
     */
    public void close() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            instance = null;
        }
    }

}