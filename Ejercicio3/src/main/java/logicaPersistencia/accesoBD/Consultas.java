package logicaPersistencia.accesoBD;

/**
 * Textos de todas las sentencias SQL que la aplicacion necesita ejecutar.
 * No ejecuta nada: solo centraliza el SQL en un unico lugar.
 */
public class Consultas {

    public String existeAlumno() {
        return """
               SELECT cedula
               FROM Alumnos
               WHERE cedula = ?
               """;
    }

    public String insertarAlumno() {
        return """
               INSERT INTO Alumnos (cedula, nombre, apellido)
               VALUES (?, ?, ?)
               """;
    }

    public String listarAlumnos() {
        return """
               SELECT cedula, nombre, apellido
               FROM Alumnos
               ORDER BY cedula
               """;
    }

    public String cantidadAlumnos() {
        return """
               SELECT COUNT(*) AS cantidad
               FROM Alumnos
               """;
    }

    /** Numero a asignar al proximo registro de ese alumno (1 si es el primero). */
    public String siguienteNum() {
        return """
               SELECT IFNULL(MAX(numero), 0) + 1 AS siguiente
               FROM Registros
               WHERE cedAlu = ?
               """;
    }

    public String insertarRegistro() {
        return """
               INSERT INTO Registros (numero, cedAlu, actividad, nota)
               VALUES (?, ?, ?, ?)
               """;
    }

    public String obtenerRegistro() {
        return """
               SELECT actividad, nota
               FROM Registros
               WHERE cedAlu = ?
                 AND numero = ?
               """;
    }

    public String listarRegistros() {
        return """
               SELECT numero, actividad, nota
               FROM Registros
               WHERE cedAlu = ?
               ORDER BY numero
               """;
    }

    public String promedioNotas() {
        return """
               SELECT AVG(nota) AS promedio
               FROM Registros
               WHERE cedAlu = ?
               """;
    }

    /**
     * Alumno con mayor promedio. Se usa LEFT JOIN para que el alumno siga
     * apareciendo aunque no tenga notas; en MySQL los NULL quedan ultimos
     * al ordenar DESC, asi que solo se devuelve uno sin notas si nadie tiene.
     */
    public String alumnoMayorPromedio() {
        return """
               SELECT a.cedula, a.nombre, a.apellido, AVG(r.nota) AS promedio
               FROM Alumnos a
               LEFT JOIN Registros r ON r.cedAlu = a.cedula
               GROUP BY a.cedula, a.nombre, a.apellido
               ORDER BY promedio DESC
               LIMIT 1
               """;
    }

    public String contarAlumnos() {
        return """
               SELECT COUNT(DISTINCT cedAlu) AS cantidad
               FROM Registros
               WHERE actividad = ?
                 AND nota = ?
               """;
    }

    public String actividadMasCursada() {
        return """
               SELECT actividad, COUNT(*) AS veces
               FROM Registros
               WHERE cedAlu = ?
               GROUP BY actividad
               ORDER BY veces DESC
               LIMIT 1
               """;
    }
}
