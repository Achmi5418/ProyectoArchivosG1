package org.example;

import java.io.File;

/**
 * Ejemplo de uso de Java IO para trabajar con archivos y directorios.
 *
 * <p><strong>Objetivo didáctico:</strong> Aprender a usar la clase {@link File}
 * para obtener información sobre ficheros y listar el contenido de un directorio.</p>
 *
 * <p>En este ejemplo se analiza el directorio actual ({@code "."}) mostrando
 * sus propiedades y el listado de archivos contenidos.</p>
 *
 * @author Grupo 1
 * @version 1.0
 */
public class MainIO {

    /**
     * Método de ejemplo para explorar archivos con Java IO.
     *
     * <p>Muestra información útil como: existencia, permisos (lectura/escritura/ejecución),
     * si es fichero o directorio y su tamaño. Además, lista todos los archivos
     * del directorio actual.</p>
     */
    public static void main() {
        // Java IO: Clase File para gestionar ficheros y directorios
        File f = new File(".");

        System.out.println("¿Existe? " + f.exists());
        System.out.println("¿Se puede leer? " + f.canRead());
        System.out.println("¿Se puede escribir? " + f.canWrite());
        System.out.println("¿Es un fichero? " + f.isFile());
        System.out.println("¿Es un directorio? " + f.isDirectory());
        System.out.println("¿Se puede ejecutar? " + f.canExecute());
        System.out.println("Tamaño (bytes): " + f.length());

        String[] archivos = f.list();
        if (archivos != null) {
            for (String filename : archivos) {
                System.out.println("Archivo/Directorio: " + filename);
            }
        }
    }
}
