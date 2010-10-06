/*
 * Constants.java
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

package org.underworldlabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.UIManager;
import org.underworldlabs.swing.plaf.UIUtils;

/**
 * Static constants.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 917 $
 * @date     $Date: 2007-06-17 22:47:42 +1000 (Sun, 17 Jun 2007) $
 */
public interface Constants {

    /** An empty string */
    public static final String EMPTY = "";
    
    public static final String NEW_LINE_STRING = "\n";
    public static final String QUOTE_STRING = "'";
    public static final char QUOTE_CHAR = '\'';
    public static final char NEW_LINE_CHAR = '\n';
    public static final char TAB_CHAR = '\t';
    public static final char COMMA_CHAR = ',';

    // tool tip html tags
    public static final String TABLE_TAG_START = 
            "<table border='0' cellspacing='0' cellpadding='2'>";

    public static final String TABLE_TAG_END = 
            "</table>";

    public static final Insets EMPTY_INSETS = new Insets(0,0,0,0);

    public static final Dimension FORM_BUTTON_SIZE = new Dimension(100, 25);
                                             
}




