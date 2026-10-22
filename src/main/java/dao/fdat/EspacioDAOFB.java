package dao.fdat;

import dao.EspacioDAO;
import modelo.Departamento;
import modelo.Espacio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oussama rajil
 * @version 1.0.0
 */
public class EspacioDAOFB implements EspacioDAO {

    final String FICHERO_DATOS = "data/dat/espacios.dat";

    public List<Espacio> leerFichero(){

        List<Espacio> espacios = new ArrayList<>();
        File fichero = new File(FICHERO_DATOS);

        if (!fichero.exists()){
            return espacios;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FICHERO_DATOS))) {

            espacios =  (List<Espacio>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Error -> " + e.getMessage());
        }
        return espacios;
    }



    @Override
    public void guardarFichero(List<Espacio> espacios) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FICHERO_DATOS))) {
            oos.writeObject(espacios);
        }
    }
}
