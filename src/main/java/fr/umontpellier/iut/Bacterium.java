package fr.umontpellier.iut;

import java.util.Random;

public class Bacterium {
    private static double mIni;
    private static double bDiff;
    private static double vCons;
    private static double kConv;
    private static double vd;
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
     * Moves the bacterium's x and y position
     * Uses computeConcentrationInDirection()
     * (more info : #8 of description.pdf)
     */
    public void move() {
        neighboringCells=environment.getNeighboringCells(x, y);
        //VX=vd(Co-Ce)/2H
        //X(t+delta)=Xt + deltaVX + Bdiff * sqrt(delta) * rand();
        //System.out.println("moving from " + x + " " + y + "...");
        double z = bDiff*Math.sqrt(environment.getTimeDelta());
        Random random = new Random();

        double vx = (vd*(computeConcentrationInDirection("east")-computeConcentrationInDirection("west")))/(2*Cell.getLength());
        double newx = x + (environment.getTimeDelta()*vx)+z*random.nextGaussian();
        if(newx<0){
            //if x is negative then wrap its position around the "environment"
            //to prevent the case where x is way too small (less than minus the width of the environment,
            //because of wrong speed parameter for example), we have to use a modulo
            newx=Environment.getHalfLength()*2-Math.abs(newx%Environment.getHalfLength()*2);
        }else if(newx>=Environment.getHalfLength()*2){
            //if x is too large then wrap its position around the "environment"
            newx=newx%(Environment.getHalfLength()*2);
        }


        //VY=vd(Co-Ce)/2H
        //Y(t+delta)=Yt + deltaVY + Bdiff * sqrt(delta) * rand();
        double vy = (vd*(computeConcentrationInDirection("north")+computeConcentrationInDirection("south")))/(2*Cell.getLength());
        double newy = y + (environment.getTimeDelta()*vy)+z*random.nextGaussian();
        if(newy<0){
            //if y is negative then wrap its position around the "environment"
            //to prevent the case where x is way too small (less than minus the height of the environment,
            //because of wrong speed parameter for example), we have to use a modulo
            newy=Environment.getHalfLength()*2-Math.abs(y%Environment.getHalfLength()*2);
        }else if(newy>=Environment.getHalfLength()*2){
            //if y is too large then wrap its position around the "environment"
            newy=newy%(Environment.getHalfLength()*2);
        }
        //System.out.println("...to " + x + " " + y);

        //Test if the bacteria ends up in a valid position
        if(environment.getNeighboringCells(newx, newy)[1][1].isSemiLiquid()){
            x=newx;
            y=newy;
        }
    }

    /**
     * @param direction
     * @return the concentration C of the cell(s) in the "direction"
     * (more info : #8 of description.pdf)
     */
    public double computeConcentrationInDirection(String direction) {
        double concentration=0;
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
    public void eat() {
        neighboringCells=environment.getNeighboringCells(x, y);
        //Just following the formula
        double wantedSubstratum= environment.getTimeDelta()*vCons;
        double obtainedSubstratum=0;
        for (Cell[] ctab: neighboringCells) {
            for (Cell c: ctab) {
                double obtainedFromCell=Math.min(wantedSubstratum/9, c.getConcentration()*Cell.getLength()*Cell.getLength());
                c.getBrokenDown(obtainedFromCell);
                obtainedSubstratum+=obtainedFromCell;
            }
        }
        mass=mass+kConv*obtainedSubstratum;
    }

    public Bacterium divide() {
        if(mass>=2*mIni) {
            mass = mIni;
            return new Bacterium(x, y, environment);
        } else {
            return null;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    //GETTERS
    public static double getmIni() {
        return mIni;
    }

    public static double getbDiff() {
        return bDiff;
    }

    public static double getvCons() {
        return vCons;
    }

    public static double getkConv() {
        return kConv;
    }

    public static double getVd() {
        return vd;
    }

    public double getMass() {
        return mass;
    }

    //SETTERS
    public static void setmIni(double mIni) {
        Bacterium.mIni = mIni;
    }

    public static void setbDiff(double bDiff) {
        Bacterium.bDiff = bDiff;
    }

    public static void setvCons(double vCons) {
        Bacterium.vCons = vCons;
    }

    public static void setkConv(double kConv) {
        Bacterium.kConv = kConv;
    }

    public static void setVd(double vd) {
        Bacterium.vd = vd;
    }

    @Override
    public String toString() {
        return "Bacterium{" +
                "x=" + x +
                ", y=" + y +
                ", mass=" + mass +
                '}';
    }
}
