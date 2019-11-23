package fr.umontpellier.iut;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {
    /**
     * WE WILL SET AND GET THE DIFFERENT PARAMETERS THROUGH THIS CLASS FOR EASE OF USE
     */

    //GETTERS
    public static void setCell_cIni(double c) {
        Cell.setcIni(c);
    }
    public static void setCell_cMin(double c) {
        Cell.setcMin(c);
    }
    public static void setCell_vDiff(double v) {
        Cell.setvDiff(v);
    }
    public static void setEnvironment_cellsPerSide(int c) {
        Environment.setCellsPerSide(c);
    }
    public static void setEnvironment_halfLength(double h) {
        Environment.setHalfLength(h);
    }
    public static void setEnvironment_substratumRadius(double r) { Environment.setSubstratumRadius(r); }
    public static void setBacterium_bDiff(double b) {
        Bacterium.setbDiff(b);
    }
    public static void setBacterium_mIni(double m) {
        Bacterium.setmIni(m);
    }
    public static void setBacterium_vCons(double v) { Bacterium.setvCons(v); }
    public static void setBacterium_vd(double v) { Bacterium.setVd(v); }
    public static void setBacterium_kConv(double k) { Bacterium.setkConv(k); /*BETWEEN 0 AND 1*/ }
    public static void setSimulation_timeDelta(double t) { Simulation.setTimeDelta(t); }
    public static void setSimulation_timeDeltaSubdivision(int t) { Simulation.setTimeDeltaSubdivision(t); }
    public static void setSimulation_initialBacteriaAmount(int n) { Simulation.setInitialBacteriaAmount(n); }
    public static void setSimulation_maxDuration(double t) { Simulation.setMaxDuration(t); }

    //SETTERS
    public static double getCell_cIni() { return Cell.getcIni(); }
    public static double getCell_cMin() { return Cell.getcMin(); }
    public static double getCell_vDiff() { return Cell.getvDiff(); }
    public static int getEnvironment_cellsPerSide() { return Environment.getCellsPerSide(); }
    public static double getEnvironment_halfLength() { return Environment.getHalfLength(); }
    public static double getEnvironment_substratumRadius() { return Environment.getSubstratumRadius(); }
    public static double getBacterium_bDiff() { return Bacterium.getbDiff(); }
    public static double getBacterium_mIni() { return Bacterium.getmIni(); }
    public static double getBacterium_vCons() { return Bacterium.getvCons(); }
    public static double getBacterium_vd() { return Bacterium.getVd(); }
    public static double getBacterium_kConv() { return Bacterium.getkConv(); }
    public static double getSimulation_timeDelta() { return Simulation.getTimeDelta(); }
    public static int getSimulation_timeDeltaSubdivision() { return Simulation.getTimeDeltaSubdivision(); }
    public static int getSimulation_initialBacteriaAmount() { return Simulation.getInitialBacteriaAmount(); }
    public static double getSimulation_maxDuration() { return Simulation.getMaxDuration(); }

    //CONFIG INTERACTION
    public static void exportConfig(String fileName){
        //Create the various JSON objects and fill them with appropriate values
        JSONObject simulationSettings = new JSONObject();
        simulationSettings.put("timeDelta", Settings.getSimulation_timeDelta());
        simulationSettings.put("timeDeltaSubdivision", Settings.getSimulation_timeDeltaSubdivision());
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
            Settings.setSimulation_timeDeltaSubdivision((int) (long) simulation.get("timeDeltaSubdivision"));
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
}
