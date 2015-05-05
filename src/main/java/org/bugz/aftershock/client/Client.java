package org.bugz.aftershock.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author bugz
 */
public class Client {

    private static final Dimension PREFFERED_DIMENSION = new Dimension(400, 100);
    
    private final JFrame frame;

    public Client() {

        frame = new JFrame("Aftershock - bugz.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(), BorderLayout.CENTER);
        frame.pack();
        
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(PREFFERED_DIMENSION);

        frame.setVisible(true);

    }

}
