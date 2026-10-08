package logicaPersistencia.excepciones;

/** Error de logica referido a los alumnos (no existe / ya existe / no hay alumnos). */
public class AlumnoException extends NotasException {

    private static final long serialVersionUID = 1L;

    public AlumnoException(String msj) {
        super(msj);
    }
}
