package fr.umontpellier.iut;

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
//        Settings.setSimulation_timeDeltaSubdivision(10);
//        Settings.setSimulation_initialBacteriaAmount(25);
//        Settings.setSimulation_maxDuration(500);
//
//        Settings.exportConfig("settings");
        try {
            Settings.loadConfig("settings");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Simulation simulation = new Simulation("outputTestRun");
        simulation.run();
    }
}
