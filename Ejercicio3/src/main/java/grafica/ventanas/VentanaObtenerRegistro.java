package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import grafica.controladores.ControladorObtenerRegistro;
import logicaPersistencia.IFachada;
import logicaPersistencia.valueObjects.VORegistro;

/** Vista del requerimiento "obtener registro". */
public class VentanaObtenerRegistro extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private JTextField txtCedula;
    private JTextField txtNumero;
    private JTextField txtActividad;
    private JTextField txtNota;
    private ControladorObtenerRegistro controlador;

    public VentanaObtenerRegistro(IFachada fachada) {
        super("Obtener registro");
        this.controlador = new ControladorObtenerRegistro(fachada, this);

        txtCedula = agregarCampo("Cedula del alumno:");
        txtNumero = agregarCampo("Numero de registro:");

        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.obtenerRegistro(txtCedula.getText(), txtNumero.getText());
            }
        });
        agregarFilaCompleta(buscar);

        txtActividad = new JTextField(16);
        txtActividad.setEditable(false);
        agregarFila("Actividad:", txtActividad);

        txtNota = new JTextField(16);
        txtNota.setEditable(false);
        agregarFila("Nota:", txtNota);

        terminarVentana();
    }

    public void mostrarRegistro(VORegistro voR) {
        txtActividad.setText(voR.getActividad());
        txtNota.setText(String.valueOf(voR.getNota()));
    }

    public void limpiarResultado() {
        txtActividad.setText("");
        txtNota.setText("");
    }
}
