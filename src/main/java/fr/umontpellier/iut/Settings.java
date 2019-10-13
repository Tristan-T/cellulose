package fr.umontpellier.iut;

public class Settings {
    /**
     * WE WILL SET THE DIFFERENT PARAMETERS THROUGH THIS CLASS
     * Ex: vDiff or halfLength
     */

    public static void setCellLength(double l) {
        Cell.setLength(l);
    }
    public static void setCellcIni(double c) {
        Cell.setcIni(c);
    }
    public static void setCellcMin(double c) {
        Cell.setcMin(c);
    }
    public static void setCellvDiff(double v) {
        Cell.setvDiff(v);
    }
    public static void setBacteriumbDiff(double b) {
        Bacterium.setbDiff(b);
    }
    public static void setBacteriummIni(double m) {
        Bacterium.setmIni(m);
    }
    public static void setEnviromnentCellsPerSide(int c) {
        Environment.setCellsPerSide(c);
    }
    public static void setEnviromnentHalfLength(double h) {
        Environment.setHalfLength(h);
    }
}
