package fr.umontpellier.iut;

import java.util.ArrayList;

public class Environment {
    private static double halfLength;
    private static int cellsPerSide;
    private double timeDelta;
    private double substratumRadius;
    private Cell[][] cells;
    private ArrayList<Bacterium> bacteria;

    public Environment(double timeDelta, double substratumRadius, int initialBacteriaAmount) {
        this.timeDelta = timeDelta;
        this.substratumRadius = substratumRadius;
        Cell.setLength((halfLength*2)/cellsPerSide);
        //build the cell array
        //to determine if the cell should have a concentration(aka instantiate it with isSubstratum=true),
        //we use the formula of the circle to represent the disc of substratum
        cells = new Cell[cellsPerSide][cellsPerSide];
        for (int i = 0; i < cellsPerSide; i++) {
            for (int j = 0; j < cellsPerSide; j++) {
                if (((j*Cell.getLength()-halfLength)*(j*Cell.getLength()-halfLength)+(i*Cell.getLength()-halfLength)*(i*Cell.getLength()-halfLength))<=substratumRadius*substratumRadius){
                    cells[i][j]=new Cell(i, j, this,true);
                } else {
                    cells[i][j]=new Cell(i, j, this,false);
                }
            }
        }
        //build the bacterium ArrayList and scatter them randomly but not in the substratum
        bacteria = new ArrayList<>();
        double bactx=halfLength;
        double bacty=halfLength;
        for (int i = 0; i < initialBacteriaAmount; i++) {
            do {
                bactx = Math.random() * (halfLength * 2);
                bacty = Math.random() * (halfLength * 2);
            } while(((bactx-halfLength)*(bactx-halfLength)+(bacty-halfLength)*(bacty-halfLength))<=substratumRadius*substratumRadius);
            bacteria.add(new Bacterium(bactx, bacty, this));
            System.out.println("Created Bacteria at" + bactx + ", " + bacty);
        }
    }

    /**
     * Runs the main algorithm for one tick (=timeDelta, the smaller unit of time defined in the simulation)
     * (more info : 4/ of description.pdf)*/

    public void tick() {
        System.out.println("diffusing");
        for (Cell[] ctab: cells) {
            for (Cell cell: ctab) {
                cell.diffuse();
            }
        }
        System.out.println("moving");
        for (Bacterium b: bacteria) {
            b.move();
        }
        System.out.println("eating");
        for (Bacterium b: bacteria) {
            b.eat();
        }
        System.out.println("dividing");

        //We create a temporary ArrayList in order to avoid modifying the one we are iterating on.
        Bacterium dividedBacterium;
        ArrayList<Bacterium> tempNewBacteria = new ArrayList<>();
        for (Bacterium b: bacteria) {
            dividedBacterium=b.divide();
            //If the bacteria returns a valid bacterium, add it to the temporary list
            if (dividedBacterium!=null){
                tempNewBacteria.add(dividedBacterium);
            }
        }
        if (!tempNewBacteria.isEmpty()){
            bacteria.addAll(tempNewBacteria);
        }
    }

    /**
     * @param x
     * @param y
     * @return an array of all the cells surrounding and including position x, y (3*3 area)
     */
    public Cell[][] getNeighboringCells(double x, double y) {
        //Convert to cell coordinates (for the table)
        //eg: 1-10 is 0 ; 11-20 is 1, etc... is the cell is 10 wide
        //USING DEPRECATED CODE => NEED TO REDO
        int centerCellX = new Double((x-1-(x-1)%Cell.getLength())/Cell.getLength()).intValue();
        int centerCellY = new Double((y-1-(y-1)%Cell.getLength())/Cell.getLength()).intValue();
        Cell neighbors[][] = new Cell[3][3];
        int ypos = 0;
        //compute the X position of the cells
        int x1 = centerCellX-1;
        if (x1<0) {
            x1=cellsPerSide-1;
            } else if (x1>cellsPerSide-1) {
                    x1=0;
                }
        int x2 = centerCellX+1;
        if (x2<0) {
            x2=cellsPerSide-1;
            } else if (x2>cellsPerSide-1) {
                    x2=0;
                }

        //populate the returning table
        for (int i = 0; i < 3; i++) {
            //compute the Y position of the cells
            if (centerCellY-1+i<0) {
                ypos=cellsPerSide-1;
            } else if (centerCellY-1+i>cellsPerSide-1) {
                ypos=0;
            } else {
                ypos=centerCellY-1+i;
            }
//            System.out.println("x="+x1+" y="+ypos);
            neighbors[0][i]=cells[x1][ypos];
//            System.out.println("x="+centerCellX+" y="+ypos);
            neighbors[1][i]=cells[centerCellX][ypos];
//            System.out.println("x="+x2+" y="+ypos);
            neighbors[2][i]=cells[x2][ypos];
        }

        return neighbors;
    }

    //GETTERS
    public double getTimeDelta() {
        return timeDelta;
    }

    public static double getHalfLength() {
        return halfLength;
    }

    //SETTERS
    public static void setHalfLength(double halfLength) {
        Environment.halfLength = halfLength;
    }

    public static void setCellsPerSide(int cellsPerSide) {
        Environment.cellsPerSide = cellsPerSide;
    }
}
