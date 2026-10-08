package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import grafica.controladores.ControladorAlumnoMayorPromedio;
import logicaPersistencia.IFachada;
import logicaPersistencia.valueObjects.VOAlumnoProm;

/** Vista del requerimiento "alumno con mayor promedio". */
public class VentanaAlumnoMayorPromedio extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtPromedio;
    private ControladorAlumnoMayorPromedio controlador;

    public VentanaAlumnoMayorPromedio(IFachada fachada) {
        super("Alumno con mayor promedio");
        this.controlador = new ControladorAlumnoMayorPromedio(fachada, this);

        txtCedula = campoSoloLectura("Cedula:");
        txtNombre = campoSoloLectura("Nombre:");
        txtApellido = campoSoloLectura("Apellido:");
        txtPromedio = campoSoloLectura("Promedio:");

        JButton consultar = new JButton("Consultar");
        consultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.alumnoMayorPromedio();
            }
        });
        agregarFilaCompleta(consultar);

        terminarVentana();
        controlador.alumnoMayorPromedio();
    }

    private JTextField campoSoloLectura(String etiqueta) {
        JTextField campo = new JTextField(16);
        campo.setEditable(false);
        agregarFila(etiqueta, campo);
        return campo;
    }

    public void mostrarAlumno(VOAlumnoProm voA) {
        txtCedula.setText(String.valueOf(voA.getCedula()));
        txtNombre.setText(voA.getNombre());
        txtApellido.setText(voA.getApellido());
        txtPromedio.setText(String.format("%.2f", voA.getPromedio()));
    }

    public void limpiarResultado() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtPromedio.setText("");
    }
}
