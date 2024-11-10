package model;

import lombok.Getter;

@Getter
public class DetalleVenta {
    private int id_detalle;
    private int id_venta;
    private int id_producto;
    private int cantidad;
    private double precio_venta;
    private double total;

    public DetalleVenta(int id_detalle, int id_venta, int id_producto, int cantidad, double precio_venta, double total) {
        this.id_detalle = id_detalle;
        this.id_venta = id_venta;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_venta = precio_venta;
        this.total = total;
    }
    public DetalleVenta( int id_producto, int cantidad, double precio_venta, double total) {
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_venta = precio_venta;
        this.total = total;
    }


    public Object[] getDetalleVentaAsArray() {
        return new Object[]{this.id_detalle, this.id_venta, this.id_producto, this.cantidad, this.precio_venta, this.total};
    }

    @Override
    public String toString() {
        return "Id detalle" + this.id_detalle + " Id venta" + this.id_venta + " Id producto" + this.id_producto + " Cantidad" + this.cantidad + " Precio venta" + this.precio_venta + " Total" + this.total;
    }
}
