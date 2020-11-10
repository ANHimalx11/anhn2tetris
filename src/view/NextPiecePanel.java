/*
 * Anh Nguyen 
 * TCSS305C - Winter 
 * Assignment 6b - Tetris 
 * NextPiecePanel.java 
 * This class displays the next piece in the game.
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Board;
import model.Point;
import model.TetrisPiece;

/**
 * This class creates the panel where the next Tetris piece is shown.
 * 
 * @author Anh Nguyen
 * @version 1.0
 *
 */
public class NextPiecePanel extends JPanel implements Observer {
    // Constants
    /**
     * This is an auto-generated serial number.
     */
    private static final long serialVersionUID = -3254087614079880441L;

    /** This is the initial width of the panel. */
    private static final int INIT_WIDTH = 300;

    /** This is the x-axis offset for correctly displaying the next piece. */
    private static final int X_OFFSET = 40;

    /** This is the y-axis offset for correctly displaying the next piece. */
    private static final int Y_OFFSET = 150;

    /** This is the initial offset of the blocks on the board. */
    private static final int INIT_OFFSET = INIT_WIDTH / 10;

    // Instances
    /**
     * This is a list of points.
     */
    private Point[] myPointsList;

    /**
     * This is the constructor for NextPiecePanel.
     * 
     * @param theBoard the board that is sent from the board class.
     */
    public NextPiecePanel(final Board theBoard) {
        super();

        // pass in board and add observer.
        final Board littleBird = theBoard;
        littleBird.addObserver(this);

        // call setUp() to make appearance.
        setUpLook();
    }

    // overriding paint component
    @Override
    public void paintComponent(final Graphics theG) {
        super.paintComponent(theG);
        final Graphics2D g2D = (Graphics2D) theG;
        g2D.setPaint(Color.WHITE);

        if (myPointsList != null) {
            // draw points with x and y offset from flip.
            for (int i = 0; i < myPointsList.length; i++) {
                g2D.fill3DRect(myPointsList[i].x() * INIT_OFFSET + X_OFFSET,
                               -myPointsList[i].y() * INIT_OFFSET + Y_OFFSET, INIT_OFFSET,
                               INIT_OFFSET, true);
            }
        }
    }

    @Override
    public void update(final Observable theObservable, final Object theArgs) {
        if (theArgs instanceof TetrisPiece) {
            myPointsList = ((TetrisPiece) theArgs).getPoints();
        }
        repaint();
    }

    /**
     * This method helps to create the appearance of the next piece panel.
     */
    private void setUpLook() {
        //make a gray line and set the title of the panel.
        final Border grayline = BorderFactory.createLineBorder(Color.GRAY);
        final TitledBorder title = BorderFactory.createTitledBorder(grayline, "Next Piece");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);

        setBackground(Color.LIGHT_GRAY);
    }
}
