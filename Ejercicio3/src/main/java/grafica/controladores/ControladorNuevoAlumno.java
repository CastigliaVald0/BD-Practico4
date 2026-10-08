package grafica.controladores;

import grafica.ventanas.VentanaNuevoAlumno;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.NotasException;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.VOAlumno;

/** Controlador del requerimiento "nuevo alumno". */
public class ControladorNuevoAlumno {

    private IFachada fachada;
    private VentanaNuevoAlumno ventana;

    public ControladorNuevoAlumno(IFachada fachada, VentanaNuevoAlumno ventana) {
        this.fachada = fachada;
        this.ventana = ventana;
    }

    public void nuevoAlumno(String cedula, String nombre, String apellido) {
        int ced;
        try {
            ced = Integer.parseInt(cedula.trim());
        } catch (NumberFormatException e) {
            ventana.mostrarError("La cedula debe ser un numero entero.");
            return;
        }
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty()) {
            ventana.mostrarError("El nombre y el apellido no pueden quedar vacios.");
            return;
        }

        try {
            fachada.nuevoAlumno(new VOAlumno(ced, nombre.trim(), apellido.trim()));
            ventana.mostrarMensaje("Alumno ingresado correctamente.");
            ventana.limpiar();
        } catch (NotasException e) {
            ventana.mostrarError(e.getMessage());
        } catch (PersistenciaException e) {
            ventana.mostrarError("Error de persistencia: " + e.getMessage());
        } catch (java.rmi.RemoteException e) {
            ventana.mostrarError("Error de comunicacion con el servidor: " + e.getMessage());
        }
    }
}
