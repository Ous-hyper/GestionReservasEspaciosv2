package dao;

import modelo.Profesor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ProfesorDAO {
    List<Profesor> leerFichero() throws FileNotFoundException;
    void guardarFichero(List<Profesor> profesores) throws IOException;
}
