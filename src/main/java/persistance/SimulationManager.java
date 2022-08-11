package persistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import metier.Simulation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SimulationManager {

    public static final String lienFichierSimulation = "src/main/resources/simulation.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void ecrire(Simulation simulation) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(lienFichierSimulation);
            mapper.writeValue(fileOutputStream, simulation);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Simulation lire() {
        try {
            FileInputStream fileInputStream = new FileInputStream(lienFichierSimulation);
            return mapper.readValue(fileInputStream, Simulation.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
