package servidor;

import java.io.InputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import logicaPersistencia.Fachada;
import logicaPersistencia.IFachada;

/**
 * Servidor central: crea la Fachada y la publica en el registro RMI para que
 * los equipos clientes puedan invocarla.
 */
public class ServidorRMI {

    public static void main(String[] args) {
        try {
            Properties cfg = new Properties();
            try (InputStream in = ServidorRMI.class.getResourceAsStream("/config.properties")) {
                cfg.load(in);
            }
            int puerto = Integer.parseInt(cfg.getProperty("rmi.puerto", "1099"));
            String nombre = cfg.getProperty("rmi.nombre", "Fachada");

            IFachada fachada = new Fachada();

            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(puerto);
                System.out.println("Registro RMI creado en el puerto " + puerto);
            } catch (Exception e) {
                registry = LocateRegistry.getRegistry(puerto);
                System.out.println("Usando el registro RMI ya existente en el puerto " + puerto);
            }

            registry.rebind(nombre, fachada);

            System.out.println("Fachada publicada como \"" + nombre + "\".");
            System.out.println("Servidor listo. Ctrl+C para detenerlo.");

        } catch (Exception e) {
            System.err.println("No se pudo levantar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
