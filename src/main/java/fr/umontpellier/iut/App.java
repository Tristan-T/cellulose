package fr.umontpellier.iut;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
//        Settings.setBacterium_bDiff(1);
//        Settings.setBacterium_mIni(10);
//        Settings.setBacterium_vCons(1);
//        Settings.setBacterium_kConv(1);
//        Settings.setBacterium_vd(1);
//        Settings.setCell_cIni(10);
//        Settings.setCell_cMin(5);
//        Settings.setCell_vDiff(1);
//        Settings.setEnvironment_cellsPerSide(10);
//        Settings.setEnvironment_halfLength(50);
//        Settings.setEnvironment_substratumRadius(25);
//        Settings.setSimulation_timeDelta(50);
//        Settings.setSimulation_timeDeltaSubdivition(10);
//        Settings.setSimulation_initialBacteriaAmount(25);
//        Settings.setSimulation_maxDuration(500);
//
//        exportConfig("settings");

        loadConfig("settings");

        Simulation simulation = new Simulation();
        System.out.println(Cell.getLength());
        simulation.run();
    }

    public static void exportConfig(String fileName){
        //Create the various JSON objects and fill them with appropriate values
        JSONObject simulationSettings = new JSONObject();
        simulationSettings.put("timeDelta", Settings.getSimulation_timeDelta());
        simulationSettings.put("timeDeltaSubdivition", Settings.getSimulation_timeDeltaSubdivition());
        simulationSettings.put("initialBacteriaAmount", Settings.getSimulation_initialBacteriaAmount());
        simulationSettings.put("maxDuration", Settings.getSimulation_maxDuration());

        JSONObject simulation = new JSONObject();
        simulation.put("Simulation", simulationSettings);

        JSONObject environmentSettings = new JSONObject();
        environmentSettings.put("cellsPerSide", Settings.getEnvironment_cellsPerSide());
        environmentSettings.put("halfLength", Settings.getEnvironment_halfLength());
        environmentSettings.put("substratumRadius", Settings.getEnvironment_substratumRadius());

        JSONObject environment = new JSONObject();
        environment.put("Environment", environmentSettings);

        JSONObject cellSettings = new JSONObject();
        cellSettings.put("cIni", Settings.getCell_cIni());
        cellSettings.put("cMin", Settings.getCell_cMin());
        cellSettings.put("vDiff", Settings.getCell_vDiff());

        JSONObject cell = new JSONObject();
        cell.put("Cell", cellSettings);

        JSONObject bacteriumSettings = new JSONObject();
        bacteriumSettings.put("bDiff", Settings.getBacterium_bDiff());
        bacteriumSettings.put("kConv", Settings.getBacterium_kConv());
        bacteriumSettings.put("mIni", Settings.getBacterium_mIni());
        bacteriumSettings.put("vCons", Settings.getBacterium_vCons());
        bacteriumSettings.put("vd", Settings.getBacterium_vd());

        JSONObject bacterium = new JSONObject();
        bacterium.put("Bacterium", bacteriumSettings);

        //Add all categories to list
        JSONArray config = new JSONArray();
        config.add(simulation);
        config.add(environment);
        config.add(cell);
        config.add(bacterium);

        //Write JSON file in the config folder
        try (FileWriter file = new FileWriter("./config/"+fileName+".json")) {

            file.write(config.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig(String fileName){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("./config/"+fileName+".json"))
        {
            //Read JSON file and cast it as JSONArray
            Object obj = jsonParser.parse(reader);
            JSONArray settings = (JSONArray) obj;

            //Get the category then the setting and apply it
            JSONObject simulation = (JSONObject) settings.get(0);
            simulation= (JSONObject) simulation.get("Simulation");

            //We have to cast what we get because it returns an object
            Settings.setSimulation_timeDelta((double) simulation.get("timeDelta"));
            Settings.setSimulation_timeDeltaSubdivition((int) (long) simulation.get("timeDeltaSubdivition"));
            Settings.setSimulation_initialBacteriaAmount((int) (long) simulation.get("initialBacteriaAmount"));
            Settings.setSimulation_maxDuration((double) simulation.get("maxDuration"));

            JSONObject environment = (JSONObject) settings.get(1);
            environment= (JSONObject) environment.get("Environment");

            Settings.setEnvironment_cellsPerSide((int) (long) environment.get("cellsPerSide"));
            Settings.setEnvironment_halfLength((double) environment.get("halfLength"));
            Settings.setEnvironment_substratumRadius((double) environment.get("substratumRadius"));

            JSONObject cell = (JSONObject) settings.get(2);
            cell= (JSONObject) cell.get("Cell");

            Settings.setCell_cIni((double) cell.get("cIni"));
            Settings.setCell_cMin((double) cell.get("cMin"));
            Settings.setCell_vDiff((double) cell.get("vDiff"));

            JSONObject bacterium = (JSONObject) settings.get(3);
            bacterium= (JSONObject) bacterium.get("Bacterium");

            Settings.setBacterium_bDiff((double) bacterium.get("bDiff"));
            Settings.setBacterium_mIni((double) bacterium.get("mIni"));
            Settings.setBacterium_vCons((double) bacterium.get("vCons"));
            Settings.setBacterium_kConv((double) bacterium.get("kConv"));
            Settings.setBacterium_vd((double) bacterium.get("vd"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * NOTES :
     * - ...
     */
}
