package model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Empleado {

    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String rfc;
    private Puesto puesto;
    private Status estado;
    private Double sueldo;
    private Double venta_semanal;
    private Double comision;
    private LocalDateTime fecha_registro;
    private LocalDateTime fecha_baja;

    public Empleado(int idEmpleado, String nombre, String apellido, String telefono, String direccion, String rfc, Puesto puesto, Status estado, Double sueldo, Double venta_semanal, Double comision, LocalDateTime fecha_registro, LocalDateTime fecha_baja) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rfc = rfc;
        this.puesto = puesto;
        this.estado = estado;
        this.sueldo = sueldo;
        this.venta_semanal = venta_semanal;
        this.comision = comision;
        this.fecha_registro = fecha_registro;
        this.fecha_baja = fecha_baja;
    }

    public Empleado(String nombre, String apellido, String telefono, String direccion, String rfc, Puesto puesto, Status estado, Double sueldo, LocalDateTime fecha_registro) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rfc = rfc;
        this.puesto = puesto;
        this.estado = estado;
        this.sueldo = sueldo;
        this.fecha_registro = fecha_registro;
    }

    public Empleado(int idEmpleado, String nombre, String apellido) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "ID: " + idEmpleado + " | Nombre: " + nombre + " " + apellido;
    }

    public enum Status {
        Activo, Inactivo
    }

    public enum Puesto {
        VENDEDOR, SUPERVISOR
    }

}
