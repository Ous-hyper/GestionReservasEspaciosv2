package modelo;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Espacio implements Serializable {
    @SerializedName("idEspacio")
    private String codigoEspacio;
    @SerializedName("nombre")
    private String nombreEspacio;
    @SerializedName("tipo")
    private String tipoEspacio;
    @SerializedName("ubicacion")
    private String edificio;
    private int capacidad;
    private String equipamiento;
    private EstadoEspacio estado;

    public Espacio() {
    }
    public Espacio(String codigoEspacio, String nombreEspacio, String tipoEspacio, String edificio,  int capacidad, String equipamiento, EstadoEspacio estado){
        this.codigoEspacio = codigoEspacio;
        this.nombreEspacio = nombreEspacio;
        this.tipoEspacio = tipoEspacio;
        this.edificio = edificio;
        this.capacidad = capacidad;
        this.equipamiento = equipamiento;
        this.estado = estado;
    }

    public EstadoEspacio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEspacio estado) {
        this.estado = estado;
    }

    public String getCodigoEspacio() {
        return codigoEspacio;
    }

    public void setCodigoEspacio(String codigoEspacio) {
        this.codigoEspacio = codigoEspacio;
    }

    public String getNombreEspacio() {
        return nombreEspacio;
    }

    public void setNombreEspacio(String nombreEspacio) {
        this.nombreEspacio = nombreEspacio;
    }

    public String getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(String tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }


    @Override
    public String toString() {
        return "Espacio{" +
                "codigoEspacio='" + codigoEspacio + '\'' +
                ", nombreEspacio='" + nombreEspacio + '\'' +
                ", tipoEspacio='" + tipoEspacio + '\'' +
                ", edificio='" + edificio + '\'' +
                ", capacidad=" + capacidad +
                ", equipamiento='" + equipamiento + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
