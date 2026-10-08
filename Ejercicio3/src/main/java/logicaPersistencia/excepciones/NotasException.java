package logicaPersistencia.excepciones;

/**
 * Excepcion base de la aplicacion: agrupa todos los errores de logica
 * (chequeos que fallan) que la Fachada notifica a la capa grafica.
 */
public class NotasException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotasException(String msj) {
        super(msj);
    }
}
