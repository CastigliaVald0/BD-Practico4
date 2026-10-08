package grafica.controladores;

import grafica.ventanas.VentanaActividadMasCursada;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.NotasException;
import logicaPersistencia.excepciones.PersistenciaException;

/** Controlador del requerimiento "actividad mas cursada por un alumno". */
public class ControladorActividadMasCursada {

    private IFachada fachada;
    private VentanaActividadMasCursada ventana;

    public ControladorActividadMasCursada(IFachada fachada, VentanaActividadMasCursada ventana) {
        this.fachada = fachada;
        this.ventana = ventana;
    }

    public void actividadMasCursada(String cedula) {
        int ced;
        try {
            ced = Integer.parseInt(cedula.trim());
        } catch (NumberFormatException e) {
            ventana.mostrarError("La cedula debe ser un numero entero.");
            return;
        }

        try {
            ventana.mostrarActividad(fachada.actividadMasCursada(ced));
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
