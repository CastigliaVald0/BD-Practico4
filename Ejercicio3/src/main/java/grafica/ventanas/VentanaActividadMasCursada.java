package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import grafica.controladores.ControladorActividadMasCursada;
import logicaPersistencia.IFachada;
import logicaPersistencia.valueObjects.VOActividad;

/** Vista del requerimiento "actividad mas cursada por un alumno". */
public class VentanaActividadMasCursada extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private JTextField txtCedula;
    private JTextField txtActividad;
    private JTextField txtVeces;
    private ControladorActividadMasCursada controlador;

    public VentanaActividadMasCursada(IFachada fachada) {
        super("Actividad mas cursada");
        this.controlador = new ControladorActividadMasCursada(fachada, this);

        txtCedula = agregarCampo("Cedula del alumno:");

        JButton consultar = new JButton("Consultar");
        consultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.actividadMasCursada(txtCedula.getText());
            }
        });
        agregarFilaCompleta(consultar);

        txtActividad = new JTextField(16);
        txtActividad.setEditable(false);
        agregarFila("Actividad:", txtActividad);

        txtVeces = new JTextField(16);
        txtVeces.setEditable(false);
        agregarFila("Veces cursada:", txtVeces);

        terminarVentana();
    }

    public void mostrarActividad(VOActividad voAct) {
        txtActividad.setText(voAct.getActividad());
        txtVeces.setText(String.valueOf(voAct.getVeces()));
    }

    public void limpiarResultado() {
        txtActividad.setText("");
        txtVeces.setText("");
    }
}
