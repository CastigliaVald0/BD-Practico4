package grafica;

import java.io.InputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import grafica.ventanas.VentanaPrincipal;
import logicaPersistencia.IFachada;

/**
 * Punto de entrada de la capa grafica (equipo Usuario).
 * Busca la Fachada en el registro RMI del servidor central y abre el menu principal.
 */
public class Principal {

    public static void main(String[] args) {
        try {
            Properties cfg = new Properties();
            try (InputStream in = Principal.class.getResourceAsStream("/config.properties")) {
                cfg.load(in);
            }
            String host = cfg.getProperty("rmi.host", "localhost");
            int puerto = Integer.parseInt(cfg.getProperty("rmi.puerto", "1099"));
            String nombre = cfg.getProperty("rmi.nombre", "Fachada");

            Registry registry = LocateRegistry.getRegistry(host, puerto);
            final IFachada fachada = (IFachada) registry.lookup(nombre);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception ignorada) {
                        // se usa el look and feel por defecto
                    }
                    new VentanaPrincipal(fachada).setVisible(true);
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar con el servidor:\n" + e.getMessage()
                    + "\n\nVerifique que el servidor RMI este levantado.",
                    "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
    }
}
