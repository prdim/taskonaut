/*
 * BumpyGradientInternalFrameUI.java
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


import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;

import org.underworldlabs.swing.plaf.smoothgradient.SmoothGradientInternalFrameUI;

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
public final class BumpyGradientInternalFrameUI 
                        extends SmoothGradientInternalFrameUI {

	private BumpyGradientInternalFrameTitlePane titlePane;

    public BumpyGradientInternalFrameUI(JInternalFrame b) {
		super(b);
	}

	public static ComponentUI createUI(JComponent c) {
		return new BumpyGradientInternalFrameUI((JInternalFrame) c);
	}

	protected JComponent createNorthPane(JInternalFrame w) {
		titlePane = new BumpyGradientInternalFrameTitlePane(w);
		return titlePane;
	}

}




