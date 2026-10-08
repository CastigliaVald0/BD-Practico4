package ejercicio2;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Practico 4 - Ejercicio 2.
 *
 * Crea la base de datos "Escuela" con el esquema pedido:
 *
 *   Alumnos  (cedula INT, nombre VARCHAR(45), apellido VARCHAR(45))
 *   Registros(numero INT, cedAlu INT, actividad VARCHAR(45), nota INT)
 *
 * - cedAlu en Registros referencia a cedula en Alumnos.
 * - Puede haber registros con igual numero para alumnos diferentes, pero no
 *   numeros repetidos para un mismo alumno  =>  PRIMARY KEY (cedAlu, numero).
 *
 * Luego carga los cinco alumnos por defecto.
 */
public class Main {

    private static final String[][] ALUMNOS = {
        { "12345678", "Matilda", "Wormwood" },
        { "25554449", "Charlie", "Brown"    },
        { "39876542", "Dora",    "Marquez"  },
        { "51112223", "Daniel",  "Mitchell" },
        { "66666666", "Merlina", "Addams"   }
    };

    private static final String CREAR_BASE =
        "CREATE DATABASE IF NOT EXISTS Escuela "
      + "DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";

    private static final String CREAR_ALUMNOS = """
        CREATE TABLE IF NOT EXISTS Escuela.Alumnos (
            cedula   INTEGER     NOT NULL,
            nombre   VARCHAR(45) NOT NULL,
            apellido VARCHAR(45) NOT NULL,
            PRIMARY KEY (cedula)
        ) ENGINE=InnoDB
        """;

    private static final String CREAR_REGISTROS = """
        CREATE TABLE IF NOT EXISTS Escuela.Registros (
            numero    INTEGER     NOT NULL,
            cedAlu    INTEGER     NOT NULL,
            actividad VARCHAR(45) NOT NULL,
            nota      INTEGER     NOT NULL,
            PRIMARY KEY (cedAlu, numero),
            FOREIGN KEY (cedAlu) REFERENCES Escuela.Alumnos (cedula)
        ) ENGINE=InnoDB
        """;

    private static final String INSERTAR_ALUMNO =
        "INSERT INTO Escuela.Alumnos (cedula, nombre, apellido) VALUES (?, ?, ?)";

    public static void main(String[] args) {

        Properties cfg = cargarConfiguracion();
        String driver   = cfg.getProperty("driver");
        String url      = cfg.getProperty("url");
        String user     = cfg.getProperty("user");
        String password = cfg.getProperty("password");

        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);

            /* --- creo la base de datos --- */
            Statement stmt = con.createStatement();
            stmt.executeUpdate(CREAR_BASE);

            /* --- creo la tabla Alumnos --- */
            stmt.executeUpdate(CREAR_ALUMNOS);

            /* --- creo la tabla Registros --- */
            stmt.executeUpdate(CREAR_REGISTROS);
            stmt.close();

            System.out.println("Base de datos y tablas creadas.");

            /* --- cargo los alumnos por defecto --- */
            PreparedStatement pstmt = con.prepareStatement(INSERTAR_ALUMNO);
            int cargados = 0;
            for (String[] a : ALUMNOS) {
                pstmt.setInt(1, Integer.parseInt(a[0]));
                pstmt.setString(2, a[1]);
                pstmt.setString(3, a[2]);
                try {
                    pstmt.executeUpdate();
                    cargados++;
                } catch (SQLException e) {
                    // 1062 = Duplicate entry: el alumno ya estaba cargado de una corrida anterior.
                    if (e.getErrorCode() == 1062) {
                        System.out.println("  (ya existia el alumno " + a[0] + ", se omite)");
                    } else {
                        throw e;
                    }
                }
            }
            pstmt.close();

            System.out.println("Datos cargados con exito (" + cargados + " alumnos nuevos).");

        } catch (ClassNotFoundException e) {
            System.err.println("No se encontro el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de acceso a la base de datos: " + e.getMessage());
        } finally {
            cerrarConexion(con);
        }
    }

    private static Properties cargarConfiguracion() {
        Properties props = new Properties();
        try (InputStream in = Main.class.getResourceAsStream("/config.properties")) {
            if (in == null) {
                throw new RuntimeException("No se encontro el archivo config.properties");
            }
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer config.properties: " + e.getMessage(), e);
        }
        return props;
    }

    private static void cerrarConexion(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            // nada para hacer
        }
    }
}
