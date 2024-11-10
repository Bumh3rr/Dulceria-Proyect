package model;

import lombok.Getter;

@Getter
public class Trabajador {
    private int id_Trab;
    private String nombre_Trab;
    private String puesto_Trab;
    private Double sueldo;
    private Double venta_semanal;
    private Double comision;
    public Trabajador(int id_Trab, String nombre_Trab, String puesto_Trab, Double sueldo, Double venta_semanal, Double comision) {
        this.id_Trab = id_Trab;
        this.nombre_Trab = nombre_Trab;
        this.puesto_Trab = puesto_Trab;
        this.sueldo = sueldo;
        this.venta_semanal = venta_semanal;
        this.comision = comision;
    }
    private Trabajador(String nombre_Trab, String puesto_Trab, Double sueldo, Double venta_semanal, Double comision) {
        this.nombre_Trab = nombre_Trab;
        this.puesto_Trab = puesto_Trab;
        this.sueldo = sueldo;
        this.venta_semanal = venta_semanal;
        this.comision = comision;
    }
    public Object[] getTrabajadorAsArray() {
        return new Object[]{this.id_Trab, this.nombre_Trab, this.puesto_Trab, this.sueldo, this.venta_semanal, this.comision};
    }
    @Override
    public String toString() {
        return "Id trabajador" + this.id_Trab + " Nombre trabajador" + this.nombre_Trab + " Puesto trabajador" + this.puesto_Trab + " Sueldo" + this.sueldo + " Venta semanal" + this.venta_semanal + " Comision" + this.comision;
    }
}
