package model;

import java.text.DecimalFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static model.Proveedor.WEEKS;

@Data
public class Venta {

    private int id_venta;
    private int id_Empleado;
    private Double total_venta;
    private String methodPayment;
    private LocalDateTime fecha_venta;

    public Venta(Double total_venta, int id_Empleado, LocalDateTime fecha_venta, String methodPayment) {
        this.total_venta = total_venta;
        this.id_Empleado = id_Empleado;
        this.fecha_venta = fecha_venta;
        this.methodPayment = methodPayment;
    }

    public Venta(int id_venta, Double total_venta, String methodPayment, LocalDateTime fecha_venta) {
        this.id_venta = id_venta;
        this.total_venta = total_venta;
        this.methodPayment = methodPayment;
        this.fecha_venta = fecha_venta;
    }

    public Object[] getVentaArray() {
        return new Object[]{
            this.id_venta,
            new DecimalFormat("#,###.00").format(total_venta),
            this.methodPayment,
            this.fecha_venta.format(DateTimeFormatter.ofPattern("yyyy-MM-dd / hh:mm:ss a")).concat(" / " + WEEKS[this.fecha_venta.getDayOfWeek().getValue() - 1])};
    }
}
