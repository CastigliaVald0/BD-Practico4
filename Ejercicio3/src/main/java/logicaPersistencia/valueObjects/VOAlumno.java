package logicaPersistencia.valueObjects;

import java.io.Serializable;

/** Value Object con los datos basicos de un alumno. */
public class VOAlumno implements Serializable {

    private static final long serialVersionUID = 1L;

    private int cedula;
    private String nombre;
    private String apellido;

    public VOAlumno(int cedula, String nombre, String apellido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
