package model;

import lombok.Data;

@Data
public class DetalleVenta {
    private int id_detalle;
    private int id_producto;
    private Double total;
    private int cantidad;
    private int id_venta;


    public DetalleVenta(int id_producto, Double total_venta, int cantidad) {
        this.id_producto = id_producto;
        this.total = total_venta;
        this.cantidad = cantidad;
    }

    public static class DetalleVentaSub {
        private int cantidad;
        private String nombre_producto;
        private Double precio_unitario;
        private Double precio_total;


        public DetalleVentaSub(int cantidad, String nombre_producto, Double precio_unitario, Double precio_total) {
            this.cantidad = cantidad;
            this.nombre_producto = nombre_producto;
            this.precio_unitario = precio_unitario;
            this.precio_total = precio_total;
        }

        public Object[] getDetalleVentaSubArray(int num) {
            return new Object[]{
                    num,
                    this.nombre_producto,
                    this.cantidad,
                    new StringBuilder("$").append(this.precio_unitario),
                    new StringBuilder("$").append(this.precio_total)
            };
        }

    }


}
