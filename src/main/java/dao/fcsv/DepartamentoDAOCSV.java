package dao.fcsv;

import dao.DepartamentoDAO;
import modelo.Departamento;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAOCSV implements DepartamentoDAO {

    private final String FICHERO_DATOS = "data/csv/departamento.csv";


    @Override
    public List<Departamento> leerFichero() throws FileNotFoundException{
        ArrayList<Departamento> listaDepartamentos = new ArrayList<>();

        File fichero = new File(FICHERO_DATOS);

        if (!fichero.exists()) {
            return listaDepartamentos;
        }

        try (BufferedReader flujoEntrada = new BufferedReader(new FileReader(fichero))) {
            String linea;

            while ((linea = flujoEntrada.readLine()) != null) {
                String[] datos = linea.split(";");
                Departamento departamento = new Departamento(
                        datos[0], datos[1]
                );

                listaDepartamentos.add(departamento);
            }


        } catch (IOException e) {
            System.out.println("Error al leer el fichero. " + e.getMessage());;
        }

        return listaDepartamentos;
    }

    @Override
    public void guardarFichero(List<Departamento> departamentos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO_DATOS))) {

            for (Departamento departamento : departamentos) {
                String linea = departamento.getId() + ";" + departamento.getNombre();
                bw.write(linea);
                bw.newLine();
            }

        }
    }

    public Departamento getDepartamentoById(String id) throws FileNotFoundException {
        for (Departamento departamento : leerFichero()) {
            if (departamento.getId().equals(id)) {
                return departamento;
            }
        }
        return null;
    }
}
