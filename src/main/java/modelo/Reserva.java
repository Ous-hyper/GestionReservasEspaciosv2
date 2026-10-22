package modelo;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Reserva implements Serializable {

    @SerializedName("idReserva")
    private String id;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private EstadoReserva estado;



    private String motivo;
    private Profesor profesor;
    private Espacio espacio;
    @SerializedName("numAsistentes")
    private int numeroAsistentes;



    public Reserva() {

    }

    public Reserva(String id, String fecha, String horaInicio,
                   String horaFin, EstadoReserva estado, String motivo, Profesor profesor,
                   Espacio espacio, int numeroAsistentes) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.profesor = profesor;
        this.motivo = motivo;
        this.espacio = espacio;
        this.numeroAsistentes = numeroAsistentes;
        this.estado = estado;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public int getNumeroAsistentes() {
        return numeroAsistentes;
    }

    public void setNumeroAsistentes(int numeroAsistentes) {
        this.numeroAsistentes = numeroAsistentes;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", motivo='"+ motivo + '\'' +
                ", profesor=" + profesor +
                ", espacio=" + espacio +
                ", numeroAsistentes=" + numeroAsistentes +
                ", estado=" + estado +
                '}';
    }
}
