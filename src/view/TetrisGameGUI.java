/*
 * Anh Nguyen 
 * TCSS305C - Winter 
 * Assignment 6b - Tetris 
 * TetrisGameGUI.java 
 * This class sets up all the display components.
 * 
 */

package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import model.Board;

/**
 * This class is the main frame of the tetris game.
 * 
 * @author Anh Nguyen
 * @version 1.4
 */
public class TetrisGameGUI extends JFrame {
    // Class constants
    /**
     * This is an auto-generated serial ID.
     */
    private static final long serialVersionUID = -7164531092305925798L;

    /** The number of rows in the grid layout for the east panel. */
    private static final int GRID_ROW = 3;

    /** The number of columns in the grid layout for the east panel. */
    private static final int GRID_COL = 1;

    /** This is the initial width of the panel. */
    private static final int EAST_WIDTH = 200;

    /** This is the initial width of the panel. */
    private static final int EAST_HEIGHT = 600;

    // Instance field
    /**
     * This is a HelpPanel that shows what buttons to press.
     */
    private HelpPanel myHelpInfo;

    /**
     * This is the panel that contains the info panels.
     */
    private JPanel myEastPanel;

    /**
     * This is a panel that displays the next tetris piece.
     */
    private NextPiecePanel myNextPanel;

    /** This is a panel that displays the status of the game. */
    private StatPanel myStatPanel;

    /**
     * This is the board that is put into the Center.
     */
    private TetrisGameBoard myNewBoard;

    /**
     * The board object that is created.
     */
    private final Board myBoard;

    /** This is a JMenuBar that will be added to the GUI. */
    private JMenuBar myMenuBar;
    

    /**
     * This is the constructor for the main frame.
     */
    public TetrisGameGUI() {
        super("Tetris");
        // setting location to center screen
        setLocationRelativeTo(null);

        myBoard = new Board();

        // set close operation.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // cannot resize window
        setResizable(false);
        
    }

    /**
     * This method calls another class to make a board and calls the board to
     * start game.
     */
    public void start() {

        createInfoPanels();
        // create a new game board to add the the center
        myNewBoard = new TetrisGameBoard(myBoard, myStatPanel);
        add(myNewBoard, BorderLayout.CENTER);

        // create all the panels and menu bar
        createMenubar();
        createGridPanel();

        addAllPanels();

        repaint();
        pack();
        // make all the components visible
        setVisible(true);
    }

    /**
     * This method creates a Grid Panel and puts all the info panels in place.
     */
    private void createGridPanel() {
        myEastPanel = new JPanel(new GridLayout(GRID_ROW, GRID_COL));
        add(myEastPanel, BorderLayout.EAST);

        myEastPanel.setSize(EAST_WIDTH, EAST_HEIGHT);

        myEastPanel.setBackground(Color.DARK_GRAY);
        myEastPanel.setVisible(true);
    }

    /**
     * This method creates the panels for the GUI.
     */
    private void createInfoPanels() {
        myHelpInfo = new HelpPanel();
        myNextPanel = new NextPiecePanel(myBoard);
        myStatPanel = new StatPanel(myBoard);
    }

    /**
     * This method adds all the panel to the east panel.
     */
    private void addAllPanels() {
        myEastPanel.add(myNextPanel);
        myEastPanel.add(myHelpInfo);
        myEastPanel.add(myStatPanel);
    }

    /** This method creates a JMenuBar for the GUI. */
    private void createMenubar() {
        myMenuBar = new JMenuBar();
        setJMenuBar(myMenuBar);
        // add file to menuBar
        createFileTab();
        // add option to menuBar
        createOptionTab();
        // add help to menuBar
        createHelpTab();
    }

    /** This method creates a File tab. */
    private void createFileTab() {
        // create file menu
        final JMenu file = new JMenu("File");
        myMenuBar.add(file);

        // create new game
        final JMenuItem newgame = new JMenuItem("New Game");
        file.add(newgame);

        // set enabled true for the first game
        newgame.setEnabled(true);

        // add actionListener to make new game.
        newgame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myNewBoard.restart();
                myStatPanel.clearStats();
            }
        });

        // separate menuItem
        file.addSeparator();

        // ability to end current game.
        final JMenuItem endgame = new JMenuItem("End Game");
        file.add(endgame);

        // add actionListener to make the current game end.
        endgame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myNewBoard.setGameOver(true);
                myNewBoard.gameOverBro();
            }
        });

        // disable endgame when newgame is enabled, vis versa.
        file.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(final MenuEvent theEvent) {
                if (myNewBoard.isGameOver()) {
                    newgame.setEnabled(true);
                    endgame.setEnabled(false);
                } else {
                    newgame.setEnabled(false);
                    endgame.setEnabled(true);
                }
            } // empty methods in menuDselected and menuCanceled

            @Override
            public void menuDeselected(final MenuEvent theEvent) {
            }

            @Override
            public void menuCanceled(final MenuEvent theEvent) {
            }
        });

        file.addSeparator();

        // create exit
        final JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);

        // Create actionListener when exit button is pressed.
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                System.exit(0);
            }
        });

    }

    /** This method creates an Option tab. */
    private void createOptionTab() {
        // create Options tab on menuBar
        final JMenu option = new JMenu("Options");
        myMenuBar.add(option);
        
        final JMenu customColor = new JMenu("Custom Color");
        option.add(customColor);
       
        final JMenuItem blockColor = new JMenuItem("Block Color");
        customColor.add(blockColor);

        blockColor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (theEvent.getSource() == blockColor) {
                    final Color cChooser =
                                    JColorChooser.showDialog(getParent(), "Pick a Block Color",
                                                             Color.DARK_GRAY);
                    // setting the icon color and the drawing area.
                    myNewBoard.getColorBlock(cChooser);
                }
            }
        });
        
        // create button for choosing color BG.
        final JMenuItem boardColor = new JMenuItem("Board Color");
        customColor.add(boardColor);

        boardColor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (theEvent.getSource() == boardColor) {
                    final Color colorChooser = JColorChooser.showDialog(getParent(),
                                                "Pick a Board Color", Color.DARK_GRAY);
                    // setting the icon color and the drawing area.
                    myNewBoard.setColorBackgroud(colorChooser);
                }
            }
        });
    }

    /** This method creates a Help tab. */
    private void createHelpTab() {
        // create Help tab on menuBar
        final HelpMenuContent help = new HelpMenuContent();
        myMenuBar.add(help);
    }
}
