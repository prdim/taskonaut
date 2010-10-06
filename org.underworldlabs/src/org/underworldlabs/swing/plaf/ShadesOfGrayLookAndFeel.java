/*
 * ShadesOfGrayLookAndFeel.java
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

package org.underworldlabs.swing.plaf;

import org.underworldlabs.swing.plaf.smoothgradient.SmoothGradientLookAndFeel;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/**
 * Themed extension to SmoothGradientLookAndFeel.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class ShadesOfGrayLookAndFeel extends SmoothGradientLookAndFeel {
    
    /** Creates a new instance of ShadesOfGrayLookAndFeel */
    public ShadesOfGrayLookAndFeel() {
        setCurrentTheme(new ShadesOfGrayTheme());
    }
    
    public String getName() {
        return "Shades of Gray Look and Feel";
    }
    
    public String getDescription() {
        return "Themed extension to Smooth Gradient Look and Feel - modified from " +
                "The JGoodies Plastic Look and Feel";
    }

}




