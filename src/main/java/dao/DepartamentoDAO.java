package dao;

import modelo.Departamento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface DepartamentoDAO {
    List<Departamento> leerFichero() throws IOException;
    void guardarFichero(List<Departamento> departamentos) throws IOException;
}
