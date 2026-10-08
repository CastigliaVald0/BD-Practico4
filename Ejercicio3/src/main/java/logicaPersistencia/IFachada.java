package logicaPersistencia;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logicaPersistencia.excepciones.NotasException;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.VOActividad;
import logicaPersistencia.valueObjects.VOAlumno;
import logicaPersistencia.valueObjects.VOAlumnoProm;
import logicaPersistencia.valueObjects.VORegistro;
import logicaPersistencia.valueObjects.VORegistroListado;

/**
 * Interfaz remota de la Fachada. Es el limite entre la capa grafica y la
 * capa de logica/persistencia: los controladores solo conocen esta interfaz.
 */
public interface IFachada extends Remote {

    void nuevoAlumno(VOAlumno voA)
            throws NotasException, PersistenciaException, RemoteException;

    void registrarNota(int cedA, VORegistro voR)
            throws NotasException, PersistenciaException, RemoteException;

    List<VOAlumno> listarAlumnos()
            throws PersistenciaException, RemoteException;

    VORegistro obtenerRegistro(int cedA, int numR)
            throws NotasException, PersistenciaException, RemoteException;

    List<VORegistroListado> listarRegistros(int cedA)
            throws NotasException, PersistenciaException, RemoteException;

    double promedioNotas(int cedA)
            throws NotasException, PersistenciaException, RemoteException;

    VOAlumnoProm alumnoMayorPromedio()
            throws NotasException, PersistenciaException, RemoteException;

    int contarAlumnos(String act, int nota)
            throws PersistenciaException, RemoteException;

    VOActividad actividadMasCursada(int cedA)
            throws NotasException, PersistenciaException, RemoteException;
}
