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
    public void move() {
        int vx = (/*vd*/*(computeConcentrationInDirection("ouest")-computeConcentrationInDirection("est")))/(2*/*longueur de la cellule*/);
        x = x + (environment/*.getTimeDelta*/*vx) + bDiff*Math.sqrt(environment./*.getTimeDelta*/)*Math.random();

        int vy = (/*vd*/*(computeConcentrationInDirection("sud")-computeConcentrationInDirection("nord")))/(2*/*longueur de la cellule*/);
        y = y + (environment/*.getTimeDelta*/*vy) + bDiff*Math.sqrt(environment./*.getTimeDelta*/)*Math.random();

    }

    /**
     * @param direction
     * @return the concentration C of the cells in the "direction"
     * (more info : #8 of description.pdf)
     */
    public double computeConcentrationInDirection(String direction) {
        double concentration;
        switch (direction) {
            case "ouest" :
                concentration = ;// getConcentration() de la cellule de gauche
                break;
            case "est" :
                concentration = ;// getConcentration() de la cellule de droite
                break;
            case "sud" :
                concentration = ;// getConcentration() de la cellule au-dessous
                break;
            case "nord" :
                concentration = ;// getConcentration() de la cellule au-dessus
                break;

        }
        return concentration;

    }

    /**
     * Lowers the concentration in the 9 neighboring cells
     * Increases mass
     * (more info : #9 of description.pdf)
     */
    public void eat() {}
    public ArrayList<Bacterium> divide() { return null;}



}
