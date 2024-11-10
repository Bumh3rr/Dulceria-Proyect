package model;

import lombok.Data;

@Data
public class Producto {
    private int id;
    private String nombre;
    private String marca;
    private String descripcion;
    private int stock;
    private Float precio_compra;
    private Float precio_venta;
}
