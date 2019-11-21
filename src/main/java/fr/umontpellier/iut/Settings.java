package fr.umontpellier.iut;

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
    public static void setSimulation_timeDeltaSubdivition(int t) { Simulation.setTimeDeltaSubdivision(t); }
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
    public static int getSimulation_timeDeltaSubdivition() { return Simulation.getTimeDeltaSubdivision(); }
    public static int getSimulation_initialBacteriaAmount() { return Simulation.getInitialBacteriaAmount(); }
    public static double getSimulation_maxDuration() { return Simulation.getMaxDuration(); }
}
