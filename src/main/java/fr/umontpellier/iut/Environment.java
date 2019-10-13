package fr.umontpellier.iut;

import java.util.ArrayList;

public class Environment {
    private static double halfLength;
    private static int cellsPerSide;
    private double timeDelta;
    private double substratumRadius;
    private Cell[][] cells; //can be any other type of array
    private ArrayList<Bacterium> bacteria; //can be any other type of array

    public Environment(double timeDelta, double substratumRadius, int initialBacteriaAmount) {
        this.timeDelta = timeDelta;
        this.substratumRadius = substratumRadius;
        Cell.setLength(halfLength/cellsPerSide/2);
        //build the cell array
        //to determine if the cell should have a concentration(aka instantiate it with isSubstratum=true),
        //we use the formula of the circle to represent the disc of substratum
        cells = new Cell[cellsPerSide][cellsPerSide];
        for (int i = 0; i < cellsPerSide; i++) {
            for (int j = 0; j < cellsPerSide; j++) {
                if (((j*Cell.getLength()-halfLength)*(j*Cell.getLength()-halfLength)+(i*Cell.getLength()-halfLength)*(i*Cell.getLength()-halfLength))<=substratumRadius*substratumRadius){
                    cells[i][j]=new Cell(j, i, this,true);
                } else {
                    cells[i][j]=new Cell(j, i, this,false);
                }
            }
        }
        //build the bacterium ArrayList and scatter them randomly but not in the substratum
        bacteria = new ArrayList<>();
        double bactx=halfLength;
        double bacty=halfLength;
        for (int i = 0; i < initialBacteriaAmount; i++) {
            do {
                bactx = Math.random() * halfLength * 2;
                bacty = Math.random() * halfLength * 2;
            } while(((bactx-halfLength)*(bactx-halfLength)+(bacty-halfLength)*(bacty-halfLength))<=substratumRadius*substratumRadius);
            bacteria.add(new Bacterium(bactx, bacty, this));
            System.out.println("Created Bacteria at" + bactx + ", " + bacty);
        }
    }

    /**
     * Runs the main algorithm for one tick (=timeDelta, the smaller unit of time defined in the simulation)
     * (more info : 4/ of description.pdf)
     */
    public void tick() {}

    /**
     * @param x
     * @param y
     * @return an array of all the cells surrounding and including position x, y (3*3 area)
     */
    public Cell[][] getNeighboringCells(double x, double y) {
        int centerCellX = (int) Math.floor(x/Cell.getLength())-1;
        int centerCellY = (int) Math.floor(y/Cell.getLength())-1;
        Cell neighbors[][] = new Cell[3][3];

        for (int i = 0; i < 3; i++) {
            neighbors[0][i]=cells[centerCellX-1][centerCellY-i-1];
            neighbors[1][i]=cells[centerCellX][centerCellY-i-1];
            neighbors[2][i]=cells[centerCellX+1][centerCellY-i-1];
        }

        return neighbors;
    }

    //GETTERS
    public double getTimeDelta() {
        return timeDelta;
    }

    //SETTERS
    public static void setHalfLength(double halfLength) {
        Environment.halfLength = halfLength;
    }

    public static void setCellsPerSide(int cellsPerSide) {
        Environment.cellsPerSide = cellsPerSide;
    }
}
