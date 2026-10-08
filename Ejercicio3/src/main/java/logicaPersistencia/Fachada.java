package logicaPersistencia;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import logicaPersistencia.accesoBD.AccesoBD;
import logicaPersistencia.excepciones.AlumnoException;
import logicaPersistencia.excepciones.NotasException;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.excepciones.RegistroException;
import logicaPersistencia.valueObjects.VOActividad;
import logicaPersistencia.valueObjects.VOAlumno;
import logicaPersistencia.valueObjects.VOAlumnoProm;
import logicaPersistencia.valueObjects.VORegistro;
import logicaPersistencia.valueObjects.VORegistroListado;

/**
 * Fachada de acceso a la capa de logica y persistencia.
 *
 * Cada requerimiento abre su propia conexion aislada, la usa y la cierra.
 * (El manejo de transacciones y el Pool de Conexiones se agregan en el Ejercicio 4.)
 */
public class Fachada extends UnicastRemoteObject implements IFachada {

    private static final long serialVersionUID = 1L;

    /* --- atributos para conexion BD --- */
    private String driver;
    private String url;
    private String user;
    private String password;

    private AccesoBD abd;

    public Fachada() throws RemoteException, PersistenciaException {
        super();
        cargarConfiguracion();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new PersistenciaException("No se encontro el driver JDBC: " + driver);
        }
        abd = new AccesoBD();
    }

    // ------------------------------------------------------------------
    // Requerimientos
    // ------------------------------------------------------------------

    @Override
    public void nuevoAlumno(VOAlumno voA)
            throws NotasException, PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            if (abd.existeAlumno(con, voA.getCedula())) {
                throw new AlumnoException("Ya existe un alumno con la cedula " + voA.getCedula());
            }
            abd.insertarAlumno(con, voA.getCedula(), voA.getNombre(), voA.getApellido());
        } finally {
            cerrarConexion(con);
        }
    }

    @Override
    public void registrarNota(int cedA, VORegistro voR)
            throws NotasException, PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            chequearAlumno(con, cedA);
            int numero = abd.siguienteNum(con, cedA);
            abd.insertarRegistro(con, numero, cedA, voR.getActividad(), voR.getNota());
        } finally {
            cerrarConexion(con);
        }
    }

    @Override
    public List<VOAlumno> listarAlumnos()
            throws PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            return abd.listarAlumnos(con);
        } finally {
            cerrarConexion(con);
        }
    }

    @Override
    public VORegistro obtenerRegistro(int cedA, int numR)
            throws NotasException, PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            chequearAlumno(con, cedA);
            VORegistro registro = abd.obtenerRegistro(con, cedA, numR);
            if (registro == null) {
                throw new RegistroException(
                        "El alumno " + cedA + " no tiene un registro con numero " + numR);
            }
            return registro;
        } finally {
            cerrarConexion(con);
        }
    }

    @Override
    public List<VORegistroListado> listarRegistros(int cedA)
            throws NotasException, PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            chequearAlumno(con, cedA);
            return abd.listarRegistros(con, cedA);
        } finally {
            cerrarConexion(con);
        }
    }

    @Override
    public double promedioNotas(int cedA)
            throws NotasException, PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            chequearAlumno(con, cedA);
            return abd.promedioNotas(con, cedA);
        } finally {
            cerrarConexion(con);
        }
    }

    @Override
    public VOAlumnoProm alumnoMayorPromedio()
            throws NotasException, PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            if (abd.cantidadAlumnos(con) == 0) {
                throw new AlumnoException("No hay alumnos registrados");
            }
            return abd.alumnoMayorPromedio(con);
        } finally {
            cerrarConexion(con);
        }
    }

    @Override
    public int contarAlumnos(String act, int nota)
            throws PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            return abd.contarAlumnos(con, act, nota);
        } finally {
            cerrarConexion(con);
        }
    }

    @Override
    public VOActividad actividadMasCursada(int cedA)
            throws NotasException, PersistenciaException, RemoteException {
        Connection con = null;
        try {
            con = abrirConexion();
            chequearAlumno(con, cedA);
            VOActividad voAct = abd.actividadMasCursada(con, cedA);
            if (voAct == null) {
                throw new RegistroException(
                        "El alumno " + cedA + " no tiene ninguna actividad registrada");
            }
            return voAct;
        } finally {
            cerrarConexion(con);
        }
    }

    // ------------------------------------------------------------------
    // Metodos auxiliares
    // ------------------------------------------------------------------

    private void chequearAlumno(Connection con, int cedA)
            throws AlumnoException, PersistenciaException {
        if (!abd.existeAlumno(con, cedA)) {
            throw new AlumnoException("No existe un alumno con la cedula " + cedA);
        }
    }

    private void cargarConfiguracion() throws PersistenciaException {
        Properties props = new Properties();
        try (InputStream in = Fachada.class.getResourceAsStream("/config.properties")) {
            if (in == null) {
                throw new PersistenciaException("No se encontro el archivo config.properties");
            }
            props.load(in);
        } catch (java.io.IOException e) {
            throw new PersistenciaException("No se pudo leer config.properties: " + e.getMessage());
        }
        driver = props.getProperty("driver");
        url = props.getProperty("url");
        user = props.getProperty("user");
        password = props.getProperty("password");
    }

    private Connection abrirConexion() throws PersistenciaException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo conectar con la base de datos.");
        }
    }

    private void cerrarConexion(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            // nada para hacer
        }
    }
}
