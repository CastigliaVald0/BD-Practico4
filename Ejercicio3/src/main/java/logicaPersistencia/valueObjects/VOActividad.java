package logicaPersistencia.valueObjects;

import java.io.Serializable;

/** Value Object con una actividad y la cantidad de veces que fue cursada. */
public class VOActividad implements Serializable {

    private static final long serialVersionUID = 1L;

    private String actividad;
    private int veces;

    public VOActividad(String actividad, int veces) {
        this.actividad = actividad;
        this.veces = veces;
    }

    public String getActividad() {
        return actividad;
    }

    public int getVeces() {
        return veces;
    }
}
