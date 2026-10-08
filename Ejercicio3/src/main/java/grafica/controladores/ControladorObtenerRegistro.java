package grafica.controladores;

import grafica.ventanas.VentanaObtenerRegistro;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.NotasException;
import logicaPersistencia.excepciones.PersistenciaException;

/** Controlador del requerimiento "obtener registro". */
public class ControladorObtenerRegistro {

    private IFachada fachada;
    private VentanaObtenerRegistro ventana;

    public ControladorObtenerRegistro(IFachada fachada, VentanaObtenerRegistro ventana) {
        this.fachada = fachada;
        this.ventana = ventana;
    }

    public void obtenerRegistro(String cedula, String numero) {
        int ced;
        int num;
        try {
            ced = Integer.parseInt(cedula.trim());
        } catch (NumberFormatException e) {
            ventana.mostrarError("La cedula debe ser un numero entero.");
            return;
        }
        try {
            num = Integer.parseInt(numero.trim());
        } catch (NumberFormatException e) {
            ventana.mostrarError("El numero de registro debe ser un numero entero.");
            return;
        }

        try {
            ventana.mostrarRegistro(fachada.obtenerRegistro(ced, num));
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
