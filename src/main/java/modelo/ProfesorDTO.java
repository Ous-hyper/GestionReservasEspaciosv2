package modelo;

public class ProfesorDTO {
    private String idProfesor;
    private String nombre;
    private String apellidos;
    private String email;
    private String idDepartamento;

    public ProfesorDTO(Profesor p) {
        this.idProfesor = p.getId();
        this.nombre = p.getNombre();
        this.apellidos = p.getApellido();
        this.email = p.getEmail();
        this.idDepartamento = p.getDepartamento().getId();
    }
}