package logicaPersistencia.valueObjects;

/** Value Object de un alumno junto con su promedio de notas. */
public class VOAlumnoProm extends VOAlumno {

    private static final long serialVersionUID = 1L;

    private double promedio;

    public VOAlumnoProm(int cedula, String nombre, String apellido, double promedio) {
        super(cedula, nombre, apellido);
        this.promedio = promedio;
    }

    public double getPromedio() {
        return promedio;
    }
}
