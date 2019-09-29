package fr.umontpellier.iut;

public class Cell {
    private double concentration;
    private static double cIni;
    private static double cMin;
    private static double vDiff;
    private int xCell;
    private int yCell;
    private double length;
    private boolean isSemiLiquid;
    private static Environment environment;

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
}
