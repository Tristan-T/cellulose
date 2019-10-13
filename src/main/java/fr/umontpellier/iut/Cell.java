package fr.umontpellier.iut;

public class Cell {
    private static double cIni;
    private static double cMin;
    private static double vDiff;
    private static double length;
    private Environment environment;
    private int xCell;
    private int yCell;
    private double concentration;
    private boolean isSemiLiquid;

    public Cell(int xCell, int yCell, Environment environment, boolean isSubstratum) {
        this.xCell = xCell;
        this.yCell = yCell;
        this.environment = environment;
        isSemiLiquid = false;
        if (isSubstratum) {
            concentration = cIni;
        } else {
            concentration=0;
        }
    }

    /**
     * Diffuses the substratum in the neighboring cells once semi liquid
     * (more info : #10 of description.pdf)
     */
    public void diffuse() {}

    /**
     * Lowers the concentration of the cell (because of bacteria)
     * (more info : #9 of description.pdf)
     */
    public void getBrokenDown() {}

    //GETTERS
    public double getConcentration() {
        return concentration;
    }
    public static double getLength() {
        return length;
    }

    //SETTERS


    public static void setcIni(double cIni) {
        Cell.cIni = cIni;
    }

    public static void setcMin(double cMin) {
        Cell.cMin = cMin;
    }

    public static void setvDiff(double vDiff) {
        Cell.vDiff = vDiff;
    }

    public static void setLength(double length) {
        Cell.length = length;
    }
}
