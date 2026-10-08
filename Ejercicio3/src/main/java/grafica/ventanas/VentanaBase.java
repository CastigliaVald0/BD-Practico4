package grafica.ventanas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Clase base de las ventanas: concentra lo comun a todas (armado del
 * formulario y dialogos de mensaje/error) para que cada Vista concreta
 * se ocupe solo de sus campos y de delegar en su Controlador.
 */
public abstract class VentanaBase extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel formulario;
    private int fila;

    protected VentanaBase(String titulo) {
        super(titulo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
    }

    protected JPanel getFormulario() {
        return formulario;
    }

    /** Agrega una fila "etiqueta + campo de texto" al formulario. */
    protected JTextField agregarCampo(String etiqueta) {
        JTextField campo = new JTextField(16);
        agregarFila(etiqueta, campo);
        return campo;
    }

    /** Agrega una fila "etiqueta + componente" al formulario. */
    protected void agregarFila(String etiqueta, Component componente) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridy = fila;
        c.gridx = 0;
        c.anchor = GridBagConstraints.LINE_END;
        formulario.add(new JLabel(etiqueta), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        formulario.add(componente, c);

        fila++;
    }

    /** Agrega un componente que ocupa el ancho completo del formulario. */
    protected void agregarFilaCompleta(Component componente) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridy = fila;
        c.gridx = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        formulario.add(componente, c);
        fila++;
    }

    /** Agrega un componente que ocupa el ancho completo y se estira en alto (tablas). */
    protected void agregarFilaExpandible(Component componente) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridy = fila;
        c.gridx = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        formulario.add(componente, c);
        fila++;
    }

    protected void terminarVentana() {
        add(formulario);
        pack();
        setMinimumSize(new Dimension(Math.max(320, getWidth()), getHeight()));
        setLocationRelativeTo(null);
    }

    public void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(this, msj, getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String msj) {
        JOptionPane.showMessageDialog(this, msj, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
