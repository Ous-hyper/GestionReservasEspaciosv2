package dao.fjson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.DepartamentoDAO;
import modelo.Departamento;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAOJS implements DepartamentoDAO {
    final String FICHERO = "data/json/departamento.json";

    public List<Departamento> leerFichero() throws FileNotFoundException {
        ArrayList<Departamento> departamentos = new ArrayList<Departamento>();

        File fichero = new File(FICHERO);

        if (!fichero.exists()) {
            return departamentos;
        }

        Gson gson = new Gson();
        FileReader fr = new FileReader(FICHERO);

        Type tipoLista = new TypeToken<ArrayList<Departamento>>() {}.getType();

        departamentos = gson.fromJson(fr, tipoLista);

        return departamentos;
    }

    public void guardarFichero(List<Departamento> departamentos) throws IOException {
        Gson gson = new Gson();

        FileWriter fw = new FileWriter(FICHERO);

        gson.toJson(departamentos, fw);

        fw.close();
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
