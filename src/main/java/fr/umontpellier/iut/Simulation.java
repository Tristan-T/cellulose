package fr.umontpellier.iut;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Simulation extends Task<Void> {
    private static double timeDelta;
    private static int timeDeltaSubdivision;
    private static int initialBacteriaAmount;
    private static double maxDuration;
    private Environment environment;
    private FileWriter outputFile;
    private String outputFileName;
    private static boolean shouldLog = false; //By default disabled
    private static boolean hasUI = true;
    private static boolean wasCancelled = false;

    /**
     * Initializes an environment with the correct time settings
     */
    public Simulation(String outputFileName) {
        this.environment = new Environment(timeDelta/timeDeltaSubdivision, initialBacteriaAmount);
        this.outputFileName = outputFileName;
    }

    /**
     * Calls tick() of environment every timeDeltaSubdivision and outputs data every timeDelta
     * @return
     */
    //Or returns an int which could be the execution code
    @Override
    public Void call() {
        if(shouldLog) {
            try {
                outputFile = new FileWriter("./output/" + outputFileName + ".json", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(wasCancelled) {
            GUI.resetGraph();
            wasCancelled = false;
        }

        double lastFrame = maxDuration;
        double timeLeft = maxDuration;
        algoend:
        while (timeLeft>0) {
            timeLeft-=timeDelta/timeDeltaSubdivision;
            //System.out.println(maxDuration);
            //output data (for rendering and stuff every delta time)
            if (timeLeft<lastFrame-timeDelta){

                if(isCancelled()) {
                    //If user has requested to cancel, cancel everything
                    break algoend;
                }

                long startTime = System.nanoTime();
                //output data
                lastFrame=timeLeft;
                double[][] bacteriaData = environment.getBacteriaData();
                double[][] cellData = environment.getCellData();

                //Slow down a lot, might want to try writing in RAM
                if(shouldLog) {
                    outputToFile(cellData, bacteriaData, maxDuration - timeLeft);
                }

                //Sout for debugging purposes
                //System.out.println("Total population: " + (bacteriaData.length-1));
                //System.out.println("Total concentration: " + cellData[cellData.length-1][0]);


                //Threading image creation
                //Would require to check if was drawn before executing but calculation is vastly slower than drawing so not an issue even if we might miss some images
                if(hasUI) {
                    //Update model
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            GUI.createModelImage(cellData, bacteriaData);
                        }
                    });

                    //Update graphs
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            GUI.updateGraph(environment.getBiomass(), environment.getTotalConcentration());
                        }
                    });
                }

                if (cellData[cellData.length-1][0]<=0.0) {
                    break algoend;
                }
                long endTime = System.nanoTime();
                System.out.println("temps d'exec: " + ((endTime - startTime)/1000000));
            }
            environment.tick();
        }
        //Print the time it took
        System.out.println(maxDuration-timeLeft);
        return null;
    }

    /**
     * Outputs formatted data given in a JSON file
     * All parameters are self-explanatory
     * @param cellData
     * @param bacteriaData
     * @param timeElapsed
     */
    public void outputToFile(double[][] cellData, double[][] bacteriaData, double timeElapsed){
        //Create the various JSON objects and fill them with appropriate values
        JSONObject fullData = new JSONObject();

        //Add the bacterium-related data to the JSON
        JSONObject bacteriaDataJson = new JSONObject();
        //All single-bacterium related data is put in this array...
        JSONArray bacteria = new JSONArray();
        for (int i = 0; i < bacteriaData.length-2; i++) {
            //...that is to say x and y position as well as mass
            JSONObject bacterium = new JSONObject();
            bacterium.put("x", bacteriaData[i][0]);
            bacterium.put("y", bacteriaData[i][1]);
            bacterium.put("m", bacteriaData[i][2]);
            bacteria.add(bacterium);
        }

        bacteriaDataJson.put("Bacteria", bacteria);

        //And add general data (total population and total biomass)
        bacteriaDataJson.put("TotalPopulation", bacteriaData.length-1);
        bacteriaDataJson.put("TotalBiomass", bacteriaData[bacteriaData.length-1][0]);

        //Add it to the general JSON object
        fullData.put("BacteriaData", bacteriaDataJson);

        //Add the cell-related data to the JSON
        JSONObject cellDataJson = new JSONObject();
        //All single-cell related data is put in this array...
        JSONArray cells = new JSONArray();
        for (int i = 0; i < cellData.length-2; i++) {
            for (int j = 0; j < cellData.length-1; j++) {
                //...that is to say x and y position as well as concentration
                JSONObject cell = new JSONObject();
                cell.put("x", i);
                cell.put("y", j);
                cell.put("c", cellData[i][j]);
                cells.add(cell);
            }
        }

        cellDataJson.put("Cells", cells);

        //And add general data (total substratum mass)
        cellDataJson.put("TotalSubstratumMass", cellData[cellData.length-1][0]);

        //Add it to the general JSON object
        fullData.put("CellsData", cellDataJson);

        //Write JSON file in the output folder
        try {
            outputFile.write(fullData.toJSONString());
            outputFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //GETTERS

    public static double getTimeDelta() {
        return timeDelta;
    }

    public static int getTimeDeltaSubdivision() {
        return timeDeltaSubdivision;
    }

    public static int getInitialBacteriaAmount() {
        return initialBacteriaAmount;
    }

    public static double getMaxDuration() {
        return maxDuration;
    }

    //SETTERS

    public static void setTimeDelta(double timeDelta) {
        Simulation.timeDelta = timeDelta;
    }

    public static void setTimeDeltaSubdivision(int timeDeltaSubdivision) {
        Simulation.timeDeltaSubdivision = timeDeltaSubdivision;
    }

    public static void setInitialBacteriaAmount(int initialBacteriaAmount) {
        Simulation.initialBacteriaAmount = initialBacteriaAmount;
    }

    public static void setMaxDuration(double simulationMaxDuration) {
        Simulation.maxDuration = simulationMaxDuration;
    }

    public static void setShouldLog(boolean shouldLog) {
        Simulation.shouldLog = shouldLog;
    }

    public static void setHasUI(boolean hasUI) {
        Simulation.hasUI = hasUI;
    }

    public static void setWasCancelled(boolean wasCancelled) {
        Simulation.wasCancelled = wasCancelled;
    }
}
