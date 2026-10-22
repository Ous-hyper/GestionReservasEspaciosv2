package dao.fcsv;

import dao.ProfesorDAO;
import modelo.Departamento;
import modelo.Espacio;
import modelo.EstadoEspacio;
import modelo.Profesor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAOCSV implements ProfesorDAO {

    private final String FICHERO_DATOS = "data/csv/profesores.csv";

    private final DepartamentoDAOCSV departamentoDAOCSV = new DepartamentoDAOCSV();

    @Override
    public List<Profesor> leerFichero() {
        ArrayList<Profesor> listaProfesores = new ArrayList<>();

        File fichero = new File(FICHERO_DATOS);

        if (!fichero.exists()) {
            return listaProfesores;
        }

        try (BufferedReader flujoEntrada = new BufferedReader(new FileReader(fichero))) {
            String linea;

            while ((linea = flujoEntrada.readLine()) != null) {
                String[] datos = linea.split(";");
                Profesor profesor = new Profesor(
                    datos[0], datos[1], datos[2], datos[3], departamentoDAOCSV.getDepartamentoById(datos[4])
                );

                listaProfesores.add(profesor);
            }


        } catch (IOException e) {
            System.out.println("Error al leer el fichero. " + e.getMessage());;
        }

        return listaProfesores;
    }

    @Override
    public void guardarFichero(List<Profesor> profesores) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO_DATOS))) {

            for (Profesor profesor : profesores) {
                String linea = profesor.getId() + ";" + profesor.getNombre()
                        + ";" + profesor.getApellido() + ";" + profesor.getEmail()
                        + ";" + profesor.getDepartamento().getId();
                bw.write(linea);
                bw.newLine();
            }

        }
    }

    public Profesor getProfesorById(String id) {
        for (Profesor profesor : leerFichero()) {
            if (profesor.getId().equals(id)) {
                return profesor;
            }
        }
        return null;
    }
}
