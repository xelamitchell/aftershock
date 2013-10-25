package org.bugz.aftershock.client.splash;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * The {@code Splash} dialog changes state based on whether the environment
 * needs to be set up or not.
 * 
 * @author bugz
 */
public class Splash extends JDialog {
    
    private static final Dimension PREFFERED_DIMENSION = new Dimension(400, 100);
    
    private JPanel statePanel;
    private JPanel statusPanel;
    private JLabel status;
    
    public Splash() {
        super();
        initialise();
    }

    private void initialise() {
        
        statePanel = new JPanel();
        statePanel.setLayout(new GridBagLayout());
        statePanel.setPreferredSize(PREFFERED_DIMENSION);
        
        statusPanel = new JPanel();
        statusPanel.setLayout(new GridBagLayout());
        statusPanel.setPreferredSize(PREFFERED_DIMENSION);
        
        status = new JLabel("initializing aftershock...");
        
        getContentPane().add(new SplashImage(), BorderLayout.CENTER);
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        statusPanel.add(status, gridBagConstraints);
        
        getContentPane().add(statusPanel, BorderLayout.SOUTH);
        
        pack();
        
        setTitle("Aftershock - bugz.com");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationByPlatform(true);
        setLocationRelativeTo(null);
        
    }
    
    /**
     * Changes the state of the dialog.
     * 
     * @param state a new panel dependent on which subsystems are available 
     * and/or needed
     */
    public void setState(JPanel state) {
        this.statePanel = state;
    }
    
    /**
     * Alters the status of the dialog indicating start-up step.
     * 
     * @param status 
     */
    public void setStatus(String status) {
        this.status.setText(status);
    }
    
}
