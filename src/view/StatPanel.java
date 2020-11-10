/*
 * Anh Nguyen 
 * TCSS305C - Winter 
 * Assignment 6b - Tetris 
 * StatPanel.java 
 * This class will create a display for info of the game.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Board;
import model.TetrisPiece;

/**
 * This class creates an info panel for the game.
 * 
 * @author Anh Nguyen
 * @version 1.2
 *
 */
public class StatPanel extends JPanel implements Observer {
    // constants
    /**
     * This is an auto-generated serial ID.
     */
    private static final long serialVersionUID = 4691417946172572337L;

    /** The number of rows in the grid. */
    private static final int NUMBER_KEYROWS = 4;

    /** The number of columns in the grid. */
    private static final int NUMBER_COL = 1;

    /** The preferred size of the panel. */
    private static final int PANEL_SIZE = 200;

    /** The X location offset for drawing strings. */
    private static final int X_OFFSET = 100;

    /** The X location offset for drawing strings. */
    private static final int X2_OFFSET = 140;

    /** The Y location offset for drawing the score. */
    private static final int Y1_OFFSET = 44;

    /** The Y location offset for drawing the current level. */
    private static final int Y2_OFFSET = 89;

    /** The Y location offset for drawing the lines until the next level. */
    private static final int Y3_OFFSET = 133;

    /** The Y location offset for drawing the total number of lines cleared. */
    private static final int Y4_OFFSET = 177;

    /** The point value for each piece added. */
    private static final int PIECE_PT = 4;

    /** The score value of clearing 1 line. */
    private static final int LINE_1_PTS = 40;

    /** The score value of clearing 2 lines. */
    private static final int LINE_2_PTS = 100;

    /** The score value of clearing 3 lines. */
    private static final int LINE_3_PTS = 300;

    /** The score value of clearing 4 lines. */
    private static final int LINE_4_PTS = 1200;

    /** The decrement of the timer to speed up the falling pieces. */
    private static final int TIMER_DECREM = 80;
    
    /** This is the limit of the timer decrememnt. */
    private static final int TIMER_DECREM_CAP = 1000;

    /** The int value of 5 used in scoring and level upkeep. */
    private static final int FIVE = 5;

    // instance fields
    /** The current level of the game. */
    private int myLevel = 1;

    /** The score made by the user. */
    private int myScore;

    /** The total lines cleared by the user. */
    private int myTotalLine;

    /** The total lines left to clear until the next level. */
    private int myLinesLeft = FIVE;

    /** The arrayList of frozen block arrays. */
    private Integer[] myFrozenList;

    /**
     * This is the constructor for the class.
     * 
     * @param theBoard gets sent the board from Tetris GUI.
     */
    public StatPanel(final Board theBoard) {
        super();

        // pass in board and add observer.
        final Board littleBoard = theBoard;
        littleBoard.addObserver(this);

        setLayout(new GridLayout(NUMBER_KEYROWS, NUMBER_COL, 0, 0));

        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));

        setUpLook();
    }

    @Override
    public void update(final Observable theObservable, final Object theArgs) {

        // if theArgs is a list of Integers
        if (theArgs instanceof Integer[]) {
            myFrozenList = (Integer[]) theArgs; // already checked
                                                // through "instanceof"
                                                // keyword
            calcScore();
            increaseLevel();
        }

        // if theArgs is a tetris piece
        if (theArgs instanceof TetrisPiece) {
            myScore += PIECE_PT;
        }
        repaint();
    }

    // overriding paint component
    @Override
    public void paintComponent(final Graphics theG) {
        super.paintComponent(theG);
        final Graphics2D g2D = (Graphics2D) theG;

        // draw score in the stats panel.
        g2D.drawString(String.valueOf(myScore), X_OFFSET, Y1_OFFSET);

        // draw my current level in the stats panel.
        g2D.drawString(String.valueOf(myLevel), X_OFFSET, Y2_OFFSET);

        // draw lines left to clear to next level
        g2D.drawString(String.valueOf(myLinesLeft), X2_OFFSET, Y3_OFFSET);

        // draw total lines cleared
        g2D.drawString(String.valueOf(myTotalLine), X2_OFFSET, Y4_OFFSET);

    }

    /**
     * This method helps to create the appearance of the next piece panel.
     */
    private void setUpLook() {
        // make a gray line and set the title of the panel.
        final Border grayline = BorderFactory.createLineBorder(Color.GRAY);
        final TitledBorder title = BorderFactory.createTitledBorder(grayline, "Stats");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);

        setBackground(Color.LIGHT_GRAY);

        makeLabel();

    }

    /**
     * This method creates the labels inside the help panel.
     */
    private void makeLabel() {

        final ArrayList<String> labelList = new ArrayList<String>();
        labelList.add("My Score: ");
        labelList.add("Current LVL: ");
        labelList.add("Lines left till next LVL: ");
        labelList.add("Total lines cleared: ");

        // Iterates through a list of string and makes a new label for it
        for (final String s : labelList) {
            final JLabel infoLabel = new JLabel(s);
            add(infoLabel);
        }
    }

    /**
     * This method calculates the score made by clearing lines.
     * 
     * @return myScore the score the user obtains.
     */
    private int calcScore() {

        // magic numbers to
        final int threeLinesCleared = 3;
        final int fourLinesCleared = 4;

        if (myFrozenList != null) {
            final int linesCleared = myFrozenList.length;
            // add to total number of lines.
            myTotalLine += myFrozenList.length;

            // # of lines mean different pts added.
            switch (linesCleared) {
                case 1:
                    myScore += myLevel * LINE_1_PTS;
                    break;
                case 2:
                    myScore += myLevel * LINE_2_PTS;
                    break;
                case threeLinesCleared:
                    myScore += myLevel * LINE_3_PTS;
                    break;
                case fourLinesCleared:
                    myScore += myLevel * LINE_4_PTS;
                    break;
                default:
                    break;
            }
        }
        return myScore;
    }

    /** This method clears the values of myTotalLines and myScore. */
    public void clearStats() {
        myScore = 0;
        myTotalLine = 0;
        myLinesLeft = FIVE;
        myLevel = 1;
    }

    /**
     * This is a getter for my level's speed.
     * 
     * @return the speed the value of the level.
     */
    public int getLevelSpeed() {
        int levelSpeed = myLevel * TIMER_DECREM;

        // to account for illegal argument in timer delay.
        if (myLevel * TIMER_DECREM >= TIMER_DECREM_CAP) {
            levelSpeed = TIMER_DECREM_CAP;
        }
        return levelSpeed;
    }

    /**
     * This is a getter for the current level.
     * 
     * @return myLevel the current level of the game.
     */
    public int getLevel() {
        return myLevel;
    }

    /**
     * This method increases the level of the game by checking lines left and
     * total lines.
     */
    private void increaseLevel() {
        // decrease myLinesLeft
        myLinesLeft = myLinesLeft - myFrozenList.length;
        // when the remainder is 0, level up.
        if (myTotalLine % FIVE == 0 || FIVE - myLinesLeft == 0) {
            myLevel++;
            myLinesLeft = FIVE;
        }
        if (myLinesLeft < 0) {
            myLevel++;
            myLinesLeft = FIVE - Math.abs(myLinesLeft);
        }
    }
}
