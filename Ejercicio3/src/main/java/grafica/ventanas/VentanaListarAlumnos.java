package grafica.ventanas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorListarAlumnos;
import logicaPersistencia.IFachada;
import logicaPersistencia.valueObjects.VOAlumno;

/** Vista del requerimiento "listar alumnos" (ordenados por cedula). */
public class VentanaListarAlumnos extends VentanaBase {

    private static final long serialVersionUID = 1L;

    private DefaultTableModel modelo;
    private ControladorListarAlumnos controlador;

    public VentanaListarAlumnos(IFachada fachada) {
        super("Listado de alumnos");
        this.controlador = new ControladorListarAlumnos(fachada, this);

        modelo = new DefaultTableModel(new String[] { "Cedula", "Nombre", "Apellido" }, 0) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(420, 240));
        agregarFilaExpandible(scroll);

        JButton actualizar = new JButton("Actualizar");
        actualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.listarAlumnos();
            }
        });
        agregarFilaCompleta(actualizar);

        terminarVentana();
        controlador.listarAlumnos();
    }

    public void mostrarAlumnos(List<VOAlumno> alumnos) {
        modelo.setRowCount(0);
        for (VOAlumno a : alumnos) {
            modelo.addRow(new Object[] { a.getCedula(), a.getNombre(), a.getApellido() });
        }
    }
}
