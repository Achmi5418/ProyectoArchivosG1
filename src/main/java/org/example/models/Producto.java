package org.example.models;

public record Producto(
        Long id,
        String nombre,
        Double precio,
        Integer stock
) {

    public Producto{
        if(precio<0 || stock<0){
            throw new IllegalArgumentException("Datos incompletos");
        }
    }

}
