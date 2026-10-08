package logicaPersistencia.valueObjects;

/** Value Object de un registro incluyendo su numero, para los listados. */
public class VORegistroListado extends VORegistro {

    private static final long serialVersionUID = 1L;

    private int numero;

    public VORegistroListado(String actividad, int nota, int numero) {
        super(actividad, nota);
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }
}
