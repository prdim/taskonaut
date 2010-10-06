/*
 * ToolBarButton.java
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

import javax.swing.Action;
import javax.swing.ImageIcon;

import java.io.Serializable;

import org.underworldlabs.swing.actions.ActionBuilder;
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
public class ToolBarButton implements Serializable, Cloneable {
    
    private int id;
    private Action action;
    private String actionId;
    private ImageIcon icon;
    private boolean visible;
    private int order;
    
    /** Defines a tool bar separator */
    public static final int SEPARATOR_ID = 29;
    
    public ToolBarButton(int id) {
        this.id = id;
    }
    
    public ToolBarButton(int id, String actionId) {
        this.id = id;
        this.actionId = actionId;
        action = ActionBuilder.get(actionId);
    }
    
    public void setActionId(String actionId) {
        this.actionId = actionId;
        action = ActionBuilder.get(actionId);
    }
    
    public String getActionId() {
        return actionId;
    }
    
    public boolean isSeparator() {
        return id == SEPARATOR_ID;
    }
    
    public int getOrder() {
        return order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void invertSelected() {
        visible = !visible;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public ImageIcon getIcon() {
        if (icon == null) {
            if (id == SEPARATOR_ID) {
                icon = IconUtilities.loadDefaultIconResource("Blank16.gif", true);
            } else {
                if (action != null) {
                    icon = (ImageIcon)action.getValue(Action.SMALL_ICON);
                }
            }            
        }
        
        return icon;
    }
    
    public String getName() {
        if (id == SEPARATOR_ID) {
            return "- Separator -";
        } else {
            return (String)action.getValue(Action.NAME);
        }
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public String toString() {
        return getName();
    }
    
    public Object clone() {
        try {
            ToolBarButton button = (ToolBarButton)super.clone();
            return button;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }        
    }
    
    
}







