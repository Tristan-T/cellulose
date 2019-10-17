package fr.umontpellier.iut;

public class Simulation {
    private static double timeDelta;
    private static int timeDeltaSubdivision;
    private static int initialBacteriaAmount;
    private Environment environment;

    /**
     * timeDelta must be a multiple of timeDeltaSubdivision =>
     */
    public Simulation() {
        this.environment = new Environment(timeDeltaSubdivision, initialBacteriaAmount);
    }

    /**
     * Calls tick() of environment every timeDeltaSubdivision and outputs data every timeDelta
     */
    //Or returns an int which could be the execution code
    public void run(int n) {
        for (int i = 0; i < n; i++) {
            environment.tick();
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
}
