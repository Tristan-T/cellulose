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
            //System.out.println("diffusing cell at: " + xCell * length + " " + yCell* length);
            Cell[][] neighbors = environment.getNeighboringCells(xCell * length, yCell * length);
            //Compute the sum of Dv*Cv-Nv*C over the 4 directly adjacent cells
            double sum = 0;
            int n = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if ((i + j) % 2 != 0 && neighbors[i][j].isSemiLiquid()) {
                        sum += neighbors[i][j].getConcentration();
                        System.out.println(neighbors[i][j].getConcentration());
                        n++;
                    }
                }
            }
            //At this point, sum has the sum of the concentrations of the valid cells, aka the the first part (Dv*Cv)
            //because Dv = 1 if the cell is valid (isSemiLiquid), else Dv = 0

            //Now subtract 4 times concentration*nb valid cells
            //Now sum has the total amount of concentration gained.

            double concentrationDelta = vDiff * environment.getTimeDelta() * length * length * (sum-(n*concentration));
            System.out.println("concentrationDelta="+vDiff+" length*length=" + length*length + " sum="+sum + " timeDelta="+environment.getTimeDelta());

            //The gained concentration is evenly subtracted from the "donor" cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if ((i + j) % 2 != 0 && neighbors[i][j].isSemiLiquid()) {
                        neighbors[i][j].setConcentration(neighbors[i][j].getConcentration()+vDiff * environment.getTimeDelta() * length * length * (sum-(n*neighbors[i][j].getConcentration())));
                    }
                }
            }

            //Update the concentration
            concentration = concentration + concentrationDelta;
//            if(Double.isNaN(concentration)||Double.isInfinite(concentration)) {
//                System.out.println("concentrationDelta: " + concentrationDelta);
//                System.exit(0);
//            }
        }
    }



    /**
     * Lowers the concentration of the cell (because of bacteria)
     * (more info : #9 of description.pdf)
     */
    public void getBrokenDown(double amount) {
        //Update concentration
        //System.out.println("before " +concentration);
        concentration = concentration-(amount/(length*length));
        //System.out.println("after " +concentration);
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


    public void setConcentration(double concentration) {
        this.concentration = concentration;
    }

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
