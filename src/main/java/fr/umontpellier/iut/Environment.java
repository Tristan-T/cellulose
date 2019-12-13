package fr.umontpellier.iut;

import java.util.ArrayList;

public class Environment {
    private static double halfLength;
    private static int cellsPerSide;
    private static double substratumRadius;
    private static double timeDelta;
    private Cell[][] cells;
    private ArrayList<Bacterium> bacteria;

    public Environment(double timeDelta, int initialBacteriaAmount) {
        Environment.timeDelta = timeDelta;
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
        }
    }

    /**
     * Runs the main algorithm for one tick (=timeDelta/timeDeltaSubdivision, the smaller unit of time defined in the simulation)
     * (more info : 4/ of description.pdf)*/

    public void tick() {
        //System.out.println("diffusing");
        for (Cell[] ctab: cells) {
            for (Cell cell: ctab) {
                cell.diffuse();
            }
        }
        //System.out.println("moving");
        for (Bacterium b: bacteria) {
            b.move();
        }
        //System.out.println("eating");
        for (Bacterium b: bacteria) {
            b.eat();
        }
        //System.out.println("dividing");

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
        //System.out.println("getting neighboring cells of point (x,y)= " + x + " " + y);
        int centerCellX = ((int) (x/Cell.getLength()))%cellsPerSide;
        int centerCellY = ((int) (y/Cell.getLength()))%cellsPerSide;
        //System.out.println("... of cell (x,y)=" + centerCellX + " " + centerCellY);
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

    //returns the sum of the mass of the bacteria
    public double getBiomass(){
        double sum = 0;
        for (Bacterium b: bacteria) {
            sum = sum + b.getMass();
        }
        return sum;
    }

    public double[][] getBacteriaData(){
        double sum = 0;
        double[][] position = new double[bacteria.size()+1][3];
        int i = 0;
        for (Bacterium b: bacteria) {
            position[i][0] = b.getX();
            position[i][1] = b.getY();
            position[i][2] = b.getMass();
            sum = sum + b.getMass();
            i++;
        }
        position[bacteria.size()][0] = sum;
        position[bacteria.size()][1] = 0;

        return position;
    }

    public double[][] getCellData(){
//        DecimalFormat df = new DecimalFormat("#.#");
        double sum = 0;
//        double[][] result = new double [cellsPerSide][cellsPerSide];
        double[][] result = new double [cellsPerSide+1][cellsPerSide];
        for (int i = 0; i<cellsPerSide ; i++) {
            for (int j = 0; j<cellsPerSide ; j++) {
//                result[i][j] = Double.parseDouble(df.format(new Double (cells[i][j].getConcentration())));
                result[i][j] = cells[i][j].getConcentration();
                sum = sum + cells[i][j].getConcentration();
            }
        }
        result[cellsPerSide][0]=sum;
        return result;
    }

    //GETTERS
    public double getTimeDelta() {
        return timeDelta;
    }

    public static double getHalfLength() {
        return halfLength;
    }

    public static int getCellsPerSide() {
        return cellsPerSide;
    }

    public static double getSubstratumRadius() {
        return substratumRadius;
    }

    //SETTERS
    public static void setHalfLength(double halfLength) {
        Environment.halfLength = halfLength;
    }

    public static void setCellsPerSide(int cellsPerSide) {
        Environment.cellsPerSide = cellsPerSide;
    }

    public static void setSubstratumRadius(double substratumRadius) {
        Environment.substratumRadius = substratumRadius;
    }
}
