package dao.fdat;

import dao.DepartamentoDAO;
import modelo.Departamento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oussama Rajil
 * @version 1.0.0
 */
public class DepartamentoDAOFB implements DepartamentoDAO {

    final String FICHERO_DATOS = "data/dat/departamento.dat";
    public List<Departamento> leerFichero() throws IOException {

        List<Departamento> departamentos = new ArrayList<>();
        File fichero = new File(FICHERO_DATOS);

        if (!fichero.exists()){
            return departamentos;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FICHERO_DATOS))) {

            departamentos =  (List<Departamento>) ois.readObject();

        } catch (ClassNotFoundException e) {
            throw new IOException("No se pudo recuperar la clase Departamento", e);
        }
        return departamentos;
    }



    @Override
    public void guardarFichero(List<Departamento> departamentos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FICHERO_DATOS))) {

            oos.writeObject(departamentos);
        }
    }
}
