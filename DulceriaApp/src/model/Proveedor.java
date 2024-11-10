package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class Proveedor {

    private int id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String state;
    private String municipality;
    private String street;
    private String zip;
    private LocalDateTime date_register;
    
    public static final String[] WEEKS = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

    public Proveedor(int id, String first_name, String last_name, String phone, String email, String state, String municipality, String street, String zip, LocalDateTime date_register) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.state = state;
        this.municipality = municipality;
        this.street = street;
        this.zip = zip;
        this.date_register = date_register;
    }
    
    public Proveedor(String first_name, String last_name, String phone, String email, String state, String municipality, String street, String zip, LocalDateTime date_register) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.state = state;
        this.municipality = municipality;
        this.street = street;
        this.zip = zip;
        this.date_register = date_register;
    }

    public Proveedor(int id) {
        this.id = id;
    }
    

    public boolean verifyNotEmpty() {
        if (first_name == null || first_name.trim().isEmpty()) {
            return false;
        }
        if (last_name == null || last_name.trim().isEmpty()) {
            return false;
        }
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
    
    public Object[] getUserArray(){
        return new Object[]{
            this.id,
            this.first_name,
            this.last_name,
            this.phone,
            this.email,
            this.date_register.format(DateTimeFormatter.ofPattern("yyyy-MM-dd / hh:mm:ss a")).concat(" / " + WEEKS[this.date_register.getDayOfWeek().getValue() - 1]),
        };
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " | " + this.first_name + " " +this.last_name;
    }
}
