/*
 * Anh Nguyen 
 * TCSS305C - Winter 
 * Assignment 6b - Tetris 
 * TetrisGameBoard.java 
 * This class sets up all the components of the board.
 * 
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Block;
import model.Board;

/**
 * This class creates sets up all the display components.
 * 
 * @author Anh Nguyen
 * @version 1.3
 *
 */
public class TetrisGameBoard extends JPanel implements Observer {

    // Class Constants
    /**
     * This is an auto-generated serial ID.
     */
    private static final long serialVersionUID = 3654590533603407509L;

    /** The initial delay timer. */
    private static final int INIT_DELAY = 1000;

    /** This is the initial width of the game board. */
    private static final int INIT_WIDTH = 300;

    /** This is the initial height of the game board. */
    private static final int INIT_HEIGHT = 600;

    /** This is the initial offset of the blocks on the board. */
    private static final int INIT_OFFSET = INIT_WIDTH / 10;
    // Instance fields
    /**
     * This is a tetris board object.
     */
    private final Board myBoard;

    /**
     * This is a list of an array of blocks.
     */
    private List<Block[]> myBlockList;

    /**
     * This is a timer for the game.
     */
    private final Timer myTimer;

    /** A boolean value if the game is over. */
    private Boolean myGameOver = true;

    /** This is an instance of a custom keyAdapter. */
    private final TetrisKeyListener myKeyAdapter;

    /** This is an instance of the statPanel from the GUI. */
    private final StatPanel myStatPanel;
    
    /** This is an instance of the color chosen by the user. */
    private Color myColor;

    /**
     * This is an instance of a paint object to paint the blocks.
     */
    private Paint myBlockColor;

    /**
     * This is the constructor for the game board.
     * 
     * @param theBoard the board that gets created.
     * @param theStatPanel the stat panel from the GUI.
     */
    public TetrisGameBoard(final Board theBoard, final StatPanel theStatPanel) {
        super();
        myBoard = theBoard;
        myStatPanel = theStatPanel;
        
        myBoard.addObserver(this); // add GUI as the observer of myBoard object.

        myKeyAdapter = new TetrisKeyListener(); // declare myKeyAdapter.

        // instantiate timer.
        myTimer = new Timer(INIT_DELAY, new ActionListener() {

            // anonymous inner class
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.step();
            }
        });

        goStop(); // starts and stops timer for the first game to start manually.
                  
