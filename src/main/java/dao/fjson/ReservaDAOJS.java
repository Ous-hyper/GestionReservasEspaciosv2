package dao.fjson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dao.ReservaDAO;
import modelo.Espacio;
import modelo.Profesor;
import modelo.Reserva;
import modelo.ReservaDTO;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOJS implements ReservaDAO {
    final String FICHERO = "data/json/reserva.json";

    public List<Reserva> leerFichero() throws FileNotFoundException {
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();

        File fichero = new File(FICHERO);

        if (!fichero.exists()) {
            return reservas;
        }

        Gson gson = new Gson();
        List<Profesor> profesores = new ProfesorDAOJS().leerFichero();
        List<Espacio> espacios = new EspacioDAOJS().leerFichero();

        try (FileReader fr = new FileReader(fichero)) {

            JsonArray array = JsonParser.parseReader(fr).getAsJsonArray();

            for (JsonElement elemento : array) {
                JsonObject json = elemento.getAsJsonObject();

                String idProfesor = json.get("idProfesor").getAsString();
                String idEspacio = json.get("idEspacio").getAsString();

                Profesor profesor = null;
                for (Profesor p : profesores) {
                    if (p.getId().equals(idProfesor)) {
                        profesor = p;
                        break;
                    }
                }

                Espacio espacio = null;
                for (Espacio e : espacios) {
                    if (e.getCodigoEspacio().equals(idEspacio)) {
                        espacio = e;
                        break;
                    }
                }

                json.remove("idProfesor");
                json.remove("idEspacio");

                Reserva reserva = gson.fromJson(json, Reserva.class);

                reserva.setProfesor(profesor);
                reserva.setEspacio(espacio);

                reservas.add(reserva);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return reservas;
    }

    public void guardarFichero(List<Reserva> reservas) throws IOException {
        Gson gson = new Gson();

        List<ReservaDTO> reservasDTO = new ArrayList<>();

        for (Reserva r : reservas) {
            reservasDTO.add(new ReservaDTO(r));
        }

        FileWriter fw = new FileWriter(FICHERO);

        gson.toJson(reservasDTO, fw);

        fw.close();
    }
}
