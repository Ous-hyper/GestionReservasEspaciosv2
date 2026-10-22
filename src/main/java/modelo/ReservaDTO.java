package modelo;

public class ReservaDTO {

    private String idReserva;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private EstadoReserva estado;
    private String idProfesor;
    private String idEspacio;
    private String motivo;
    private int numAsistentes;

    public ReservaDTO(Reserva r) {
        this.idReserva = r.getId();
        this.fecha = r.getFecha();
        this.horaInicio = r.getHoraInicio();
        this.horaFin = r.getHoraFin();
        this.estado = r.getEstado();
        this.idProfesor = r.getProfesor().getId();
        this.idEspacio = r.getEspacio().getCodigoEspacio();
        this.motivo = r.getMotivo();
        this.numAsistentes = r.getNumeroAsistentes();
    }
}