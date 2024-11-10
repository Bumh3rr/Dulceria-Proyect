package model;

import lombok.Data;

@Data
public class Producto {

    private int id;
    private String nombre;
    private String marca;
    private String descripcion;
    private int stock;
    private double precio_Compra;
    private double precio_Venta;
    private Categoria categoria;
    private Proveedor proveedor;

    public Producto(int id, String nombre, String marca, String descripcion, int stock, double precio_Compra, double precio_Venta, Categoria categoria, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio_Compra = precio_Compra;
        this.precio_Venta = precio_Venta;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    public Producto(String nombre, String marca, String descripcion, int stock, double precio_Compra, double precio_Venta, Categoria categoria, Proveedor proveedor) {
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio_Compra = precio_Compra;
        this.precio_Venta = precio_Venta;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    public boolean verifyNotEmpty() {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        if (marca == null || marca.trim().isEmpty()) {
            return false;
        }
        if (stock <= 0) {
            return false;
        }
        if (precio_Venta <= 0) {
            return false;
        }
        if (precio_Compra <= 0) {
            return false;
        }
        return true;
    }

}
