package fr.umontpellier.iut;

import java.util.ArrayList;

public class Bacterium {
    private double x;
    private double y;
    private double mass;
    private static double mIni;
    private static double bDiff;
    private Environment environment;

    /**
     * Moves the bacterium's x and y positon
     * Uses computeConcentrationInDirection()
     * (more info : #8 of description.pdf)
     */
    public void move() {}

    /**
     * @param direction
     * @return the concentration C of the cells in the "direction"
     * (more info : #8 of description.pdf)
     */
    public double computeConcentrationInDirection(String direction) {return 0.0;}

    /**
     * Lowers the concentration in the 9 neighboring cells
     * Increases mass
     * (more info : #9 of description.pdf)
     */
    public void eat() {}
    public ArrayList<Bacterium> divide() { return null;}



}
