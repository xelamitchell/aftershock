package org.bugz.aftershock.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author bugz
 */
public class AClient {

    private static final Dimension PREFFERED_DIMENSION = new Dimension(400, 100);
    
    private JFrame frame;

    public AClient() {

        frame = new JFrame("Aftershock - Bugz.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(), BorderLayout.CENTER);
        frame.pack();
        
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(PREFFERED_DIMENSION);

        frame.setVisible(true);

    }

}
