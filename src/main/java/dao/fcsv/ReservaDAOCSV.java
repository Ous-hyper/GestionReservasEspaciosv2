package dao.fcsv;

import dao.ReservaDAO;
import modelo.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOCSV implements ReservaDAO {

    private final String FICHERO_DATOS = "data/csv/reserva.csv";

    private final EspacioDAOCSV espacioDAOCSV = new EspacioDAOCSV();
    private final ProfesorDAOCSV profesorDAOCSV = new ProfesorDAOCSV();

    @Override
    public List<Reserva> leerFichero() {
        ArrayList<Reserva> listaReservas = new ArrayList<>();

        File fichero = new File(FICHERO_DATOS);

        if (!fichero.exists()) {
            return listaReservas;
        }

        try (BufferedReader flujoEntrada = new BufferedReader(new FileReader(fichero))) {
            String linea;

            while ((linea = flujoEntrada.readLine()) != null) {
                String[] datos = linea.split(";");

                if (datos.length < 8) continue;

                Reserva reserva = new Reserva(
                        //4 string
                        datos[0], datos[1], datos[2], datos[3],
                        EstadoReserva.valueOf(datos[4]),datos[5], profesorDAOCSV.getProfesorById(datos[6]),
                        espacioDAOCSV.getEspacioById(datos[7]), Integer.parseInt(datos[8])
                );

                listaReservas.add(reserva);
            }


        } catch (IOException e) {
            System.out.println("Error al leer el fichero. " + e.getMessage());;
        }

        return listaReservas;
    }

    @Override
    public void guardarFichero(List<Reserva> reservas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO_DATOS))) {

            for (Reserva reserva : reservas) {
                String linea = reserva.getId() + ";" + reserva.getFecha()
                        + ";" + reserva.getHoraInicio() + ";" + reserva.getHoraFin()
                        + ";" + reserva.getEstado() + ";" + reserva.getMotivo() + ";" + reserva.getProfesor().getId()
                        + ";" + reserva.getEspacio().getCodigoEspacio() + ";" + reserva.getNumeroAsistentes();
                bw.write(linea);
                bw.newLine();
            }

        }
    }
}
