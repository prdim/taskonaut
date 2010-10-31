/*
 * NumberCellEditor.java
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

import java.awt.Toolkit;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/**
 * Simple numeric value table column cell editor.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 677 $
 * @date     $Date: 2007-01-27 22:27:34 +1100 (Sat, 27 Jan 2007) $
 */
public class NumberCellEditor extends JTextField
                              implements TableCellEditorValue {
    
    private int maxLength;
    private Toolkit toolkit;
    private NumberFormat integerFormatter;

    public NumberCellEditor() {
        this(-1, false);
    }

    public NumberCellEditor(boolean alignLeft) {
        this(-1, alignLeft);
    }

    public NumberCellEditor(int maxLength) {
        this(maxLength, false);
    }

    public NumberCellEditor(int maxLength, boolean alignLeft) {
        super();
        this.maxLength = maxLength;
        toolkit = Toolkit.getDefaultToolkit();
        integerFormatter = NumberFormat.getNumberInstance();
        integerFormatter.setParseIntegerOnly(true);
        setBorder(null);
        setHorizontalAlignment(alignLeft ? JTextField.LEFT : JTextField.RIGHT);
    }

    public int getValue() {
        int retVal = 0;
        try {
            retVal = integerFormatter.parse(getText()).intValue();
        } catch (ParseException e) {
            toolkit.beep();
        }
        return retVal;
    }

    /**
     * Returns the current editor value from the component
     * defining this object.
     *
     * @return the editor's value
     */
    public String getEditorValue() {
        return getStringValue();
    }

    public String getStringValue() {
        return Integer.toString(getValue());
    }
    
    public int getCharsLength() {
        return getText().length();
    }
    
    public boolean isZero() {
        return getValue() == 0;
    }
    
    public void setValue(int value) {
        setText(integerFormatter.format(value));
    }
    
    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    protected Document createDefaultModel() {
        return new WholeNumberDocument();
    }
    
    protected class WholeNumberDocument extends PlainDocument {
        public void insertString(int offs, String str, AttributeSet a)
        throws BadLocationException {
            
            if (maxLength != -1) {

                if (getLength() >= maxLength) {
                    toolkit.beep();
                    return;
                }

            }
            
            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int j = 0;
            
            for (int i = 0; i < result.length; i++) {
                if (Character.isDigit(source[i]) ||
                        (offs == 0 && i == 0 && source[i] == '-')) {
                    result[j++] = source[i];
                }
                else {
                    toolkit.beep();
                }
            }
            super.insertString(offs, new String(result, 0, j), a);
        }
    } // class WholeNumberDocument
    
    
}

