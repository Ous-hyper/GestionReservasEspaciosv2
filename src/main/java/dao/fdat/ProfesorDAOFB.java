package dao.fdat;

import dao.ProfesorDAO;
import modelo.Espacio;
import modelo.Profesor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oussama rajil
 * @version 1.0.0
 */
public class ProfesorDAOFB implements ProfesorDAO {

    final String FICHERO_DATOS = "data/dat/profesores.dat";

    public List<Profesor> leerFichero(){

        List<Profesor> profesores = new ArrayList<>();
        File fichero = new File(FICHERO_DATOS);

        if (!fichero.exists()){
            return profesores;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FICHERO_DATOS))) {

            profesores =  (List<Profesor>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Error -> " + e.getMessage());
        }
        return profesores;
    }



    @Override
    public void guardarFichero(List<Profesor> profesores) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FICHERO_DATOS))) {
            oos.writeObject(profesores);
        }
    }
}
