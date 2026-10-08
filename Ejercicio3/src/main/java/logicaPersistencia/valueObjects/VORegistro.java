package logicaPersistencia.valueObjects;

import java.io.Serializable;

/** Value Object con la actividad cursada y la nota obtenida. */
public class VORegistro implements Serializable {

    private static final long serialVersionUID = 1L;

    private String actividad;
    private int nota;

    public VORegistro(String actividad, int nota) {
        this.actividad = actividad;
        this.nota = nota;
    }

    public String getActividad() {
        return actividad;
    }

    public int getNota() {
        return nota;
    }
}
