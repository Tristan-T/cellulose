package fr.umontpellier.iut;

public class Simulation {
    private Environment environment;
    private double timeDelta;
    private double timeDeltaSubdivision;
    private double substratumRadius;

    /**
     * timeDelta must be a multiple of timeDeltaSubdivision =>
     */
    public Simulation(double timeDelta, int amountOfTimeSubdivisions, double substratumRadius, int initialBacteriaAmount) {
        this.timeDelta = timeDelta;
        this.substratumRadius = substratumRadius;
        this.timeDeltaSubdivision = timeDelta/amountOfTimeSubdivisions;
        this.environment = new Environment(timeDeltaSubdivision, substratumRadius, initialBacteriaAmount);
    }

    /**
     * Calls tick() of environment every timeDeltaSubdivision and outputs data every timeDelta
     */
    public void run() {} //Or returns an int which could be the execution code
}
