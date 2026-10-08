package grafica.ventanas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorListarRegistros;
import logicaPersistencia.IFachada;
import logicaPersistencia.valueObjects.VORegistroListado;

/** Vista del requerimiento "listar registros de un alumno". */
public class VentanaListarRegistros extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private JTextField txtCedula;
    private DefaultTableModel modelo;
    private ControladorListarRegistros controlador;

    public VentanaListarRegistros(IFachada fachada) {
        super("Registros de un alumno");
        this.controlador = new ControladorListarRegistros(fachada, this);

        txtCedula = agregarCampo("Cedula del alumno:");

        JButton listar = new JButton("Listar registros");
        listar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.listarRegistros(txtCedula.getText());
            }
        });
        agregarFilaCompleta(listar);

        modelo = new DefaultTableModel(new String[] { "Numero", "Actividad", "Nota" }, 0) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(420, 220));
        agregarFilaExpandible(scroll);

        terminarVentana();
    }

    public void mostrarRegistros(List<VORegistroListado> registros) {
        modelo.setRowCount(0);
        for (VORegistroListado r : registros) {
            modelo.addRow(new Object[] { r.getNumero(), r.getActividad(), r.getNota() });
        }
        if (registros.isEmpty()) {
            mostrarMensaje("El alumno no tiene registros de notas.");
        }
    }

    public void limpiarResultado() {
        modelo.setRowCount(0);
    }
}
