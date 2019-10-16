package fr.umontpellier.iut;

public class App {
    public static void main(String[] args) {
        Settings.setBacteriumbDiff(1);
        Settings.setBacteriummIni(10);
        Settings.setBacteriumvCons(1);
        Settings.setBacteriumkConv(1);
        Settings.setCellcIni(10);
        Settings.setCellcMin(5);
        Settings.setCellvDiff(1);
        Settings.setEnviromnentCellsPerSide(10);
        Settings.setEnviromnentHalfLength(50);

        Simulation simulation = new Simulation(50, 10, 25, 25);
        System.out.println(Cell.getLength());
        simulation.run(15);
    }
    /**
     * NOTES :
     * - ...
     */
}
