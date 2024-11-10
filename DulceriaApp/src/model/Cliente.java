package model;

import lombok.Getter;

@Getter
public class Cliente {
    private int id_cliente;
    private String nom_cliente;
    public Cliente(int id_cliente, String nom_cliente) {
        this.id_cliente = id_cliente;
        this.nom_cliente = nom_cliente;
    }
    public boolean verifyNotEmpty() {
        return nom_cliente != null && !nom_cliente.trim().isEmpty();
    }
    public Object[] getClientAsArray() {
        return new Object[]{this.id_cliente, this.nom_cliente};
    }
    @Override
    public String toString() {
        return "Id cliente" + this.id_cliente + " Nombre cliente" + this.nom_cliente;}
}
