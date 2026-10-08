package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import grafica.controladores.ControladorPromedioNotas;
import logicaPersistencia.IFachada;

/** Vista del requerimiento "promedio de notas de un alumno". */
public class VentanaPromedioNotas extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private JTextField txtCedula;
    private JTextField txtPromedio;
    private ControladorPromedioNotas controlador;

    public VentanaPromedioNotas(IFachada fachada) {
        super("Promedio de notas");
        this.controlador = new ControladorPromedioNotas(fachada, this);

        txtCedula = agregarCampo("Cedula del alumno:");

        JButton calcular = new JButton("Calcular promedio");
        calcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.promedioNotas(txtCedula.getText());
            }
        });
        agregarFilaCompleta(calcular);

        txtPromedio = new JTextField(16);
        txtPromedio.setEditable(false);
        agregarFila("Promedio:", txtPromedio);

        terminarVentana();
    }

    public void mostrarPromedio(double promedio) {
        txtPromedio.setText(String.format("%.2f", promedio));
    }

    public void limpiarResultado() {
        txtPromedio.setText("");
    }
}
