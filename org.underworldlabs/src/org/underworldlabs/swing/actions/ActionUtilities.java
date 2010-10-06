/*
 * ActionUtilities.java
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

package org.underworldlabs.swing.actions;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import org.underworldlabs.swing.util.IconUtilities;

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
public class ActionUtilities {
    
    private ActionUtilities() {}

    public static JButton createButton(ActionListener actionListener, 
                                       Icon icon,
                                       String name, 
                                       String command) {
        JButton item = new JButton(name);
        item.setActionCommand(command);
        
        if (icon != null) {
            item.setIcon(icon);
        }
        
        if (actionListener != null) {
            item.addActionListener(actionListener);
        }
        
        return item;
    }

    public static JButton createButton(ActionListener actionListener, 
                                       String command,
                                       Icon icon,
                                       String toolTipText) {
        JButton item = new JButton(icon);
        item.setMargin(new Insets(1, 1, 1, 1));

        item.setToolTipText(toolTipText);
        item.setActionCommand(command);
        
        if (actionListener != null) {
            item.addActionListener(actionListener);
        }
        
        return item;
    }

    public static JButton createButton(ActionListener actionListener, 
                                       String icon,
                                       String toolTipText, 
                                       String command) {
        
        JButton item = new JButton();
        if (icon != null) {
            item.setIcon(IconUtilities.loadIcon(icon));
            item.setMargin(new Insets(1, 1, 1, 1));
        }

        item.setToolTipText(toolTipText);
        item.setActionCommand(command);
        
        if (actionListener != null) {
            item.addActionListener(actionListener);
        }
        
        return item;
    }

    public static JButton createButton(String name, 
                                       String icon, 
                                       String command,
                                       boolean iconOnly) {
        JButton item = new JButton(name);
        item.setToolTipText(name);
        item.setActionCommand(command);
        
        if (icon != null) {
            item.setIcon(IconUtilities.loadIcon(icon));
        }
        
        if(iconOnly) {
            item.setMargin(new Insets(1, 1, 1, 1));
            item.setText(null);
        }
        return item;
    }

    public static JButton createButton(ActionListener actionListener, 
                                       String name, 
                                       String command) {
        JButton item = new JButton(name);
        item.setActionCommand(command);
        item.addActionListener(actionListener);
        return item;
    }

    public static JButton createButton(String name, String command) {
        JButton item = new JButton(name);
        item.setActionCommand(command);
        return item;
    }

    public static JCheckBox createCheckBox(ActionListener actionListener,
                                           String name, 
                                           String command, 
                                           boolean selected) {
        JCheckBox item = new JCheckBox(name, selected);
        item.setActionCommand(command);
        
        if (actionListener != null) {
            item.addActionListener(actionListener);
        }

        return item;
    }

    public static JCheckBox createCheckBox(ActionListener actionListener,
                                           String name, 
                                           String command) {
        return createCheckBox(actionListener, name, command, false);
    }

    public static JCheckBox createCheckBox(String name, 
                                           String command, 
                                           boolean selected) {
        return createCheckBox(null, name, command, selected);
    }

    public static JCheckBox createCheckBox(String name, 
                                           String command) {
        return createCheckBox(null, name, command, false);
    }

    public static JComboBox createComboBox(ActionListener actionListener, 
                                           String[] values, 
                                           String command) {
        JComboBox combo = new JComboBox(values);
        combo.setActionCommand(command);
        
        if (actionListener != null) {
            combo.addActionListener(actionListener);
        }

        return combo;
    }

    public static JComboBox createComboBox(String[] values, String command) {
        return createComboBox(null, values, command);
    }

    public static JComboBox createComboBox(ActionListener actionListener, 
                                           Vector values, 
                                           String command) {
        JComboBox combo = new JComboBox(values);
        combo.setActionCommand(command);
        
        if (actionListener != null) {
            combo.addActionListener(actionListener);
        }

        return combo;
    }

    public static JComboBox createComboBox(Vector values, String command) {
        return createComboBox(null, values, command);
    }

}




