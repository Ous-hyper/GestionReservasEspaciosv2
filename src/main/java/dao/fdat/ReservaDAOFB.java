package dao.fdat;

import dao.ReservaDAO;
import modelo.Espacio;
import modelo.Reserva;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oussama rajil
 * @version 1.0.0
 */
public class ReservaDAOFB implements ReservaDAO {

    final String FICHERO_DATOS = "data/dat/reserva.dat";

    public List<Reserva> leerFichero(){

        List<Reserva> reservas = new ArrayList<>();
        File fichero = new File(FICHERO_DATOS);

        if (!fichero.exists()){
            return reservas;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FICHERO_DATOS))) {

            reservas =  (List<Reserva>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Error -> " + e.getMessage());
        }
        return reservas;
    }



    @Override
    public void guardarFichero(List<Reserva> reservas) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FICHERO_DATOS))) {
            oos.writeObject(reservas);
        }
    }
}
