/*
 * EachRowEditor.java
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

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Hashtable;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

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
public class EachRowEditor implements TableCellEditor {
    
    protected Hashtable editors;
    protected TableCellEditor editor;
    protected TableCellEditor defaultEditor;
    protected JTable table;
    
    /**
     * Constructs a EachRowEditor.
     * create default editor
     *
     * @see TableCellEditor
     * @see DefaultCellEditor
     */
    public EachRowEditor(JTable table) {
        this.table = table;
        editors = new Hashtable();
        JTextField defaultField = new JTextField();
        defaultField.setBorder(null);
        defaultEditor = new DefaultCellEditor(defaultField);
    }
    
    /**
     * @param row    table row
     * @param editor table cell editor
     */
    public void setEditorAt(int row, TableCellEditor editor) {
        editors.put(new Integer(row),editor);
    }
    
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value, 
                                                 boolean isSelected, 
                                                 int row, 
                                                 int column) {
        
        Component _editor = editor.getTableCellEditorComponent(
                                      table, value, isSelected, row, column);
        _editor.setFont(table.getFont());
        return _editor;
    }
    
    public Object getCellEditorValue() {
        return editor.getCellEditorValue();
    }
    public boolean stopCellEditing() {
        return editor.stopCellEditing();
    }
    public void cancelCellEditing() {
        editor.cancelCellEditing();
    }
    public boolean isCellEditable(EventObject anEvent) {
        selectEditor((MouseEvent)anEvent);
        return editor.isCellEditable(anEvent);
    }
    public void addCellEditorListener(CellEditorListener l) {
        editor.addCellEditorListener(l);
    }
    public void removeCellEditorListener(CellEditorListener l) {
        editor.removeCellEditorListener(l);
    }
    public boolean shouldSelectCell(EventObject anEvent) {
        selectEditor((MouseEvent)anEvent);
        return editor.shouldSelectCell(anEvent);
    }
    
    protected void selectEditor(MouseEvent e) {
        int row;
        if (e == null) {
            row = table.getSelectionModel().getAnchorSelectionIndex();
        } else {
            row = table.rowAtPoint(e.getPoint());
        }
        editor = (TableCellEditor)editors.get(new Integer(row));
        if (editor == null) {
            editor = defaultEditor;
        }
    }
}





