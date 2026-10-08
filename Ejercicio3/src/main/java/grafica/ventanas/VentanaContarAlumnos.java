package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import grafica.controladores.ControladorContarAlumnos;
import logicaPersistencia.IFachada;

/**
 * Vista del requerimiento "contar alumnos": cuantos alumnos cursaron una
 * actividad y obtuvieron en esa cursada una nota determinada.
 */
public class VentanaContarAlumnos extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private JTextField txtActividad;
    private JTextField txtNota;
    private JTextField txtCantidad;
    private ControladorContarAlumnos controlador;

    public VentanaContarAlumnos(IFachada fachada) {
        super("Contar alumnos");
        this.controlador = new ControladorContarAlumnos(fachada, this);

        txtActividad = agregarCampo("Actividad:");
        txtNota = agregarCampo("Nota:");

        JButton contar = new JButton("Contar");
        contar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.contarAlumnos(txtActividad.getText(), txtNota.getText());
            }
        });
        agregarFilaCompleta(contar);

        txtCantidad = new JTextField(16);
        txtCantidad.setEditable(false);
        agregarFila("Cantidad de alumnos:", txtCantidad);

        terminarVentana();
    }

    public void mostrarCantidad(int cantidad) {
        txtCantidad.setText(String.valueOf(cantidad));
    }

    public void limpiarResultado() {
        txtCantidad.setText("");
    }
}
