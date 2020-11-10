/*
 * Anh Nguyen 
 * TCSS305C - Winter 
 * Assignment 6b - Tetris 
 * TetrisGameMain.java 
 * This class will kick off the tetris game.
 */
package view;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is the main class that will kick off the Tetris game.
 * @author Anh Nguyen
 * @version 1.0
 *
 */
public final class TetrisGameMain {

    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisGameMain() {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the PowerPaint GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        /* Use an appropriate Look and Feel */
        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGameGUI().start();
            }
        });
    }
}
