/*
 * Anh Nguyen 
 * TCSS305C - Winter 
 * Assignment 6b - Tetris 
 * HelpContent.java
 * This class creates the content within the help tab.
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This class creates all the content for the help tab in the menu bar.
 * 
 * @author Anh Nguyen
 * @version 1.0
 *
 */
public class HelpMenuContent extends JMenu {

    /**
     * This is an auto-generated serialVersionUID.
     */
    private static final long serialVersionUID = 247057821553197383L;

    /**
     * This is a string for the About message.
     */
    private static final String ABOUT_MESSAGE = "TCSS 305 Tetris\nWinter 2016\nAnh Nguyen";

    /**
     * This is the constructor for the HelpContent object.
     */
    public HelpMenuContent() {
        super("Help");
        createScoreMath();
        createAbout();
    }

    /**
     * This method creates the Score algorithm view.
     */
    private void createScoreMath() {

        // create Score Algorithm
        final JMenuItem scoreMath = new JMenuItem("Score Algorithm");
        add(scoreMath);

        // creating scoring image
        final ImageIcon scoreIcon = new ImageIcon("images/score_method.png");

        // add pop-up for scoring algorithm
        scoreMath.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (theEvent.getSource() == scoreMath) {
                    JOptionPane.showMessageDialog(getParent(), null, "Scoring Algorithm",
                                                  JOptionPane.INFORMATION_MESSAGE, scoreIcon);
                }
            }
        });

    }

    /**
     * This method creates the about clause for the application.
     */
    private void createAbout() {

        // create icon for the about JOptionPane.
        final ImageIcon icon = new ImageIcon("images/myGamingIcon.png");

        final JMenuItem about = new JMenuItem("About...");
        add(about);

        // add ActionListener when about button is pressed to show dialogue.
        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (theEvent.getSource() == about) {
                    JOptionPane.showMessageDialog(getParent(), ABOUT_MESSAGE, "About",
                                                  JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }
        });

    }
}
