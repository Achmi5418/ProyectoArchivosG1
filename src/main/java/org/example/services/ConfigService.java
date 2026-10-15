package org.example.services;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Servicio de configuración que gestiona la carga y acceso a propiedades
 * desde el archivo {@code config.properties}.
 *
 * <p><strong>Concepto para alumnos:</strong> Este patrón permite separar
 * la configuración (como rutas de archivos) del código fuente, haciendo
 * la aplicación más flexible y configurable.</p>
 *
 * <p>El archivo de configuración se carga automáticamente al inicializar la clase
 * (bloque static).</p>
 *
 * @author Grupo 1
 * @version 1.0
 */
public class ConfigService {

    /**
     * Colección de propiedades de configuración cargadas desde {@code config.properties}.
     * <p>Estas propiedades pueden accederse desde cualquier parte de la aplicación.</p>
     */
    public static final Properties props = new Properties();

    static {
        try {
            props.load(new FileReader("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo de configuración", e);
        }
    }

    /**
     * Obtiene el valor de una propiedad a partir de su clave.
     *
     * @param key Clave de la propiedad a buscar
     * @return Valor asociado a la clave, o {@code null} si la propiedad no existe
     */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
