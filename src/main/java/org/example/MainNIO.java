package org.example;

import java.nio.file.Path;

/**
 * Ejemplo de uso de Java NIO para trabajar con rutas de archivos.
 *
 * <p><strong>Objetivo didáctico:</strong> Introducir la API NIO ({@code java.nio.file}),
 * más moderna y flexible que la antigua API IO para gestionar rutas y ficheros.</p>
 *
 * <p>En este ejemplo se crea un objeto {@link Path} apuntando al archivo {@code pom.xml}.</p>
 *
 * @author Grupo 1
 * @version 1.0
 */
public class MainNIO {

    /**
     * Método de ejemplo para trabajar con rutas usando Java NIO.
     */
    public static void main() {
        // Java NIO: Path representa una ruta en el sistema de archivos
        Path path = Path.of("pom.xml");
    }
}
}
