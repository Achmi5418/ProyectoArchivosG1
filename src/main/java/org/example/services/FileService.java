package org.example.services;

import org.example.models.Producto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileService {

    private static String fileName;

    static {
        Properties prop = new Properties();
        try {
            prop.load( new FileReader("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileService.fileName = prop.getProperty("fichero","temp.txt");
    }

    /**
     * Guarda una lista de productos en el archivo de texto especificado.
     * Cada producto se guarda en una línea con formato: id;nombre;precio;stock;
     *
     * @param productos Lista de productos a guardar
     * @throws IllegalArgumentException Si el nombre del archivo o la lista son nulos
     * @throws RuntimeException Si ocurre un error durante la escritura
     */
    public static void guardarArchivoTexto(List<Producto> productos) {
        if (fileName == null) {
            throw new IllegalArgumentException("El archivo no puede ser nulo");
        }
        if (productos == null) {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }
        try (var bfw = new BufferedWriter(new FileWriter(fileName))) {
            for (Producto producto : productos) {
                bfw.write(
                        producto.id() + ";" +
                                producto.nombre() + ";" +
                                producto.precio() + ";" +
                                producto.stock() + ";"
                );
                bfw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lee todos los productos del archivo de texto utilizando BufferedReader.
     * Cada línea del archivo debe tener el formato: id;nombre;precio;stock;
     *
     * @return Lista de productos leídos del archivo
     * @throws RuntimeException Si el archivo no existe o ocurre un error durante la lectura
     */
    public static List<Producto> leerArchivoTexto() {
        var salida = new ArrayList<Producto>();
        try (var bfr = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = bfr.readLine()) != null) {
                salida.add(createProductoFromString(s));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return (salida);
    }

    /**
     * Lee todos los productos del archivo de texto utilizando NIO (Files.readAllLines).
     * Cada línea del archivo debe tener el formato: id;nombre;precio;stock;
     *
     * @return Lista de productos leídos del archivo
     * @throws RuntimeException Si ocurre un error durante la lectura
     */
    public static List<Producto> leerArchivoTextoNIO() {
        Path archivo = Path.of(fileName);
        var salida = new ArrayList<Producto>();
        var lineas = new ArrayList<String>();

        try {
            lineas = (ArrayList<String>) Files.readAllLines(archivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lineas.forEach(line -> salida.add(createProductoFromString(line)));

        return salida;
    }


    /**
     * Lee y filtra productos del archivo de texto utilizando un stream NIO.
     * Solo se devuelven las líneas que contienen la palabra clave (case-insensitive).
     * Cada línea del archivo debe tener el formato: id;nombre;precio;stock;
     *
     * @param keyword Palabra clave para filtrar las líneas
     * @return Lista de productos filtrados
     * @throws RuntimeException Si ocurre un error durante la lectura
     */
    public static List<Producto> leerArchivoTextoFiltrado(String keyword) {
        Path archivo = Path.of(fileName);
        var salida = new ArrayList<Producto>();

        try {

            Files.lines(archivo)
                    .filter(line -> line.toLowerCase().contains(keyword.toLowerCase()))
                    .forEach(line -> salida.add(createProductoFromString(line)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return salida;
    }

    /* Helpers */

    /**
     * Convierte una cadena con formato "id;nombre;precio;stock;" en un objeto Producto.
     *
     * @param s Cadena a parsear
     * @return Objeto Producto creado a partir de la cadena
     * @throws NumberFormatException Si los campos numéricos no tienen el formato esperado
     * @throws ArrayIndexOutOfBoundsException Si la cadena no tiene el formato esperado
     */
    private static Producto createProductoFromString(String s) {
        String[] campos = s.split(";");
        Producto p = new Producto(
                Long.parseLong(campos[0]),
                campos[1],
                Double.parseDouble(campos[2]),
                Integer.parseInt(campos[3])
        );
        return p;
    }
    
}