        // setting the preferred size of the board.
        setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));

        setUpComponents();
        // instantiate the list of blocks
        myBlockList = new ArrayList<Block[]>();
    }
    
    /**
     * This method sets up the components for the TetrisGameBoard.
     */
    private void setUpComponents() {
        // setting background color of the board.
        if (myColor == null) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setColorBackgroud(myColor);
        }
        
    }

    // gets the new information from the board.
    @Override
    public void update(final Observable theObservable, final Object theArgs) {

        if (theArgs instanceof ArrayList) {
            myBlockList = (ArrayList<Block[]>) theArgs; // already checked
                                                        // through "instanceof"
                                                        // keyword
        }
        if (theArgs instanceof Boolean) {
            myGameOver = (Boolean) theArgs;
            gameOverBro();
        }
        // change the speed of the timer with each level up.
        speedUpTimer();
        // print to console
        // System.out.println(theObservable.toString());
        repaint();
    }

    // overriding paint component
    @Override
    public void paintComponent(final Graphics theG) {
        super.paintComponent(theG);
        final Graphics2D g2D = (Graphics2D) theG;
        
        //use user's color if applicable.
        if (myBlockColor == null) {
            g2D.setPaint(Color.GRAY);
        } else {
            g2D.setPaint(myBlockColor);
        }
        // counters for rows and columns
        int countRow = 0;
        int countCol = 0;

        // traverse through a list of a block list
        for (final Block[] b : myBlockList) {

            // traverse through the block list for blocks
            for (final Block p : b) {

                if (p != null) {
                    // draw blocks
                    g2D.fill3DRect(countRow * INIT_OFFSET,
                                   (INIT_HEIGHT - INIT_OFFSET) - (countCol * INIT_OFFSET),
                                   INIT_OFFSET, INIT_OFFSET, true);
                }
                countRow++;
            }
            // Reset row
            countRow = 0;
            countCol++;
        }
    }

    /**
     * This method makes this JPanel accessible to the KeyAdapterListener.
     */
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    /**
     * This method will be called if there is a game over; stops timer and
     * removes keyAdapter.
     */
    public void gameOverBro() {
        if (myGameOver) {
            // will not update timer anymore.
            myTimer.stop();
            // remove keyEvents
            removeKeyListener(myKeyAdapter);
            // indicate to user that the game is over.
            JOptionPane.showMessageDialog(getParent(), "Game Over!", "Game Over Bro", 1);
        }
    }

    /** This method will start a new game. */
    public void restart() {
        // add keyListeners
        addKeyListener(myKeyAdapter);
        // call new game from board.
        myStatPanel.clearStats();
        myBoard.newGame();
        // set gameOver to false to start game.
        setGameOver(false);
        // restart timer
        myTimer.restart();
    }

    /**
     * A private method to prevent null pointer exceptions within the Board
     * class.
     */
    private void goStop() {
        myTimer.start();
        myTimer.stop();
    }

    /** Increases the timer as the level increases. */
    private void speedUpTimer() {
        //change speed of timer when level is greater than 1.
        if (myStatPanel.getLevel() > 1) {
            myTimer.setDelay(myTimer.getInitialDelay() - myStatPanel.getLevelSpeed());
        }
    }

    // setter for myGameOver
    /**
     * This method makes the status for the current game to end.
     * 
     * @param theBoolean a value to set if the game is over or not.
     */
    public void setGameOver(final Boolean theBoolean) {
        myGameOver = theBoolean;
    }

    // getter for myGameOver
    /**
     * This method retrieves whether the game is over or not.
     * 
     * @return myGameOver A boolean value if the game is over.
     */
    public boolean isGameOver() {
        return myGameOver;
    }
    
    /**
     * This method sets the color of the background.
     * @param theColor the color the background will be.
     */
    public final void setColorBackgroud(final Color theColor) {
        myColor = theColor;
        setBackground(myColor);
    }
    
    /**
     * This method gets the user's choice of block color.
     * @param theColorBlock a color from the JColorChooser
     * @return myBlockColor the color
     */
    public final Paint getColorBlock(final Color theColorBlock) {
        myBlockColor = theColorBlock;
        return myBlockColor;
    }
    
    
//    public final Color setBlockColor(final Color theColor) {
//        return myBlockColor = theColor;       
//    }

    // ****************Inner Class****************
    /**
     * This is an inner class that takes care of the KeyPressed events.
     * 
     * @author Anh Nguyen
     * @version 1.0
     *
     */
    private class TetrisKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(final KeyEvent theEvent) {

            // create a switch statement for all the keys pressed

            final int keyPressed = theEvent.getKeyCode();
            // when timer is running key events are active.
            if (myTimer.isRunning()) {
                switch (keyPressed) {

                    case KeyEvent.VK_A:
                        myBoard.left(); // move piece to the left
                        break;
                    case KeyEvent.VK_D:
                        myBoard.right(); // move piece to the right
                        break;
                    case KeyEvent.VK_S:
                        myBoard.down(); // move piece down
                        break;
                    case KeyEvent.VK_SPACE:
                        myBoard.drop(); // drop the piece straight down
                        break;
                    case KeyEvent.VK_W:
                        myBoard.rotateCW(); // rotate piece Clockwise
                        break;
                    case KeyEvent.VK_Q:
                        myBoard.rotateCCW(); // rotate piece Counter-Clockwise
                        break;
                    case KeyEvent.VK_P:
                        myTimer.stop(); // pauses game time
                        break;
                    default:
                        break;
                }
            } else {
                if (keyPressed == KeyEvent.VK_P) {
                    myTimer.start();
                }
            }
        }
    } // END OF INNER CLASS
}
