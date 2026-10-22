package dao;

import modelo.Reserva;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ReservaDAO {
    List<Reserva> leerFichero() throws FileNotFoundException;
    void guardarFichero(List<Reserva> reservas) throws IOException;
}
