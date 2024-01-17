/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *      FILE:       MineSweeperTile.java
 *      DATE:       2/6/2023
 *      AUTHOR:     TNHo
 *      VERSION:    1.0
 *      PURPOSE:    Stores the values for the tiles in the game. 
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package Main;

public class MineSweeperTile {

    public MineSweeperTile() {
        isClicked = false;
        isFLagged = false;
        hasMine = false;
        isUncovered = false;
        adjMineAmt = 0;
    }

    public boolean getClicked() {
        return isClicked;
    }
    public boolean getFlagged() {
        return isFLagged;
    }
    public boolean getMine() {
        return hasMine;
    }
    public boolean getUnCovered() {
        return isUncovered;
    }
    public int getadjMines() {
        return adjMineAmt;
    }
    public String toString() { // For debug purposes
        String iC = "";
        String iF = "";
        String hM = "";
        if (isClicked == false) {
            iC = " has not been clicked";
        } else {
            iC = " has been clicked";
        }
        if (isFLagged == false) {
            iF = " has not been flagged";
        } else {
            iF = " has been flagged";
        }
        if (hasMine == false) {
            hM = " has no mine.";
        } else {
            hM = " has a mine.";
        }
        
        return "This tile has "+adjMineAmt+","+iC+","+iF+", "+hM;
    }

    public void setMine(boolean minePos) {
        hasMine = minePos;
    }
    public void setFlag(boolean rClick) {
        isFLagged = rClick;
    }
    public void setClick(boolean isclk) {
        isClicked = isclk;
    }
    public void setAdjMine(int aMine) {
        adjMineAmt = aMine;
    }
    public void setCover(boolean uCver) {
        isUncovered = uCver;
    }

    private boolean isClicked;
    private boolean isUncovered;
    private boolean isFLagged;
    private boolean hasMine;
    private int adjMineAmt;
}
