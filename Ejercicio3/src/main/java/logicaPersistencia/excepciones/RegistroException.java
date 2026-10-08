package logicaPersistencia.excepciones;

/** Error de logica referido a los registros de notas (no existe / el alumno no tiene registros). */
public class RegistroException extends NotasException {

    private static final long serialVersionUID = 1L;

    public RegistroException(String msj) {
        super(msj);
    }
}
