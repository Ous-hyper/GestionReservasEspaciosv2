# Gestión de Reservas de Espacios

Aplicación de escritorio (JavaFX) para gestionar las reservas de espacios de un centro educativo: departamentos, profesores, espacios y reservas.

Esta versión del proyecto guarda y recupera los datos mediante **ficheros binarios (`.dat`)**, usando `ObjectOutputStream` y `ObjectInputStream`.

## Tecnologías

- Java (proyecto Maven)
- JavaFX 26 (interfaz gráfica)
- Gson 2.11.0 (persistencia en JSON)

## Estructura del proyecto

```
GestionReservasEspacios
├── data
│   ├── csv/    departamento.csv, espacios.csv, profesores.csv, reserva.csv
│   ├── dat/    departamento.dat, espacios.dat, profesores.dat, reserva.dat
│   └── json/   departamento.json, espacios.json, profesores.json, reserva.json
├── src/main/java
│   ├── modelo/     Departamento, Profesor, Espacio, Reserva, EstadoEspacio, EstadoReserva
│   ├── dao/        Interfaces: DepartamentoDAO, ProfesorDAO, EspacioDAO, ReservaDAO
│   │   ├── fcsv/   Implementaciones para CSV
│   │   ├── fdat/   Implementaciones para ficheros binarios (.dat)
│   │   └── fjson/  Implementaciones para JSON
│   ├── servicio/   CentroEducativo (lógica de negocio y reglas)
│   └── vista/      MainFX (interfaz JavaFX)
└── pom.xml
```

## Ejecución

Desde la raíz del proyecto:

```bash
mvn clean javafx:run
```

También se puede ejecutar la clase `vista.MainFX` desde el IDE (IntelliJ IDEA, Eclipse...).

> Los ficheros se leen y escriben con rutas relativas (`data/dat/...`), por lo que la aplicación debe ejecutarse desde la raíz del proyecto.

## Persistencia con ficheros `.dat`

Cada entidad se guarda en su propio fichero dentro de `data/dat/`:

| Entidad | Fichero | Clase DAO |
|---|---|---|
| Departamento | `departamento.dat` | `DepartamentoDAOFB` |
| Espacio | `espacios.dat` | `EspacioDAOFB` |
| Profesor | `profesores.dat` | `ProfesorDAOFB` |
| Reserva | `reserva.dat` | `ReservaDAOFB` |

- Al arrancar, `CentroEducativo` lee las cuatro listas a través de los DAO.
- Si un fichero no existe, se parte de una lista vacía.
- El menú **Archivo → Guardar datos binarios (.dat)** vuelca las cuatro listas completas, sustituyendo el contenido anterior.
- Las clases `Departamento`, `Profesor`, `Espacio` y `Reserva` implementan `Serializable`, requisito de la serialización binaria.
- Los cambios hechos en la aplicación solo viven en memoria hasta que se pulsa **Guardar**. Si se cierra sin guardar, al volver a abrir se recuperan los datos del último guardado.

### Cambiar la fuente de datos

`MainFX` incluye las tres variantes (CSV, DAT y JSON). Solo una está activa y las demás están comentadas. Para cambiar de fuente hay que activar el grupo correspondiente en cinco puntos: la creación de `CentroEducativo`, el `catch` de carga, el título de la ventana, el elemento de menú *Guardar* y el método `guardarDatos()`.

## Reglas de negocio

Las reglas se validan en `CentroEducativo` y, si se incumplen, la interfaz muestra el mensaje de error en un cuadro de diálogo.

| # | Regla |
|---|---|
| 1 | No pueden existir dos objetos con el mismo identificador dentro de una misma entidad (sin distinguir mayúsculas de minúsculas: `E001` y `e001` se consideran iguales). |
| 2 | Un profesor debe pertenecer a un departamento existente. |
| 3 | No se puede reservar un espacio que no esté disponible. |
| 4 | El número de asistentes no puede superar la capacidad del espacio. |
| 5 | La hora de finalización debe ser posterior a la hora de inicio. |
| 6 | No pueden existir dos reservas del mismo espacio que se solapen en fecha y horario. |
| 7 | Un profesor no puede tener dos reservas simultáneas, ni siquiera en espacios diferentes. |
| 8 | Las reservas consecutivas están permitidas (por ejemplo, 10:00-11:00 y 11:00-12:00). |

Notas:

- Las reservas **canceladas** no bloquean el espacio ni al profesor.
- Fecha con formato `yyyy-MM-dd` y horas con formato `HH:mm` (por ejemplo `09:30`).
- No se puede eliminar un departamento con profesores asignados, ni un profesor con reservas asociadas.

## Pruebas manuales

| Prueba | Resultado esperado |
|---|---|
| Crear una reserva en un espacio no disponible | Error: el espacio no está disponible |
| Crear una reserva con más asistentes que la capacidad | Error: se supera la capacidad |
| Crear una reserva con hora de fin igual o anterior a la de inicio | Error: la hora de fin debe ser posterior |
| Crear una reserva que se solape con otra del mismo espacio | Error: el espacio ya está reservado |
| Crear una reserva que se solape con otra del mismo profesor en otro espacio | Error: el profesor ya tiene otra reserva |
| Crear una reserva consecutiva (11:00-12:00 tras 10:00-11:00) | Se crea correctamente |
| Crear un espacio con un código ya existente (`e001` si existe `E001`) | Error: ya existe un espacio con ese código |
| Crear un profesor con un departamento inexistente | Error: debe pertenecer a un departamento existente |
| Guardar, cerrar y volver a abrir la aplicación | Los datos se conservan |
| Cerrar sin guardar tras modificar datos | Se recuperan los datos del último guardado |

## Autores
 - Oussama Rajil