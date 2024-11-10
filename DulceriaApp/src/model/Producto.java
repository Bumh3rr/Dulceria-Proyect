package model;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Producto {
    private int id;
    private String nombre;
    private String marca;
    private String descripcion;
    private int stock;
    private double precio_Compra;
    private double precio_Venta;
    private int id_Categoria;
    private int id_Prov;


    public Producto(int id, String nombre, String marca, String descripcion, int stock, double precio_Compra, double precio_Venta, int id_Categoria, int id_Prov) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio_Compra = precio_Compra;
        this.precio_Venta = precio_Venta;
        this.id_Categoria = id_Categoria;
        this.id_Prov = id_Prov;
    }
}
