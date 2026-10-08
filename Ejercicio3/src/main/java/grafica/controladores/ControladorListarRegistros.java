package grafica.controladores;

import grafica.ventanas.VentanaListarRegistros;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.NotasException;
import logicaPersistencia.excepciones.PersistenciaException;

/** Controlador del requerimiento "listar registros de un alumno". */
public class ControladorListarRegistros {

    private IFachada fachada;
    private VentanaListarRegistros ventana;

    public ControladorListarRegistros(IFachada fachada, VentanaListarRegistros ventana) {
        this.fachada = fachada;
        this.ventana = ventana;
    }

    public void listarRegistros(String cedula) {
        int ced;
        try {
            ced = Integer.parseInt(cedula.trim());
        } catch (NumberFormatException e) {
            ventana.mostrarError("La cedula debe ser un numero entero.");
            return;
        }

        try {
            ventana.mostrarRegistros(fachada.listarRegistros(ced));
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
