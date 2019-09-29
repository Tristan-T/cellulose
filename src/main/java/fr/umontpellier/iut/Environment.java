package fr.umontpellier.iut;

import java.util.ArrayList;

public class Environment {
    private double halfLength;
    private int cellsPerSide;
    private double timeDelta;
    private ArrayList<Cell> cells; //can be any other type of array
    private ArrayList<Bacterium> bacterias; //can be any other type of array

    /**
     * Runs the main algorithm for one tick (=timeDelta, the smaller unit of time defined in the simulation)
     * (more info : 4/ of description.pdf)
     */
    public void tick() {}

    /**
     * @param x
     * @param y
     * @return an array of all the cells surrounding and including position x, y
     */
    public ArrayList<Cell> getNeighboringCells(int x, int y) {return null;}
}
