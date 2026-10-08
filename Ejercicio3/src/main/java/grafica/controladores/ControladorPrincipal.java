package grafica.controladores;

import grafica.ventanas.VentanaActividadMasCursada;
import grafica.ventanas.VentanaAlumnoMayorPromedio;
import grafica.ventanas.VentanaContarAlumnos;
import grafica.ventanas.VentanaListarAlumnos;
import grafica.ventanas.VentanaListarRegistros;
import grafica.ventanas.VentanaNuevoAlumno;
import grafica.ventanas.VentanaObtenerRegistro;
import grafica.ventanas.VentanaPromedioNotas;
import grafica.ventanas.VentanaRegistrarNota;
import logicaPersistencia.IFachada;

/**
 * Controlador del menu principal: abre la ventana de cada requerimiento,
 * pasandole la referencia remota a la Fachada.
 */
public class ControladorPrincipal {

    private IFachada fachada;

    public ControladorPrincipal(IFachada fachada) {
        this.fachada = fachada;
    }

    public void abrirNuevoAlumno() {
        new VentanaNuevoAlumno(fachada).setVisible(true);
    }

    public void abrirRegistrarNota() {
        new VentanaRegistrarNota(fachada).setVisible(true);
    }

    public void abrirListarAlumnos() {
        new VentanaListarAlumnos(fachada).setVisible(true);
    }

    public void abrirObtenerRegistro() {
        new VentanaObtenerRegistro(fachada).setVisible(true);
    }

    public void abrirListarRegistros() {
        new VentanaListarRegistros(fachada).setVisible(true);
    }

    public void abrirPromedioNotas() {
        new VentanaPromedioNotas(fachada).setVisible(true);
    }

    public void abrirAlumnoMayorPromedio() {
        new VentanaAlumnoMayorPromedio(fachada).setVisible(true);
    }

    public void abrirContarAlumnos() {
        new VentanaContarAlumnos(fachada).setVisible(true);
    }

    public void abrirActividadMasCursada() {
        new VentanaActividadMasCursada(fachada).setVisible(true);
    }
}
