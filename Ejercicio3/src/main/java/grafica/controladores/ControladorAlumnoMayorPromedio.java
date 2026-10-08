package grafica.controladores;

import grafica.ventanas.VentanaAlumnoMayorPromedio;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.NotasException;
import logicaPersistencia.excepciones.PersistenciaException;

/** Controlador del requerimiento "alumno con mayor promedio". */
public class ControladorAlumnoMayorPromedio {

    private IFachada fachada;
    private VentanaAlumnoMayorPromedio ventana;

    public ControladorAlumnoMayorPromedio(IFachada fachada, VentanaAlumnoMayorPromedio ventana) {
        this.fachada = fachada;
        this.ventana = ventana;
    }

    public void alumnoMayorPromedio() {
        try {
            ventana.mostrarAlumno(fachada.alumnoMayorPromedio());
        } catch (NotasException e) {
            ventana.limpiarResultado();
            ventana.mostrarError(e.getMessage());
        } catch (PersistenciaException e) {
            ventana.limpiarResultado();
            ventana.mostrarError("Error de persistencia: " + e.getMessage());
        } catch (java.rmi.RemoteException e) {
            ventana.limpiarResultado();
            ventana.mostrarError("Error de comunicacion con el servidor: " + e.getMessage());
        }
    }
}
