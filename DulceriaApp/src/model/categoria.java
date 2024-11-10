package model;

import lombok.Getter;

public class categoria {
    private int id;
    @Getter
    private String tipo;
    public categoria(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public boolean verifyNotEmpty() {
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    public Object[] getCategoriaArray(){
        return new Object[]{
            this.id,
            this.tipo
        };
    }


}
