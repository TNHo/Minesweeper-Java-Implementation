/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *      FILE:       MineSweeperModel.java
 *      DATE:       2/6/2023
 *      AUTHOR:     TNHo
 *      VERSION:    1.0
 *      PURPOSE:    Contains the 2d arrays the we will use to represent the
 *                  board. Mines will be randomly placed.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package Main;

import java.util.Random;

public class MineSweeperModel {
    public static String[][] mineGrid;
    
    public static MineSweeperTile[][] gridGen(int height, int width, int mineNumber, Random rand, MineSweeperTile[][] mineTiles) {
        mineGrid = new String[height][width];
        int mineCount = 0;
        // Fill in the rest
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                mineGrid[r][c] = "0";
                mineTiles[r][c] = new MineSweeperTile();
                mineTiles[r][c].setClick(false);
            }
        }
        
        // Figure out the mine pos
        while (mineCount < mineNumber) {
            int mineTileX = 0;
            int mineTileY = 0;
            // If the positions for the mine matches, keep looping until it doesn't
            while (true) {
                mineTileX = rand.nextInt(height);
                mineTileY = rand.nextInt(width);
                if (mineTiles[mineTileX][mineTileY].getMine()!=true) 
                    break;
            }
            mineGrid[mineTileX][mineTileY] = "m";
            mineTiles[mineTileX][mineTileY].setMine(true);
            mineCount = mineCount + 1;
        }
        // Check tiles for adjacent mines
        mineTiles = addAdjM(mineGrid, mineTiles, height, width);
        
        // Print to console for debugging
        String printOut = "";
        for (int pr = 0; pr < height; pr++) {
            for (int pc = 0; pc < width; pc++) {
                printOut = printOut + mineGrid[pr][pc] + " ";
            }
            printOut = printOut + "\n";
        }
        return mineTiles;
    }
    
    /* NOTE TO SELF: DON'T DO THAT, THAT LOOKS BAD!
     */ 
    public static MineSweeperTile[][] addAdjM(String[][] mineGD, MineSweeperTile[][] mineTl, int height, int width) {
        for (int rcm = 0; rcm < height; rcm++) {
            for (int ccm = 0; ccm < width; ccm++) {
                if (mineTl[rcm][ccm].getMine()!=true) {
                    int adjMine = 0;
                    //Check directly beside the tile
                    try {
                        if (mineTl[rcm][ccm-1].getMine()==true)
                            adjMine = adjMine + 1;
                    } catch (Exception e) {
                    } // Do nothing
                    try {
                        if (mineTl[rcm][ccm+1].getMine()==true)
                            adjMine = adjMine + 1;
                    } catch (Exception e) {
                    } // Do nothing
                    //Check above row
                    try {
                        if (mineTl[rcm-1][ccm-1].getMine()==true)
                            adjMine = adjMine + 1;
                    } catch (Exception e) {
                    } // Do nothing
                    try {
                        if (mineTl[rcm-1][ccm].getMine()==true)
                            adjMine = adjMine + 1;
                    } catch (Exception e) {
                    } // Do nothing
                    try {
                        if (mineTl[rcm-1][ccm+1].getMine()==true)
                            adjMine = adjMine + 1;
                    } catch (Exception e) {
                    } // Do nothing
                    //Check the below row
                    try {
                        if (mineTl[rcm+1][ccm-1].getMine()==true)
                            adjMine = adjMine + 1;
                    } catch (Exception e) {
                    } // Do nothing
                    try {
                        if (mineTl[rcm+1][ccm].getMine()==true)
                            adjMine = adjMine + 1;
                    } catch (Exception e) {
                    } // Do nothing
                    try {
                        if (mineTl[rcm+1][ccm+1].getMine()==true)
                            adjMine = adjMine + 1;
                    } catch (Exception e) {
                    } // Do nothing
                    // AAAAHHHHHH
                    mineTl[rcm][ccm].setAdjMine(adjMine);
                    mineGD[rcm][ccm] = Integer.toString(mineTl[rcm][ccm].getadjMines()); 
                }
            }
        }
        return mineTl;
    }
    
    /* 
     * A method that uses recursion to uncover tiles.
     * It goes through some if statements to check which values to pass into the next recursion ca-
     * WHAT THE HECK IS THIS?! I FEEL LIKE I HAVE COMMITED WAR CRIMES!
     */
    public static MineSweeperTile[][] uncoverTiles(MineSweeperTile[][] mineTl, int xCord, int yCord, boolean isFirstClick, int height, int width) {
        mineTl[xCord][yCord].setCover(true);
        try {
            // START
            try {
                if(mineTl[xCord-1][yCord+1].getadjMines()>0) 
                    mineTl[xCord-1][yCord+1].setCover(true);
            } catch(Exception e) {
            }
            try {
                if(mineTl[xCord][yCord+1].getadjMines()>0) 
                    mineTl[xCord][yCord+1].setCover(true);
            } catch(Exception e) {
            }
            try {
                if(mineTl[xCord+1][yCord+1].getadjMines()>0) 
                    mineTl[xCord+1][yCord+1].setCover(true);
            } catch(Exception e) {
            }
            try {
                if(mineTl[xCord-1][yCord].getadjMines()>0) 
                    mineTl[xCord-1][yCord].setCover(true);
            } catch(Exception e) {
            }
            try {
                if(mineTl[xCord+1][yCord].getadjMines()>0) 
                    mineTl[xCord+1][yCord].setCover(true);
            } catch(Exception e) {
            }
            try {
                if(mineTl[xCord-1][yCord-1].getadjMines()>0) 
                    mineTl[xCord-1][yCord-1].setCover(true);
            } catch(Exception e) {
            }
            try {
                if(mineTl[xCord][yCord-1].getadjMines()>0) 
                    mineTl[xCord][yCord-1].setCover(true);
            } catch(Exception e) {
            }
            try {
                if(mineTl[xCord+1][yCord-1].getadjMines()>0) 
                    mineTl[xCord+1][yCord-1].setCover(true);
            } catch(Exception e) {
            }
            // END
            isFirstClick = false;
            if (mineTl[xCord][yCord].getadjMines()==0&&xCord-1 >= 0 && mineTl[xCord-1][yCord].getUnCovered()==false && mineTl[xCord-1][yCord].getMine()==false) {
                mineTl = uncoverTiles(mineTl, xCord-1, yCord, isFirstClick, height, width);
            }
            if (mineTl[xCord][yCord].getadjMines()==0&&xCord+1 < width && mineTl[xCord+1][yCord].getUnCovered()==false && mineTl[xCord+1][yCord].getMine()==false) {
                mineTl = uncoverTiles(mineTl, xCord+1, yCord, isFirstClick, height, width);
            }
            if (mineTl[xCord][yCord].getadjMines()==0&&yCord-1 >= 0 && mineTl[xCord][yCord-1].getUnCovered()==false && mineTl[xCord][yCord-1].getMine()==false) {
                mineTl = uncoverTiles(mineTl, xCord, yCord-1, isFirstClick, height, width);
            }
            if (mineTl[xCord][yCord].getadjMines()==0&&yCord+1 < height && mineTl[xCord][yCord+1].getUnCovered()==false && mineTl[xCord][yCord+1].getMine()==false) {
                mineTl = uncoverTiles(mineTl, xCord, yCord+1,isFirstClick, height, width);
            }

            return mineTl;
        } catch(Exception e) {
            System.out.println(e);
            return mineTl;
        }
    }
}
