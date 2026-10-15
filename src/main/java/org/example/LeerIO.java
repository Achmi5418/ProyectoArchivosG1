package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Ejemplo de lectura de archivos con Java IO.
 *
 * <p><strong>Objetivo didáctico:</strong> Aprender a leer el contenido de un fichero
 * utilizando {@link BufferedReader} y el bloque {@code try-with-resources}.</p>
 *
 * <p>En este ejemplo se lee el archivo {@code pom.xml} de dos formas diferentes:
 * mediante {@code readAllLines()} y mediante lectura línea por línea con un bucle {@code while}.</p>
 *
 * <p><strong>Importante:</strong> Tras leer con {@code readAllLines()}, el lector
 * llega al final del archivo. Si se intenta leer de nuevo línea por línea sin
 * reiniciar, no se obtendrán resultados.</p>
 *
 * @author Grupo 1
 * @version 1.0
 */
public class LeerIO {

    /**
     * Método de ejemplo para leer archivos con BufferedReader.
     */
    public static void main() {
        File f = new File("pom.xml");

        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            // Opción 1: Leer todas las líneas de una vez (Java 11+)
            List<String> lineas = bfr.readAllLines();
            lineas.forEach(s -> System.out.println(s));

            // Opción 2: Leer línea por línea
            String s;
            while ((s = bfr.readLine()) != null) {
                System.out.println(s);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("El archivo no existe: " + f.getName(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }
    }
}