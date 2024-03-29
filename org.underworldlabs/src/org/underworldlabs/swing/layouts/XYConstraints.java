/*
 * XYConstraints.java
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

package org.underworldlabs.swing.layouts;

import java.io.Serializable;

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
public class XYConstraints implements Cloneable, Serializable {
    
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    public XYConstraints() {
        this(0, 0, 0, 0);
    }
    
    public XYConstraints(int i, int j, int k, int l) {
        x = i;
        y = j;
        width = k;
        height = l;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int i) {
        x = i;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int i) {
        y = i;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int i) {
        width = i;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int i) {
        height = i;
    }
    
    public void setConstraints(int i, int j, int k, int l) {
        x = i;
        y = j;
        width = k;
        height = l;
    }
    
    public int hashCode() {
        return x ^ y * 37 ^ width * 43 ^ height * 47;
    }
    
    public boolean equals(Object obj) {
        
        if(obj instanceof XYConstraints) {
            XYConstraints xyconstraints = (XYConstraints)obj;
            return xyconstraints.x == x && xyconstraints.y == y &&
            xyconstraints.width == width && xyconstraints.height == height;
        } else
            return false;
        
    }
    
    public Object clone() {
        return new XYConstraints(x, y, width, height);
    }
    
    public String toString() {
        return "XYConstraints [" + x + ", " + y + ", " +
        width + ", " + height + "]";
    }
    
} // class







