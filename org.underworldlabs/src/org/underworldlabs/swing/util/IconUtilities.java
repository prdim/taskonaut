/*
 * IconUtilities.java
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

package org.underworldlabs.swing.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/**
 * Icon and image loader and cache.<br>
 * This aims to load images from jar file resources not 
 * local file system paths.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class IconUtilities {
    
    /** default icon resource path in this package tree */
    private static final String ICON_PATH = "/org/underworldlabs/swing/icons/";
    
    /** Icons repository */
    private static Map<String,ImageIcon> icons = new HashMap<String,ImageIcon>();
    
    public static ImageIcon loadImage(String name) {
        return new ImageIcon(
                IconUtilities.class.getResource(name));
    }
    
    public static ImageIcon loadIcon(String name) {
        return loadIcon(name, false);
    }
    
    public static ImageIcon loadIcon(String name, boolean store) {
        ImageIcon icon = null;    
        
        if (icons.containsKey(name)) {
            icon = icons.get(name);
        }
        else {
            URL url = IconUtilities.class.getResource(name);
            if (url != null) {
                icon = new ImageIcon(url);
            }
            // try the default resource path
            else {
                icon = loadDefaultIconResource(name, false);
            }

            if (store) {
                icons.put(name, icon);
            }
        }
        return icon;
    }

    public static ImageIcon loadDefaultIconResource(String name, boolean store) {
        ImageIcon icon = null;
        name = ICON_PATH + name;
        if (icons.containsKey(name)) {
            icon = icons.get(name);
        }
        else {
            URL url = IconUtilities.class.getResource(name);
            if (url == null) {
                throw new RuntimeException("Icon at resource path not found: " +
                        name);
            }
            icon = new ImageIcon(url);

            if (store) {
                icons.put(name, icon);
            }            
        }
        return icon;
    }

    // prevent instantiation
    private IconUtilities() {}

}

