package org.example.services;

import org.example.models.Producto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestión de archivos de texto que almacenan información de productos.
 *
 * <p><strong>Objetivo didáctico:</strong> Comparar y utilizar las dos principales
 * formas de trabajar con archivos en Java: <em>Java IO</em> (orientado a streams)
 * y <em>Java NIO</em> (orientado a rutas y operaciones eficientes).</p>
 *
 * <p>Los productos se almacenan con el formato: {@code id;nombre;precio;stock;}</p>
 *
 * <p><strong>Recurso clave:</strong> El nombre del archivo se obtiene desde
 * {@link ConfigService}, permitiendo configurar la ruta sin modificar código.</p>
 *
 * @author Grupo 1
 * @version 1.0
 */
public class FileService {

    private static String fileName;
    private static ConfigService configService;

    /**
     * Inyecta una instancia de {@link ConfigService} para obtener la configuración.
     *
     * @param configService Instancia de ConfigService a inyectar
     */
    public static void setConfigService(ConfigService configService) {
        FileService.configService = configService;
        if (FileService.configService != null && FileService.configService.props != null) {
            FileService.fileName = FileService.configService.props.getProperty("fichero", "temp.txt");
        }
    }

    /**
     * Guarda una lista de productos en el archivo de texto especificado.
     *
     * <p><strong>API utilizada:</strong> Java IO - {@link BufferedWriter} + {@link FileWriter}</p>
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
            throw new RuntimeException("Error al guardar el archivo de productos", e);
        }
    }

    /**
     * Lee todos los productos del archivo de texto utilizando Java IO.
     *
     * <p><strong>API utilizada:</strong> Java IO - {@link BufferedReader} + {@link FileReader}
     * junto con un bucle para leer línea a línea.</p>
     *
     * <p><strong>Formato esperado:</strong> {@code id;nombre;precio;stock;}</p>
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
            throw new RuntimeException("El archivo no existe: " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de productos", e);
        }

        return salida;
    }

    /**
     * Lee todos los productos del archivo de texto utilizando Java NIO.
     *
     * <p><strong>API utilizada:</strong> Java NIO - {@link Files#readAllLines(Path)}
     * Lee todas las líneas del archivo en una sola operación.</p>
     *
     * <p><strong>Formato esperado:</strong> {@code id;nombre;precio;stock;}</p>
     *
     * <p><strong>Nota para alumnos:</strong> A diferencia de BufferedReader (stream),
     * readAllLines carga todo el contenido en memoria. Útil para archivos pequeños.</p>
     *
     * @return Lista de productos leídos del archivo
     * @throws RuntimeException Si ocurre un error durante la lectura
     */
    public static List<Producto> leerArchivoTextoNIO() {
        Path archivo = Path.of(fileName);
        var salida = new ArrayList<Producto>();
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de productos con NIO", e);
        }

        lineas.forEach(line -> salida.add(createProductoFromString(line)));

        return salida;
    }


    /**
     * Lee y filtra productos del archivo de texto utilizando Streams con Java NIO.
     *
     * <p><strong>API utilizada:</strong> Java NIO - {@link Files#lines(Path)}
     * combinado con {@link java.util.stream.Stream} para filtrar y transformar datos.</p>
     *
     * <p><strong>Filtrado:</strong> Solo se devuelven las líneas que contienen
     * la palabra clave, sin distinguir entre mayúsculas y minúsculas (case-insensitive).</p>
     *
     * <p><strong>Formato esperado:</strong> {@code id;nombre;precio;stock;}</p>
     *
     * <p><strong>Nota para alumnos:</strong> {@code Files.lines()} devuelve un Stream
     * que debe cerrarse automáticamente con try-with-resources implícito, pero al
     * consumirlo con forEach se cierra correctamente.</p>
     *
     * @param keyword Palabra clave para filtrar las líneas del archivo
     * @return Lista de productos filtrados según la palabra clave
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
            throw new RuntimeException("Error al leer y filtrar el archivo de productos", e);
        }
        return salida;
    }

    /* Métodos auxiliares */

    /**
     * Convierte una cadena con formato {@code id;nombre;precio;stock;} en un objeto {@link Producto}.
     *
     * <p><strong>Proceso:</strong> Divide la línea usando el delimitador {@code ;}
     * y parsea cada campo al tipo correspondiente.</p>
     *
     * <p><strong>Formato esperado:</strong> {@code id;nombre;precio;stock;}</p>
     *
     * <p><strong>Ejemplo:</strong> {@code "1;Teclado;45.5;10;"} → Producto(1L, "Teclado", 45.5, 10)</p>
     *
     * @param s Cadena a parsear (línea del archivo)
     * @return Objeto Producto creado a partir de la cadena
     * @throws NumberFormatException Si los campos numéricos no tienen el formato esperado
     * @throws ArrayIndexOutOfBoundsException Si la cadena no tiene el formato esperado (estructura incorrecta)
     */
    private static Producto createProductoFromString(String s) {
        String[] campos = s.split(";");
        return new Producto(
                Long.parseLong(campos[0]),
                campos[1],
                Double.parseDouble(campos[2]),
                Integer.parseInt(campos[3])
        );
    }
    
}
