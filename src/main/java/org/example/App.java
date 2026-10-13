package org.example;

import static org.example.services.FileService.*;

public class App {
    public static void main(String[] args) {
        leerArchivoTextoFiltrado("teclado").forEach(System.out::println);
    }




}
