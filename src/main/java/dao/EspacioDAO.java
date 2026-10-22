package dao;

import modelo.Espacio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface EspacioDAO {
    List<Espacio> leerFichero() throws FileNotFoundException;
    void guardarFichero(List<Espacio> espacios) throws IOException;
}
