package fr.umontpellier.iut;

public class App {
    public static void main(String[] args) {
        Settings.setBacteriumbDiff(0);
        Settings.setBacteriummIni(10);
        Settings.setCellcIni(10);
        Settings.setCellcMin(5);
        Settings.setCellvDiff(0);
        Settings.setEnviromnentCellsPerSide(10);
        Settings.setEnviromnentHalfLength(50);

        Simulation simulation = new Simulation(50, 10, 25, 25);
    }
    /**
     * NOTES :
     * - ...
     */
}
