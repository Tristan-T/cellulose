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
        if (isSubstratum) {
            concentration = cIni;
            isSemiLiquid = false;
        } else {
            concentration = 0;
            isSemiLiquid = true ;
        }
    }

    /**
     * Diffuses the substratum in the neighboring cells once semi liquid
     * (more info : #10 of description.pdf)
     */
    public void diffuse() {
        if (isSemiLiquid) {
            Cell[][] neighbors = environment.getNeighboringCells((xCell) * length, (yCell) * length);
            //Compute the sum of Dv*Cv-Nv*C
            double sum = 0;
            int n = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if ((i + j) % 2 != 0 && neighbors[i][j].isSemiLiquid()) {
                        sum += neighbors[i][j].getConcentration();
                        n++;
                    }
                }
            }
            sum -= n * concentration;

            //Plug the formula of diffusion
            concentration = concentration + vDiff * environment.getTimeDelta() * Environment.getHalfLength() * Environment.getHalfLength() * sum;
        }
    }

    /**
     * Lowers the concentration of the cell (because of bacteria)
     * (more info : #9 of description.pdf)
     */
    public void getBrokenDown(double amount) {
        //Update concentration
        concentration = concentration-(amount/(length*length));

        //Update isSemiLiquid
        if(concentration<=cMin){
            isSemiLiquid=true;
        }
    }

    //GETTERS
    public double getConcentration() {
        return concentration;
    }

    public boolean isSemiLiquid() {
        return isSemiLiquid;
    }

    public static double getLength() {
        return length;
    }

    public static double getcIni() {
        return cIni;
    }

    public static double getcMin() {
        return cMin;
    }

    public static double getvDiff() {
        return vDiff;
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
