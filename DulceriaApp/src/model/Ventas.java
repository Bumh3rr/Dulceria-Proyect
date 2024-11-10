package model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Ventas {
    private int id_venta;
    private int id_Cliente;
    private int id_Trab;
    private Double total_venta;
    private LocalDateTime fecha_venta;
    public Ventas(int id_venta, int id_Cliente, int id_Trab, Double total_venta, LocalDateTime fecha_venta) {
        this.id_venta = id_venta;
        this.id_Cliente = id_Cliente;
        this.id_Trab = id_Trab;
        this.total_venta = total_venta;
        this.fecha_venta = fecha_venta;
    }
    public Ventas( int id_Cliente, int id_Trab, Double total_venta, LocalDateTime fecha_venta) {
        this.id_Cliente = id_Cliente;
        this.id_Trab = id_Trab;
        this.total_venta = total_venta;
        this.fecha_venta = fecha_venta;
    }
 @Override
    public String toString() {
        return "Id venta" + this.id_venta + " Id cliente" + this.id_Cliente +
                " Id trabajador" + this.id_Trab + " Total venta" + this.total_venta +
                " Fecha venta" + this.fecha_venta;
    }

}
