package model;

import java.text.DecimalFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static model.Proveedor.WEEKS;

@Data
public class Venta {
    private int id_venta;
    private int cantidad_total_productos;
    private Empleado empleado;
    private Double total_venta;
    String methodPayment;
    private LocalDateTime fecha_venta;

    public Venta(Empleado empleado, int cantidad_total_productos, Double total_venta, String methodPayment, LocalDateTime fecha_venta) {
        this.empleado = empleado;
        this.cantidad_total_productos = cantidad_total_productos;
        this.total_venta = total_venta;
        this.methodPayment = methodPayment;
        this.fecha_venta = fecha_venta;
    }

    public Venta(int id_venta,Empleado empleado,int cantidad_total_productos, Double total_venta, String methodPayment, LocalDateTime fecha_venta) {
        this.id_venta = id_venta;
        this.empleado = empleado;
        this.cantidad_total_productos = cantidad_total_productos;
        this.total_venta = total_venta;
        this.methodPayment = methodPayment;
        this.fecha_venta = fecha_venta;
    }

    public Object[] getVentaArray() {
        return new Object[]{
                this.id_venta,
                new DecimalFormat("#,###.00").format(total_venta),
                this.cantidad_total_productos,
                this.methodPayment,
                this.fecha_venta.format(DateTimeFormatter.ofPattern("yyyy-MM-dd / hh:mm:ss a")).concat(" / " + WEEKS[this.fecha_venta.getDayOfWeek().getValue() - 1])};
    }

}
