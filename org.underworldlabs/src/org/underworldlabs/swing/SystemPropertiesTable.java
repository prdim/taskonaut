/*
 * SystemPropertiesTable.java
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

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import org.underworldlabs.swing.table.DefaultTableHeaderRenderer;
import org.underworldlabs.swing.table.PropertyWrapperModel;

/**
 * Simple system properties table with the values 
 * from <code>System.getProperties()</code>.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class SystemPropertiesTable extends JTable {
    
    /** table model */
    private PropertyWrapperModel model;
    
    /** Creates a new instance of SystemPropertiesTable */
    public SystemPropertiesTable() {
        if (UIManager.getLookAndFeel() instanceof MetalLookAndFeel) {
            getTableHeader().setDefaultRenderer(new DefaultTableHeaderRenderer());
        }

        model = new PropertyWrapperModel(System.getProperties(), 
                                         PropertyWrapperModel.SORT_BY_KEY);
        setModel(model);
        
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(0).setCellRenderer(new PropertiesTableCellRenderer());
        getColumnModel().getColumn(1).setCellRenderer(new PropertiesTableCellRenderer());
    }
    
    private class PropertiesTableCellRenderer extends DefaultTableCellRenderer {
        public PropertiesTableCellRenderer() {}
        public Component getTableCellRendererComponent(JTable table, 
                                                       Object value, 
                                                       boolean isSelected, 
                                                       boolean hasFocus, 
                                                       int row, 
                                                       int column) {
            if (value != null) {
                String toolTip = value.toString();
                setToolTipText(toolTip);
            }
            return super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
        }
    }
    
}

