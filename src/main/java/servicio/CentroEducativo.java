package servicio;

import dao.DepartamentoDAO;
import dao.EspacioDAO;
import dao.ProfesorDAO;
import dao.ReservaDAO;
import dao.fcsv.DepartamentoDAOCSV;
import dao.fcsv.EspacioDAOCSV;
import dao.fcsv.ProfesorDAOCSV;
import dao.fcsv.ReservaDAOCSV;
import modelo.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CentroEducativo {
    private List<Departamento> listaDepartamentos;
    private List<Espacio> listaEspacios;
    private List<Profesor> listaProfesores;
    private List<Reserva> listaReservas;

    private DepartamentoDAO departamentoDAO;
    private EspacioDAO espacioDAO;
    private ProfesorDAO profesorDAO;
    private ReservaDAO reservaDAO;

    public CentroEducativo(DepartamentoDAO departamentoDAO, EspacioDAO espacioDAO, ProfesorDAO profesorDAO, ReservaDAO reservaDAO) throws IOException {
        listaDepartamentos=new ArrayList<>();
        listaEspacios=new ArrayList<>();
        listaProfesores=new ArrayList<>();
        listaReservas=new ArrayList<>();

        this.departamentoDAO = departamentoDAO;
        this.espacioDAO = espacioDAO;
        this.profesorDAO = profesorDAO;
        this.reservaDAO = reservaDAO;

        listaDepartamentos = departamentoDAO.leerFichero();
        listaEspacios = espacioDAO.leerFichero();
        listaProfesores = profesorDAO.leerFichero();
        listaReservas = reservaDAO.leerFichero();
    }

    public List getDepartamentos() {
        return listaDepartamentos;
    }

    public List getEspacios() {
        return listaEspacios;
    }

    public List getProfesores() {
        return listaProfesores;
    }

    public List getReservas() {
        return listaReservas;
    }

    public void guardarFicheroDepartamento() throws IOException {
        departamentoDAO.guardarFichero(listaDepartamentos);
    }

    public void guardarFicheroEspacio() throws IOException {
        espacioDAO.guardarFichero(listaEspacios);
    }

    public void guardarFicheroProfesor() throws IOException {
        profesorDAO.guardarFichero(listaProfesores);
    }

    public void guardarFicheroReserva() throws IOException {
        reservaDAO.guardarFichero(listaReservas);
    }



    public void altaDepartamento(Departamento departamento) {
        for (Departamento d : listaDepartamentos) {
            if (d.getId().equalsIgnoreCase(departamento.getId())) {
                throw new IllegalArgumentException("Ya existe un departamento con el id " + departamento.getId() + ".");
            }
        }
        listaDepartamentos.add(departamento);
    }

    /**
     * Regla de negocio: un profesor debe pertenecer a un departamento existente.
     * Por eso no se permite eliminar un departamento que todavía tiene profesores asignados;
     * si se eliminara, esos profesores quedarían con un departamento inexistente
     * @param idDepartamento
     */

    public void bajaDepartamento(String idDepartamento) {
        for (Profesor p : listaProfesores) {
            if (p.getDepartamento() != null && p.getDepartamento().getId().equalsIgnoreCase(idDepartamento)) {
                throw new IllegalArgumentException("No se puede eliminar: el departamento tiene profesores asignados.");
            }
        }
        listaDepartamentos.removeIf(departamento -> departamento.getId().equals(idDepartamento));
    }

    public void altaProfesor(Profesor profesor) {
        for (Profesor p : listaProfesores) {
            if (p.getId().equalsIgnoreCase(profesor.getId())) {
                throw new IllegalArgumentException("Ya existe un profesor con el id " + profesor.getId() + ".");
            }
        }
        boolean departamentoExiste = false;
        if (profesor.getDepartamento() != null) {
            for (Departamento d : listaDepartamentos) {
                if (d.getId().equalsIgnoreCase(profesor.getDepartamento().getId())) {
                    departamentoExiste = true;
                    break;
                }
            }
        }
        if (!departamentoExiste) {
            throw new IllegalArgumentException("El profesor debe pertenecer a un departamento existente.");
        }
        listaProfesores.add(profesor);
    }


    /**
     * No se puede eliminar un profesor con reservas asociadas: las reservas quedarían
     * apuntando a un profesor inexistente y daría error al volver a cargar los ficheros.
     * @param idProfesor
     */
    public void bajaProfesor(String idProfesor) {
        for (Reserva r : listaReservas) {
            if (r.getProfesor() != null && r.getProfesor().getId().equalsIgnoreCase(idProfesor)) {
                throw new IllegalArgumentException("No se puede eliminar: el profesor tiene reservas asociadas.");
            }
        }
        listaProfesores.removeIf(profesor -> profesor.getId().equals(idProfesor));
    }

    public void modificarDisponibilidadEspacio(String idEspacio, EstadoEspacio estado) {
        for (Espacio espacio : listaEspacios) {
            if (espacio.getCodigoEspacio().equalsIgnoreCase(idEspacio)) {
                espacio.setEstado(estado);
            }
        }
    }

    public void altaEspacio(Espacio espacio) {
        for (Espacio e : listaEspacios) {
            if (e.getCodigoEspacio().equalsIgnoreCase(espacio.getCodigoEspacio())) {
                throw new IllegalArgumentException("Ya existe un espacio con el código " + espacio.getCodigoEspacio() + ".");
            }
        }
        listaEspacios.add(espacio);
    }

    public void crearReserva(Reserva reserva) {
        // Id único
        for (Reserva r : listaReservas) {
            if (r.getId().equalsIgnoreCase(reserva.getId())) {
                throw new IllegalArgumentException("Ya existe una reserva con el id " + reserva.getId() + ".");
            }
        }

        // Espacio disponible (se consulta el estado actual en la lista de espacios)
        Espacio espacio = reserva.getEspacio();
        for (Espacio e : listaEspacios) {
            if (e.getCodigoEspacio().equalsIgnoreCase(reserva.getEspacio().getCodigoEspacio())) {
                espacio = e;
                break;
            }
        }
        if (espacio.getEstado() != EstadoEspacio.DISPONIBLE) {
            throw new IllegalArgumentException("El espacio " + espacio.getNombreEspacio() + " no está disponible.");
        }

        // Asistentes <= capacidad
        if (reserva.getNumeroAsistentes() > espacio.getCapacidad()) {
            throw new IllegalArgumentException("El número de asistentes (" + reserva.getNumeroAsistentes()
                    + ") supera la capacidad del espacio (" + espacio.getCapacidad() + ").");
        }

        // Formato de fecha y horas
        LocalDate fecha;
        LocalTime inicio;
        LocalTime fin;
        try {
            fecha = LocalDate.parse(reserva.getFecha());
            inicio = LocalTime.parse(reserva.getHoraInicio());
            fin = LocalTime.parse(reserva.getHoraFin());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha u hora no válidas. Use el formato yyyy-MM-dd y HH:mm (por ejemplo 09:30).");
        }

        // Hora fin > hora inicio
        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("La hora de finalización debe ser posterior a la hora de inicio.");
        }

        for (Reserva r : listaReservas) {
            if (r.getEstado() == EstadoReserva.CANCELADA) continue;
            if (!LocalDate.parse(r.getFecha()).equals(fecha)) continue;

            boolean solapan = inicio.isBefore(LocalTime.parse(r.getHoraFin()))
                    && LocalTime.parse(r.getHoraInicio()).isBefore(fin);
            if (!solapan) continue;

            // Mismo espacio
            if (r.getEspacio().getCodigoEspacio().equalsIgnoreCase(espacio.getCodigoEspacio())) {
                throw new IllegalArgumentException("El espacio " + espacio.getNombreEspacio() + " ya está reservado de "
                        + r.getHoraInicio() + " a " + r.getHoraFin() + " ese día.");
            }

            // Mismo profesor en otro espacio
            if (r.getProfesor().getId().equalsIgnoreCase(reserva.getProfesor().getId())) {
                throw new IllegalArgumentException("El profesor ya tiene otra reserva de "
                        + r.getHoraInicio() + " a " + r.getHoraFin() + " ese día.");
            }
        }

        listaReservas.add(reserva);
    }

    public void cancelarReserva(String idReserva) {
        for (Reserva reserva : listaReservas) {
            if (reserva.getId().equalsIgnoreCase(idReserva)) {
                reserva.setEstado(EstadoReserva.CANCELADA);
            }
        }
    }

    public void guardarTodo() throws IOException {
        departamentoDAO.guardarFichero(listaDepartamentos);
        espacioDAO.guardarFichero(listaEspacios);
        profesorDAO.guardarFichero(listaProfesores);
        reservaDAO.guardarFichero(listaReservas);
    }
}