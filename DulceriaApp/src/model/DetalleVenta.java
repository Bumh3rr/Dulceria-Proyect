package model;

import lombok.Data;

@Data
public class DetalleVenta {
    private int id_detalle;
    private int id_venta;
    private int id_producto;
    private int cantidad;
    private double precio_venta;
    private double total;

    public DetalleVenta( int id_producto, int cantidad, double precio_venta, double total) {
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_venta = precio_venta;
        this.total = total;
    }

}
