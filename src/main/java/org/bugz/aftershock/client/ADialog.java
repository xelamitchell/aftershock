package org.bugz.aftershock.client;

import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

/**
 * Deals with downloading the baseq2 resources.
 * 
 * @author bugz
 */
public class ADialog extends JDialog {
    
    public ADialog() {
        super();
        initialise();
    }

    private void initialise() {
        
        JComponent.setDefaultLocale(Locale.UK);
        
        
        
    }
    
    private JButton ok;
    private JButton cancel;
    private JButton exit;
    
}
