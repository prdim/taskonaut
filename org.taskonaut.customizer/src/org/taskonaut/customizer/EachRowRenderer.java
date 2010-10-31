/*
 * EachRowRenderer.java
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

package org.taskonaut.customizer;

import java.awt.Component;
import java.util.Hashtable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
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
public class EachRowRenderer implements TableCellRenderer {
    
    protected Hashtable renderers;
    protected TableCellRenderer renderer;
    protected TableCellRenderer defaultRenderer;

    public EachRowRenderer() {
        renderers = new Hashtable();
        defaultRenderer = new DefaultTableCellRenderer();
    }

    public void add(int row, TableCellRenderer renderer) {
        renderers.put(new Integer(row),renderer);
    }
    
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, 
                                                   boolean isSelected, 
                                                   boolean hasFocus,
                                                   int row, 
                                                   int column) {

        renderer = (TableCellRenderer)renderers.get(new Integer(row));

        if (renderer == null) {
            renderer = defaultRenderer;
        }

        if (value != null && renderer instanceof DefaultTableCellRenderer) {
            ((DefaultTableCellRenderer)renderer).setToolTipText(value.toString());
        }

        return renderer.getTableCellRendererComponent(table,
                                                      value, 
                                                      isSelected, 
                                                      hasFocus, 
                                                      row, 
                                                      column);

    }
}




