package grafica.controladores;

import grafica.ventanas.VentanaListarAlumnos;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.PersistenciaException;

/** Controlador del requerimiento "listar alumnos". */
public class ControladorListarAlumnos {

    private IFachada fachada;
    private VentanaListarAlumnos ventana;

    public ControladorListarAlumnos(IFachada fachada, VentanaListarAlumnos ventana) {
        this.fachada = fachada;
        this.ventana = ventana;
    }

    public void listarAlumnos() {
        try {
            ventana.mostrarAlumnos(fachada.listarAlumnos());
        } catch (PersistenciaException e) {
            ventana.mostrarError("Error de persistencia: " + e.getMessage());
        } catch (java.rmi.RemoteException e) {
            ventana.mostrarError("Error de comunicacion con el servidor: " + e.getMessage());
        }
    }
}
