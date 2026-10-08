package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import grafica.controladores.ControladorNuevoAlumno;
import logicaPersistencia.IFachada;

/** Vista del requerimiento "nuevo alumno". */
public class VentanaNuevoAlumno extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private ControladorNuevoAlumno controlador;

    public VentanaNuevoAlumno(IFachada fachada) {
        super("Nuevo alumno");
        this.controlador = new ControladorNuevoAlumno(fachada, this);

        txtCedula = agregarCampo("Cedula:");
        txtNombre = agregarCampo("Nombre:");
        txtApellido = agregarCampo("Apellido:");

        JButton aceptar = new JButton("Ingresar alumno");
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.nuevoAlumno(txtCedula.getText(), txtNombre.getText(), txtApellido.getText());
            }
        });
        agregarFilaCompleta(aceptar);

        terminarVentana();
    }

    public void limpiar() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.requestFocusInWindow();
    }
}
