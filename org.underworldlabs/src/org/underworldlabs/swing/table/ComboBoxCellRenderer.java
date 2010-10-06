/*
 * ComboBoxCellRenderer.java
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
import java.awt.Font;
import java.awt.Graphics;
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
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class ComboBoxCellRenderer extends JLabel
                                  implements TableCellRenderer {
    
    private static Color iconColor;
    
    static {
        iconColor = Color.DARK_GRAY.darker();
    }
    
    /** Creates a new instance of ComboBoxCellRenderer */
    public ComboBoxCellRenderer() {}

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean cellHasFocus,
                                                   int row, 
                                                   int col) {
        setFont(table.getFont());
        
        if (value == null) {
            setText("");
        } else {
            setText(value.toString());            
        }

        return this;
    }

    private int ICON_HEIGHT = 10;
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int height = getHeight();
        int width = getWidth();

        int x = 0, y = 0;
        int xo = width - 15;
        int yo = (height - ICON_HEIGHT) / 2;

        g.setColor(iconColor);        
        for (int i = 1; i <= ICON_HEIGHT; i++) {
            
            y = yo + i + 2;
            
            for (int j = i; j <= ICON_HEIGHT; j++) {
                
                if (j > ICON_HEIGHT - i)
                    break;
                
                x = xo + j;
                g.drawLine(x, y, x, y);
                
            }
            
        }

    }

}




