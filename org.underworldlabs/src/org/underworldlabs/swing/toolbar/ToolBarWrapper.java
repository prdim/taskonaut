/*
 * ToolBarWrapper.java
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

package org.underworldlabs.swing.toolbar;

import java.util.Vector;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/** <p>Wrapper class defining a tool bar, its buttons
 *  and its row and position within the tool bar area.
 *
 *  @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class ToolBarWrapper implements Cloneable {
    
    /** This tool bar's name */
    private String name;
    
    /** Whether the tool bar is visible */
    private boolean visible;
    
    /** This tool bar's buttons */
    private Vector buttons;
    
    /** The tool bar's constraints */
    private ToolBarConstraints constraints;
    
    public ToolBarWrapper() {}
    
    public ToolBarWrapper(String name, boolean visible) {
        this.name = name;
        this.visible = visible;
    }
    
    public ToolBarConstraints getConstraints() {
        return constraints;
    }
    
    public void setConstraints(ToolBarConstraints constraints) {
        this.constraints = constraints;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void addButton(ToolBarButton button) {
        if (buttons == null) {
            buttons = new Vector();
        }        
        buttons.add(button);
    }
    
    public void resetButtons(Vector _buttons) {
        int size = _buttons.size();

        if (buttons != null) {
            buttons.clear();
        } else {
            buttons = new Vector(size);
        }

        for (int i = 0; i < size; i++) {
            buttons.add(((ToolBarButton)_buttons.elementAt(i)).clone());
        }
        
    }
    
    private int getVisibleButtonsCount(Vector _buttons) {
        int count = 0;
        ToolBarButton button = null;
        
        for (int i = 0, k = _buttons.size(); i < k; i++) {
            button = (ToolBarButton)_buttons.elementAt(i);
            if (button.isVisible()) {
                count++;
            }            
        }
        
        return count;
    }
    
    public void setButtonsVector(Vector _buttons) {
        constraints.setMinimumWidth(-1);
        buttons = _buttons;
    }
    
    public int getButtonCount() {
        return buttons.size();
    }
    
    public void setVisible(boolean _visible) {
        
        if (visible == _visible) {
            return;
        }        
        visible = _visible;
        
        if (visible) {
            constraints.setRow(ToolBarProperties.getNextToolbarRow());
            constraints.setPosition(0);
        }
        else {
            constraints.reset();
        }

    }
    
    public void resetButtons() {
        if (buttons != null && buttons.size() > 0) {
            buttons.clear();
        }
    }
    
    public boolean hasButtons() {
        return buttons != null && buttons.size() > 0;
    }
    
    public Vector getButtonsVector() {
        return buttons;
    }
    
    public ToolBarButton[] getButtonsArray() {
        if (buttons != null && buttons.size() > 0) {
            return (ToolBarButton[])buttons.toArray(new ToolBarButton[]{}) ;
        } else {
            return null;
        }
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Object clone() {
        ToolBarWrapper tb = new ToolBarWrapper(name, visible);
        tb.setConstraints((ToolBarConstraints)constraints.clone());

        if (buttons != null) {
            tb.resetButtons((Vector)buttons.clone());
        }        
        return tb;
    }
    
    public String toString() {
        return name;
    }
    
}




