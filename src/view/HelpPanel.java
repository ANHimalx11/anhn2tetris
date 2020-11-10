/*
 * Anh Nguyen 
 * TCSS305C - Winter 
 * Assignment 6b - Tetris 
 * HelpPanel.java 
 * This class creates the Help display for the game.
 * 
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * This class creates the Help display for the game.
 * 
 * @author Anh Nguyen
 * @version 1.2
 *
 */
public class HelpPanel extends JPanel {

    // Class constants
    /**
     * This is an auto-generated serial ID.
     */
    private static final long serialVersionUID = -1129735927402799521L;

    /**
     * Number of keys to press.
     */
    private static final int NUMBER_KEYROWS = 7;

    /**
     * Number of columns in the help panel.
     */
    private static final int NUMBER_COL = 1;
    
    /** This is the size of the help panel. */
    private static final int PANEL_SIZE = 200;

    // Instance fields

    /**
     * This is the constructor that makes a HelpPanel object.
     */
    public HelpPanel() {
        super();

        setLayout(new GridLayout(NUMBER_KEYROWS, NUMBER_COL, 0 , 0));

        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
 
        makeLabel();
        
        setUpLook();
    }

    /**
     * This method helps to create the appearance of the next piece panel.
     */
    private void setUpLook() {
        //make a gray line and set the title of the panel.
        final Border grayline = BorderFactory.createLineBorder(Color.GRAY);
        final TitledBorder title = BorderFactory.createTitledBorder(grayline, "Commands");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);

        setBackground(Color.LIGHT_GRAY);

    }

    /**
     * This method creates the labels inside the help panel.
     */
    private void makeLabel() {
        
        final ArrayList<String> labelList = new ArrayList<String>();
        labelList.add("Move Left = A");
        labelList.add("Move Right = D");
        labelList.add("Move Down = S");
        labelList.add("Move Drop = SPACE");
        labelList.add("Move Rotate CW = W");
        labelList.add("Move Rotate CCW = Q");
        labelList.add("Pause = P");
        
        //Iterates through a list of string and makes a new label for it
        for (final String s : labelList) {
            final JLabel infoLabel = new JLabel(s);
            add(infoLabel);
        }
    }

}
