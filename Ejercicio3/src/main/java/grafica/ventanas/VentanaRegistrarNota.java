package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import grafica.controladores.ControladorRegistrarNota;
import logicaPersistencia.IFachada;

/** Vista del requerimiento "registrar nota". */
public class VentanaRegistrarNota extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private JTextField txtCedula;
    private JTextField txtActividad;
    private JTextField txtNota;
    private ControladorRegistrarNota controlador;

    public VentanaRegistrarNota(IFachada fachada) {
        super("Registrar nota");
        this.controlador = new ControladorRegistrarNota(fachada, this);

        txtCedula = agregarCampo("Cedula del alumno:");
        txtActividad = agregarCampo("Actividad:");
        txtNota = agregarCampo("Nota:");

        JButton aceptar = new JButton("Registrar nota");
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.registrarNota(txtCedula.getText(), txtActividad.getText(), txtNota.getText());
            }
        });
        agregarFilaCompleta(aceptar);

        terminarVentana();
    }

    public void limpiar() {
        txtActividad.setText("");
        txtNota.setText("");
        txtActividad.requestFocusInWindow();
    }
}
