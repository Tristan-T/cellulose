package fr.umontpellier.iut;

import java.util.ArrayList;

public class Bacterium {
    private static double mIni;
    private static double bDiff;
    private double x;
    private double y;
    private double mass;
    private Environment environment;
    private Cell[][] neighboringCells;

    public Bacterium(double x, double y, Environment environment) {
        this.x = x;
        this.y = y;
        this.environment = environment;
        mass = mIni;
    }

    /**
     * Moves the bacterium's x and y positon
     * Uses computeConcentrationInDirection()
     * (more info : #8 of description.pdf)
     */
    public void move() {
        //VX=vd(Co-Ce)/2H
        //X(t+delta)=Xt + deltaVX + Bdiff * sqrt(delta) * rand();
        double vx = ((computeConcentrationInDirection("west")-computeConcentrationInDirection("east")))/(2*Cell.getLength());
        x = x + (environment.getTimeDelta()*vx) + bDiff*Math.sqrt(environment.getTimeDelta())*Math.random();

        //VY=vd(Co-Ce)/2H
        //Y(t+delta)=Yt + deltaVY + Bdiff * sqrt(delta) * rand();
        double vy = ((computeConcentrationInDirection("south")-computeConcentrationInDirection("north")))/(2*Cell.getLength());
        y = y + (environment.getTimeDelta()*vy) + bDiff*Math.sqrt(environment.getTimeDelta())*Math.random();

    }

    /**
     * @param direction
     * @return the concentration C of the cell(s) in the "direction"
     * (more info : #8 of description.pdf)
     */
    public double computeConcentrationInDirection(String direction) {
        double concentration=0;
        neighboringCells=environment.getNeighboringCells(x, y);
        switch (direction){
            case "west" :
                concentration = neighboringCells[0][1].getConcentration();//getConcentration() of left cell
                break;
            case "east" :
                concentration = neighboringCells[2][1].getConcentration();//getConcentration() of right cell
                break;
            case "south" :
                concentration = neighboringCells[1][2].getConcentration();//getConcentration() of bottom cell
                break;
            case "north" :
                concentration = neighboringCells[1][0].getConcentration();//getConcentration() of top cell
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

    public Bacterium divide() {
        if(mass>=2*mIni) {
            mass = mIni;
            return new Bacterium(x, y, environment);
        } else {
            return null;
        }
    }

    //SETTERS
    public static void setmIni(double mIni) {
        Bacterium.mIni = mIni;
    }

    public static void setbDiff(double bDiff) {
        Bacterium.bDiff = bDiff;
    }
}
