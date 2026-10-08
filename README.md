# BD3 — Práctico 4 (Ejercicios 2 y 3)

Proyecto Gradle multi-módulo con un subproyecto por ejercicio.

```
Practico4/
├── Ejercicio2/                  Main que crea la BD "Escuela" y carga los alumnos
│   └── src/main/java/ejercicio2/Main.java
├── Ejercicio3/                  Aplicación en 2 capas, con RMI y MVC
│   └── src/main/java/
│       ├── logicaPersistencia/  IFachada, Fachada
│       │   ├── excepciones/     NotasException, AlumnoException,
│       │   │                    RegistroException, PersistenciaException
│       │   ├── valueObjects/    VOAlumno, VOAlumnoProm, VORegistro,
│       │   │                    VORegistroListado, VOActividad
│       │   └── accesoBD/        Consultas, AccesoBD
│       ├── grafica/             Principal (arranque del cliente)
│       │   ├── ventanas/        Vistas (Swing)
│       │   └── controladores/   Controladores (MVC)
│       └── servidor/            ServidorRMI (publica la Fachada)
├── gradlew / gradlew.bat        Gradle wrapper (no hace falta instalar Gradle)
└── .vscode/                     settings, launch.json y extensiones recomendadas
```

## Requisitos

- JDK 21 (ya instalado).
- MySQL corriendo en `localhost:3306`.
  En esta máquina el servicio se llama **MySQL97** y está **detenido**; hay que
  arrancarlo desde una consola **como administrador**:

  ```powershell
  Start-Service MySQL97
  ```

- Extensiones de VS Code: ya están instaladas (`vscjava.vscode-java-pack` y
  `vscjava.vscode-gradle`). También quedaron listadas en `.vscode/extensions.json`
  como recomendadas del workspace.

## Configuración

Las credenciales están en el `config.properties` de cada ejercicio:

- `Ejercicio2/src/main/resources/config.properties` — la URL **no** incluye la base,
  porque ese programa es el que la crea.
- `Ejercicio3/src/main/resources/config.properties` — apunta a `Escuela` e incluye
  además host, puerto y nombre del registro RMI.

Ajustar `user` y `password` según tu instalación (por defecto `root` / `root`).

## Cómo ejecutar

### Ejercicio 2 — crear la base y cargar los alumnos

```powershell
.\gradlew :Ejercicio2:run
```

Crea la base `Escuela` con:

```sql
Alumnos  (cedula PK, nombre, apellido)
Registros(numero, cedAlu, actividad, nota,
          PRIMARY KEY (cedAlu, numero),
          FOREIGN KEY (cedAlu) REFERENCES Alumnos(cedula))
```

La PK compuesta `(cedAlu, numero)` es la que impone la regla del enunciado:
puede repetirse el `numero` entre alumnos distintos, pero no dentro del mismo alumno.

Después inserta los 5 alumnos por defecto. Es idempotente: si se corre dos veces,
avisa cuáles ya existían en lugar de fallar.

### Ejercicio 3 — aplicación en 2 capas

Son dos procesos. En una terminal, el servidor central:

```powershell
.\gradlew :Ejercicio3:runServidor
```

En otra terminal, el cliente con la interfaz gráfica:

```powershell
.\gradlew :Ejercicio3:runCliente
```

Desde VS Code también se puede usar el panel **Run and Debug**, que ya tiene las
tres configuraciones cargadas, más el compound *"Ejercicio 3 — Servidor + Cliente"*
que levanta ambos de una.

Para correrlo en una LAN real (servidor y cliente en equipos distintos), arrancar
el servidor con la IP visible desde la red:

```powershell
java -Djava.rmi.server.hostname=<IP_DEL_SERVIDOR> -cp ... servidor.ServidorRMI
```

y poner esa misma IP en `rmi.host` del `config.properties` del cliente.

## Nextcloud y los directorios de compilación

La carpeta está dentro de `Nextcloud3`, que sincroniza y convierte archivos en
*placeholders*. Eso hacía fallar la compilación en la segunda corrida:

```
Cannot snapshot ...\build\resources\main\config.properties: not a regular file
```

Por eso el `build.gradle` raíz redirige la salida de compilación fuera del árbol
sincronizado:

```
C:\Users\valdo\.gradle-builds\Practico4\{Ejercicio2,Ejercicio3}
```

Queda como efecto secundario que los binarios ya no se sincronizan a la nube.
Si en algún momento vuelve a aparecer un error parecido apuntando a `.gradle\`,
conviene agregar `.gradle` y `build` a la lista de exclusiones de Nextcloud
(*Configuración → Avanzado → Editar lista de ignorados*).

## Notas de diseño

- **2 capas.** La capa gráfica (`grafica`) sólo conoce `IFachada` y los value
  objects; nunca toca JDBC. Toda la lógica y la persistencia quedan del lado de
  `logicaPersistencia`.
- **Excepciones.** `AlumnoException` y `RegistroException` heredan de
  `NotasException`, que es la que figura en la firma de `IFachada`. Así un
  controlador puede capturar el caso general o distinguir el específico.
  `PersistenciaException` va aparte porque representa un error de la BD, no un
  chequeo de negocio.
- **Sin transacciones.** Tal como pide el enunciado, cada requerimiento abre una
  conexión aislada con `DriverManager`, la usa y la cierra en el `finally`.
  El Pool de Conexiones y el `commit`/`rollback` corresponden al Ejercicio 4.
- **`alumnoMayorPromedio`.** El único chequeo que pide el enunciado es que exista
  al menos un alumno, así que la consulta usa `LEFT JOIN`: un alumno sin notas
  queda último al ordenar `DESC` (en MySQL los `NULL` van al final), y sólo se
  devuelve si nadie tiene notas registradas.
