/*
 * ColourTableCellRenderer.java
 *
 * Copyright (C) 2002-2007 Takis Diakoumis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.underworldlabs.swing.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/**
 * Cell Render for colour selections in cells - 
 * typically in system preferences etc.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class ColourTableCellRenderer extends JLabel
                                     implements TableCellRenderer {

    private StringBuffer text;
    private static ColourSwatchIcon icon;
    
    static {
        icon = new ColourSwatchIcon();
    }
    
    /** Creates a new instance of ColourTableCellRenderer */
    public ColourTableCellRenderer() {
        setIconTextGap(10);
        text = new StringBuffer();
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean cellHasFocus,
                                                   int row, 
                                                   int col) {
        
        Color colour = (Color)value;
        
        int red = colour.getRed();
        int green = colour.getGreen();
        int blue = colour.getBlue();

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        icon.setColour(colour);
        setIcon(icon);
        
        text.append(" [").
             append(red).
             append(",").
             append(green).
             append(",").
             append(blue).
             append("]");
        
        setText(text.toString());
        text.setLength(0);
        
        return this;
    }
    
}

class ColourSwatchIcon extends ImageIcon {
    
    private static final int WIDTH = 11;
    private static final int HEIGHT = 11;
    
    private Color colour;
    
    public ColourSwatchIcon() {}
    
    public int getIconWidth() {
        return WIDTH;
    }
    
    public int getIconHeight() {
        return HEIGHT;
    }
    
    public void setColour(Color colour) {
        this.colour = colour;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // fill the background
        g.setColor(colour);
        g.fillRect(4, 4, HEIGHT, HEIGHT);
        // draw the line
        g.setColor(Color.BLACK);
        g.drawRect(4, 4, WIDTH, HEIGHT);
    }
    
} // class ColourSwatchIcon




