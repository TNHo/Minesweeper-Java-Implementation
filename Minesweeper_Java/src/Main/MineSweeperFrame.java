/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *      FILE:       MineSweeperFrame.java 
 *      DATE:       2/6/2023 
 *      AUTHOR:     TNHo
 *      VERSION: 1.0
 *      PURPOSE:    Where GUI stuff happens for our minesweeper game.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package Main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MineSweeperFrame extends javax.swing.JPanel implements MouseListener {

    /**
     * Creates new form MineSweeperFrame
     */
    public MineSweeperFrame() {
        initComponents();
    }
    
    // Set up variables
    private int xCord;
    private int yCord;
    private boolean started = false;
    private boolean firstClick = true; // The first click can never be a mine
    private int mineNum = 12;
    private int startX = 495;
    private int startY = 495;
    private int startXOLD = 495;
    private int startYOLD = 495;
    private int tileSize = 55;
    private int flaggedTilesNum = 0;
    public static int height = 9;
    public static int width = 9;
    public static Clip placeXSnd, placeOSnd;
    public static MineSweeperTile[][] mineTiles;
    private boolean wentBoom = false;
    private static long startSec = System.currentTimeMillis();
    JFrame wl = new JFrame();
    private static int countedSec = 0;
    private Random rand = new Random();
    //private Random rand = new Random(height * width); // Generate the same seed every time for testing purposes
    public void resetti(int h, int w, int mn, int le, int wi, int ts) {
        removeMouseListener(this);
        height = h;
        width = w;
        mineNum = mn;
        startX = le;
        startY = wi;
        startXOLD = le;
        startYOLD = wi;
        tileSize = ts;
        started = false;
        firstClick = true;
        mineTiles = new MineSweeperTile[height][width];
        repaint();
    }
    
    // The mouse events
    public void mouseClicked(MouseEvent e) { // NOT IMPLEMENTED
    }
    public void mousePressed(MouseEvent e) { // NOT IMPLEMENTED 
    }
    public void mouseExited(MouseEvent e) { // NOT IMPLEMENTED 
    }
    public void mouseEntered(MouseEvent e) { // NOT IMPLEMENTED 
    }
    public void mouseReleased(MouseEvent e) {  
        // Get our clicked tile
        xCord = e.getX() / tileSize;
        yCord = e.getY() / tileSize;
        try {
            if (e.getButton() == MouseEvent.BUTTON1) { // LEFT
                leftClick();
                repaint();
            } else if (e.getButton() == MouseEvent.BUTTON3) { // RIGHT
                rightClick();
            }
        } catch(Exception err) {
            repaint();
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (started == false) {
            mineSweeperGame(g);
        }
        timer();
        mineSweeperGUI(g);
    }
    
    /*
     * Set up the minesweeper tile board. This method should only be called once
     * at the start of new every game. 
     */
    public void mineSweeperGame(Graphics g) {
        mineTiles = new MineSweeperTile[height][width];
        mineTiles = MineSweeperModel.gridGen(height, width, mineNum, rand, mineTiles); //Set up grid
        firstClick = true;
        started = true;
        wentBoom = false;
        countedSec = 0;
        flaggedTilesNum = 0;
        startSec = System.currentTimeMillis();
        try {
            this.addMouseListener(this); // Allows us to have mouse inputs
        } catch(Exception err) {
            System.out.println(err);
        }
    }
    
    public void mineSweeperGUI(Graphics g) {
        // Color vars
        Color myGray = new Color(211, 211, 211); // Covers
        Color black = new Color(0, 0, 0); // Grid
        Color red = new Color(252, 3, 3); //3
        Color darkRed = new Color(150, 50, 50); // 5
        Color blu = new Color(38, 0, 255); // 1
        Color darkBlu = new Color(17, 0, 115); // 4
        Color ligBlu = new Color(62, 138, 214); // 6
        Color green = new Color(8, 158, 45); // 2
        Color purp = new Color(159, 16, 199); // 7
        Color grey = new Color(124, 124, 125); // 8
        // Draw our time
        g.setColor(black);
        g.drawString("Time elapsed (seconds):", 10, startYOLD+30);
        g.drawString(Integer.toString(countedSec), 180, startYOLD+30);
        // Draw our grey tiles (unclicked) 
        g.setColor(myGray);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (mineTiles[row][col].getUnCovered() == false) {
                    g.fillRect(row * tileSize, col * tileSize, tileSize, tileSize);
                }
            }
        }
        // Draw our grid lines
        g.setColor(black);
        for (int r = 0; r < height; r++) {
            g.drawLine(startX, 0, startX, startYOLD);
            startX = startX - tileSize;
        }
        for (int c = 0; c < width; c++) {
            g.drawLine(0, startY, startXOLD, startY);
            startY = startY - tileSize;
        }
        // Resets our startX and startY values so the grid can properly be
        // drawn upon the next repaint() call.
        startX = startXOLD;
        startY = startYOLD;
        // SET COLORS FOR THE NUMBERS, FLAGS, AND MINES
        for (int ro = 0; ro < height; ro++) {
            for (int co = 0; co < width; co++) {
                if (mineTiles[ro][co].getUnCovered() == true && mineTiles[ro][co].getMine() == false && mineTiles[ro][co].getadjMines() != 0) {
                    if (mineTiles[ro][co].getadjMines()==1) {
                        g.setColor(blu);
                    }
                    if (mineTiles[ro][co].getadjMines()==2) {
                        g.setColor(green);
                    }
                    if (mineTiles[ro][co].getadjMines()==3) {
                        g.setColor(red);
                    }
                    if (mineTiles[ro][co].getadjMines()==4) {
                        g.setColor(darkBlu);
                    }
                    if (mineTiles[ro][co].getadjMines()==5) {
                        g.setColor(darkRed);
                    }
                    if (mineTiles[ro][co].getadjMines()==6) {
                        g.setColor(ligBlu);
                    }
                    if (mineTiles[ro][co].getadjMines()==7) {
                        g.setColor(purp);
                    }
                    if (mineTiles[ro][co].getadjMines()==8) {
                        g.setColor(grey);
                    }
                    g.drawString(Integer.toString(mineTiles[ro][co].getadjMines()), ((ro + 1) * tileSize) - (tileSize / 2), ((co + 1) * tileSize) - (tileSize / 2));
                }
                if (mineTiles[ro][co].getUnCovered() == true && mineTiles[ro][co].getMine() == true) {
                    g.setColor(black);
                    g.drawString("M", ((ro + 1) * tileSize) - (tileSize / 2), ((co + 1) * tileSize) - (tileSize / 2));
                }
                if (mineTiles[ro][co].getUnCovered() == false && mineTiles[ro][co].getFlagged() == true) {
                    g.setColor(black);
                    g.drawString("F", ((ro + 1) * tileSize) - (tileSize / 2), ((co + 1) * tileSize) - (tileSize / 2));
                }
            }
        }
    }
    
    public void rightClick() {
        if (mineTiles[xCord][yCord].getUnCovered()==false&&mineTiles[xCord][yCord].getFlagged() == false) {
            mineTiles[xCord][yCord].setFlag(true);
            if(mineTiles[xCord][yCord].getMine()==true)
                flaggedTilesNum = flaggedTilesNum + 1;
        } else if(mineTiles[xCord][yCord].getFlagged() == true) {
            mineTiles[xCord][yCord].setFlag(false);
            if(mineTiles[xCord][yCord].getMine()==true)
                flaggedTilesNum = flaggedTilesNum - 1;
        }
        if(flaggedTilesNum == mineNum) {
            System.out.println("YOU WON!");
            JDialog settingsDialog = new JDialog(wl, "You won!", true);

            settingsDialog.setSize(400, 300);
            settingsDialog.setVisible(true);
            flaggedTilesNum = flaggedTilesNum - 1;
        }
        repaint();
    }
    
    public void leftClick() {
        // Our first click can never be a mine, move the mine to another tile if the first click would've been a mine
        if (wentBoom == false && firstClick == true && mineTiles[xCord][yCord].getMine()==true) {
            mineTiles[xCord][yCord].setMine(false);
            int mineTileX = 0;
            int mineTileY = 0;
            while (true) {
                mineTileX = rand.nextInt(height);
                mineTileY = rand.nextInt(width);
                if ((mineTileX!=xCord)&&(mineTileY!=yCord)&&mineTiles[mineTileX][mineTileY].getMine()!=true) 
                    break;
            }
            mineTiles[mineTileX][mineTileY].setMine(true);
            // REDO THE ADJACENT MINE TILES
            MineSweeperModel.addAdjM(MineSweeperModel.mineGrid, mineTiles, height, width);
        }
        firstClick = false;
        // Call recursive method to uncover tiles
        boolean isFirstClick = true;
        if (mineTiles[xCord][yCord].getMine() == false) 
            mineTiles = MineSweeperModel.uncoverTiles(mineTiles, xCord, yCord, isFirstClick, height, width);
        
        // Don't click on a mine or you will go BOOM BOOM!
        if (mineTiles[xCord][yCord].getMine() == true&&wentBoom==false) {
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    mineTiles[r][c].setCover(true);
                }
            }
            //Play sounds
            playSound("sounds/mineexplodes.wav"); // KABOOM, YOU LOST :(
            //playSound("sounds/lol_u_died.wav"); // Don't play this unless april fools
            
            wentBoom = true;
            firstClick = true;
            System.out.println("You lost");
            // Open a new window
            JDialog settingsDialog = new JDialog(wl, "You lost!", true);

            settingsDialog.setSize(400, 300);
            //settingsDialog.setVisible(true);
            // END
        }
    }
    
    public void playSound(String filePath) {
        //Load sounds
        Clip soundEffect; //Clips to be played
        //Load up sound file
        soundEffect = null;
        File soundFile = new File(filePath);//folder in project
        try {
            soundEffect = AudioSystem.getClip();
            soundEffect.open(AudioSystem.getAudioInputStream(soundFile));
            soundEffect.setFramePosition(0);
            soundEffect.start(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //Count our seconds
    public void timer() {
        if(firstClick!=true && wentBoom==false) {
            long now = System.currentTimeMillis();
            countedSec = (int)((now-this.startSec)/1000);
        } 
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
