package grafica.ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import grafica.controladores.ControladorPrincipal;
import logicaPersistencia.IFachada;

/**
 * Menu principal de la aplicacion. Solo arma la vista y delega cada accion
 * en el ControladorPrincipal, que es quien abre la ventana correspondiente.
 */
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private ControladorPrincipal controlador;

    public VentanaPrincipal(IFachada fachada) {
        super("Notas - Menu principal");
        this.controlador = new ControladorPrincipal(fachada);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Registro de notas", SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 4, 12));
        add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(0, 1, 6, 6));
        botones.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));

        botones.add(boton("Nuevo alumno", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirNuevoAlumno(); }
        }));
        botones.add(boton("Registrar nota", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirRegistrarNota(); }
        }));
        botones.add(boton("Listar alumnos", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirListarAlumnos(); }
        }));
        botones.add(boton("Obtener registro", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirObtenerRegistro(); }
        }));
        botones.add(boton("Listar registros de un alumno", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirListarRegistros(); }
        }));
        botones.add(boton("Promedio de notas de un alumno", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirPromedioNotas(); }
        }));
        botones.add(boton("Alumno con mayor promedio", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirAlumnoMayorPromedio(); }
        }));
        botones.add(boton("Contar alumnos por actividad y nota", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirContarAlumnos(); }
        }));
        botones.add(boton("Actividad mas cursada por un alumno", new ActionListener() {
            public void actionPerformed(ActionEvent e) { controlador.abrirActividadMasCursada(); }
        }));

        add(botones, BorderLayout.CENTER);

        JButton salir = new JButton("Salir");
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { dispose(); System.exit(0); }
        });
        JPanel pie = new JPanel();
        pie.add(salir);
        add(pie, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton boton(String texto, ActionListener accion) {
        JButton b = new JButton(texto);
        b.addActionListener(accion);
        return b;
    }
}
