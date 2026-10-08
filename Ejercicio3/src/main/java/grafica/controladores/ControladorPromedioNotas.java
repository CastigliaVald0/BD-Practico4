package grafica.controladores;

import grafica.ventanas.VentanaPromedioNotas;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.NotasException;
import logicaPersistencia.excepciones.PersistenciaException;

/** Controlador del requerimiento "promedio de notas de un alumno". */
public class ControladorPromedioNotas {

    private IFachada fachada;
    private VentanaPromedioNotas ventana;

    public ControladorPromedioNotas(IFachada fachada, VentanaPromedioNotas ventana) {
        this.fachada = fachada;
        this.ventana = ventana;
    }

    public void promedioNotas(String cedula) {
        int ced;
        try {
            ced = Integer.parseInt(cedula.trim());
        } catch (NumberFormatException e) {
            ventana.mostrarError("La cedula debe ser un numero entero.");
            return;
        }

        try {
            ventana.mostrarPromedio(fachada.promedioNotas(ced));
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
