package dao.fjson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dao.ProfesorDAO;
import dao.fcsv.DepartamentoDAOCSV;
import modelo.Departamento;
import modelo.Profesor;
import modelo.ProfesorDTO;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAOJS implements ProfesorDAO {
    final String FICHERO = "data/json/profesores.json";

    private final DepartamentoDAOJS departamentoDAOJS = new DepartamentoDAOJS();

    public List<Profesor> leerFichero() throws FileNotFoundException {
        ArrayList<Profesor> profesores = new ArrayList<Profesor>();

        File fichero = new File(FICHERO);

        if (!fichero.exists()) {
            return profesores;
        }

        Gson gson = new Gson();

        try (FileReader fr = new FileReader(fichero)) {

            JsonArray array =
                    JsonParser.parseReader(fr).getAsJsonArray();

            for (JsonElement elemento : array) {

                JsonObject json = elemento.getAsJsonObject();

                String idDepartamento =
                        json.get("idDepartamento").getAsString();

                Departamento departamento =
                        departamentoDAOJS.getDepartamentoById(idDepartamento);

                json.remove("idDepartamento");

                Profesor profesor = gson.fromJson(json, Profesor.class);

                profesor.setDepartamento(departamento);

                profesores.add(profesor);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return profesores;
    }

    public void guardarFichero(List<Profesor> profesores) throws IOException {
        Gson gson = new Gson();

        List<ProfesorDTO> profesoresDTO = new ArrayList<>();

        for (Profesor p : profesores) {
            profesoresDTO.add(new ProfesorDTO(p));
        }

        FileWriter fw = new FileWriter(FICHERO);

        gson.toJson(profesoresDTO, fw);

        fw.close();
    }

    public Profesor getProfesorById(String id) throws FileNotFoundException {
        for (Profesor profesor : leerFichero()) {
            if (profesor.getId().equals(id)) {
                return profesor;
            }
        }
        return null;
    }
}
