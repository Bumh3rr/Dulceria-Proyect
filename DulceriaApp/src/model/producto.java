package model;

import lombok.Getter;
import lombok.Setter;

/**
 * La clase producto representa un producto en el sistema.
 */
@Getter
@Setter
public class producto {
    private int id_Prod;
    private String nombre_Prod;
    private int id_Categoria;
    private int id_Prov;
    private int stock_Disp;
    private double precio_Compra;
    private double precio_Venta;
    private String descripcion;

    /**
     * Constructor por defecto de la clase producto.
     */
    public producto() {
    }

    /**
     * Constructor con parámetros de la clase producto.
     *
     * @param id_Prod       el ID del producto
     * @param nombre_Prod   el nombre del producto
     * @param id_Categoria  el ID de la categoría del producto
     * @param id_Prov       el ID del proveedor del producto
     * @param stock_Disp    el stock disponible del producto
     * @param precio_Compra el precio de compra del producto
     * @param precio_Venta  el precio de venta del producto
     * @param descripcion   la descripción del producto
     */
    public producto(int id_Prod, String nombre_Prod, int id_Categoria, int id_Prov, int stock_Disp, double precio_Compra, double precio_Venta, String descripcion) {
        this.id_Prod = id_Prod;
        this.nombre_Prod = nombre_Prod;
        this.id_Categoria = id_Categoria;
        this.id_Prov = id_Prov;
        this.stock_Disp = stock_Disp;
        this.precio_Compra = precio_Compra;
        this.precio_Venta = precio_Venta;
        this.descripcion = descripcion;
    }
    public boolean verifyNotEmpty() {
        return nombre_Prod != null && !nombre_Prod.trim().isEmpty() &&
                id_Categoria != 0 &&
                id_Prov != 0 &&
                stock_Disp != 0 &&
                precio_Compra != 0 &&
                precio_Venta != 0 &&
                descripcion != null && !descripcion.trim().isEmpty();
    }
    public Object[] getProductoArray(){
        return new Object[]{
            this.id_Prod,
            this.nombre_Prod,
            this.id_Categoria,
            this.id_Prov,
            this.stock_Disp,
            this.precio_Compra,
            this.precio_Venta,
            this.descripcion
        };
    }
}