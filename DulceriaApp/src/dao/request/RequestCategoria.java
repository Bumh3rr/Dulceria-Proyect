package dao.request;

import dao.CategoriaDao;
import dao.pool.PoolThreads;
import model.Categoria;

import java.util.LinkedList;

/**
 * Clase RequestCategoria que maneja las solicitudes relacionadas con las categorías.
 */
public class RequestCategoria {

    /**
     * Agrega una categoría a la base de datos.
     *
     * @param categoriaN El objeto Categoria que se desea agregar.
     * @return Booleano que indica si la categoría fue agregada exitosamente.
     * @throws Exception Si ocurre un error durante la operación.
     */
    public static Boolean addCategoria(Categoria categoriaN) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return CategoriaDao.addCategoriaBD(categoriaN);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    /**
     * Obtiene una lista de todas las categorías de la base de datos.
     *
     * @return LinkedList de objetos Categoria.
     * @throws Exception Si ocurre un error durante la operación.
     */
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