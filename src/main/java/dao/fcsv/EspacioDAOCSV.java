package dao.fcsv;

import dao.EspacioDAO;
import modelo.Departamento;
import modelo.Espacio;
import modelo.EstadoEspacio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EspacioDAOCSV implements EspacioDAO {

    private final String FICHERO_DATOS = "data/csv/espacios.csv";

    @Override
    public List<Espacio> leerFichero() {
        ArrayList<Espacio> listaEspacios = new ArrayList<>();

        File fichero = new File(FICHERO_DATOS);

        if (!fichero.exists()) {
            return listaEspacios;
        }

        try (BufferedReader flujoEntrada = new BufferedReader(new FileReader(fichero))) {
            String linea;

            while ((linea = flujoEntrada.readLine()) != null) {
                String[] datos = linea.split(";");
                Espacio espacio = new Espacio(
                        // 7, 5 int
                        datos[0], datos[1], datos[2], datos[3],
                        Integer.parseInt(datos[4]), datos[5],
                        EstadoEspacio.valueOf(datos[6].trim().toUpperCase())
                );

                listaEspacios.add(espacio);
            }


        } catch (IOException e) {
            System.out.println("Error al leer el fichero. " + e.getMessage());;
        }

        return listaEspacios;
    }

    @Override
    public void guardarFichero(List<Espacio> espacios) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO_DATOS))) {

            for (Espacio espacio : espacios) {
                String linea = espacio.getCodigoEspacio() + ";" + espacio.getNombreEspacio()
                        + ";" + espacio.getTipoEspacio() + ";" + espacio.getEdificio()
                        + ";" + espacio.getCapacidad() + ";" + espacio.getEquipamiento()
                        + ";" + espacio.getEstado();
                bw.write(linea);
                bw.newLine();
            }

        }
    }

    public Espacio getEspacioById(String id) {
        for (Espacio espacio : leerFichero()) {
            if (espacio.getCodigoEspacio().equals(id)) {
                return espacio;
            }
        }
        return null;
    }
}
