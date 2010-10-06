/*
 * BumpyGradientLookAndFeel.java
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

package org.underworldlabs.swing.plaf.bumpygradient;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import org.underworldlabs.swing.plaf.smoothgradient.SmoothGradientLookAndFeel;

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
public class BumpyGradientLookAndFeel extends SmoothGradientLookAndFeel {
    
    /** The modified darker highlight for internal frame bumps */
    private static Color internalFrameBumpsHighlight;
    
    /** Constructs the <code>PlasticLookAndFeel</code>. */
    public BumpyGradientLookAndFeel() {
        if (internalFrameBumpsHighlight == null)
            internalFrameBumpsHighlight = new Color(198,198,246);
    }
    
    public String getID() {
        return "BumpyGradient";
    }
    
    public String getName() {
        return "Bumpy Gradient Look and Feel";
    }
    
    public String getDescription() {
        return "The Execute Query Bumpy Gradient Look and Feel - modified from " +
                "The JGoodies Plastic Look and Feel";
    }
    
    // Overriding Superclass Behavior ***************************************
    
    /**
     * Initializes the class defaults, that is, overrides some UI delegates
     * with JGoodies Plastic implementations.
     *
     * @see javax.swing.plaf.basic.BasicLookAndFeel#getDefaults
     */
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);
        
        String NAME_PREFIX = "org.underworldlabs.swing.plaf.bumpygradient.BumpyGradient";
        
        // Overwrite some of the uiDefaults.
        Object[] uiDefaults = {
            "RootPaneUI", NAME_PREFIX + "RootPaneUI",
            "InternalFrameUI", NAME_PREFIX + "InternalFrameUI",
        };

        table.putDefaults(uiDefaults);
        
    }
    
    public static Color getInternalFrameBumpsHighlight() {
        if (internalFrameBumpsHighlight == null) {
            internalFrameBumpsHighlight = new Color(198,198,246);
        }
        return internalFrameBumpsHighlight;
    }
    
}




