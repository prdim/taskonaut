/*
 * ComponentTitledPanel.java
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

package org.underworldlabs.swing;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/** 
 * This class provides a panel with a component
 * as the title within a titled border.<br>
 * Modified example from
 * http://www2.gol.com/users/tame/swing/examples/BorderExamples1.html
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 658 $
 * @date     $Date: 2007-01-08 22:34:31 +1100 (Mon, 08 Jan 2007) $
 */
public class ComponentTitledPanel extends JPanel {
    
    protected ComponentTitledBorder border;
    protected JComponent component;
    protected JPanel panel;
    
    public ComponentTitledPanel() { 
        this(null);
    }
    
    public ComponentTitledPanel(JComponent component) {
        super(new BorderLayout());
        this.component = component;
        border = new ComponentTitledBorder(component);
        setBorder(border);
        panel = new JPanel();
        
        if (component != null) {
            add(component);
        }
        add(panel);
    }
    
    protected Border getComponentTitledBorder() {
        return border;
    }
    
    public JComponent getTitleComponent() {
        return component;
    }
    
    public void setTitleComponent(JComponent newComponent) {
        if (component != null) {
            remove(component);
        }
        add(newComponent);
        border.setTitleComponent(newComponent);
        component = newComponent;
        revalidate();
        repaint();
    }
    
    public JPanel getContentPane() {
        return panel;
    }
    
    public void doLayout() {
        Insets insets = getInsets();
        Rectangle rect = getBounds();
        
        rect.x = 0;
        rect.y = 0;

        Rectangle compR = border.getComponentRect(rect,insets);
        component.setBounds(compR);

        /*
        if (component != null) {
            Border _border = getBorder();
            if (_border instanceof ComponentTitledBorder) {
                Rectangle compR = ((ComponentTitledBorder)_border).
                                        getComponentRect(rect,insets);
                component.setBounds(compR);
            }
        }
        */

        rect.x += insets.left;
        rect.y += insets.top;
        rect.width  -= insets.left + insets.right;
        rect.height -= insets.top  + insets.bottom;
        panel.setBounds(rect);
    }
    
}




