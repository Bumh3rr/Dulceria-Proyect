package model;

import form.panels.PanelRequestVenta;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Venta {
    private int id_venta;
    private int id_Empleado;
    private Double total_venta;
    String methodPayment;
    private LocalDateTime fecha_venta;

    public Venta(Double total_venta, int id_Empleado, LocalDateTime fecha_venta, String methodPayment) {
        this.total_venta = total_venta;
        this.id_Empleado = id_Empleado;
        this.fecha_venta = fecha_venta;
        this.methodPayment = methodPayment;
    }
}
