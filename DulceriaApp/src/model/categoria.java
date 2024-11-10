package model;

import lombok.Data;
@Data
public class Categoria {
    private int id;
    private String tipo;

    public Categoria(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    public Categoria(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.tipo;
    }
}
