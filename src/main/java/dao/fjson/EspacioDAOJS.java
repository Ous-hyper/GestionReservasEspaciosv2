package dao.fjson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.EspacioDAO;
import modelo.Departamento;
import modelo.Espacio;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EspacioDAOJS implements EspacioDAO {
    final String FICHERO = "data/json/espacios.json";

    public List<Espacio> leerFichero() throws FileNotFoundException {
        ArrayList<Espacio> espacios = new ArrayList<Espacio>();

        File fichero = new File(FICHERO);

        if (!fichero.exists()) {
            return espacios;
        }

        Gson gson = new Gson();
        FileReader fr = new FileReader(FICHERO);

        Type tipoLista = new TypeToken<ArrayList<Espacio>>() {}.getType();

        espacios = gson.fromJson(fr, tipoLista);

        return espacios;
    }

    public void guardarFichero(List<Espacio> espacios) throws IOException {
        Gson gson = new Gson();

        FileWriter fw = new FileWriter(FICHERO);

        gson.toJson(espacios, fw);

        fw.close();
    }

    public Espacio getEspacioById(String id) throws FileNotFoundException {
        for (Espacio espacio : leerFichero()) {
            if (espacio.getCodigoEspacio().equals(id)) {
                return espacio;
            }
        }
        return null;
    }
}
