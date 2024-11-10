package form.request;

import dao.CategoriaDao;
import dao.PoolThreads;
import model.Categoria;

import java.util.LinkedList;

public class RequestCategoria {
    public static Boolean addCategoria(Categoria categoriaN) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return CategoriaDao.addCategoriaBD(categoriaN);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    public static LinkedList<Categoria> getCategoriasAll() throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return dao.CategoriaDao.getCategoriasBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
}
