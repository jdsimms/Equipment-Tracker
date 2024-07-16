/*
 * Class to set colored Icon for ping status
 */
package gui_app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 * @author John Simmonds
 */
public class RoundIcon implements Icon{
    Color color;
    
    public RoundIcon(Color c) {
        this.color=c;
    }
    
    public void paintIcon (Component c, Graphics g, int x, int y){
        g.setColor(color);
        g.fillOval(x, y, getIconWidth(), getIconHeight());
    }
    
    public int getIconWidth() {
        return 10;
    }
    
    public int getIconHeight() {
        return 10;
    }
}
