/*
 * RolloverButton.java
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

package org.underworldlabs.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JToolTip;
import javax.swing.UIManager;
import javax.swing.border.Border;
import org.underworldlabs.swing.plaf.UIUtils;
import org.underworldlabs.swing.plaf.base.AcceleratorToolTipUI;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/**
 * This class creates a JButton where the borders are painted only
 * when the mouse is positioned over it. <br>
 *
 * When the mouse pointer is moved away from the button, the borders are removed.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class RolloverButton extends JButton
                           implements MouseListener {
    
    private String toolTip;
    private String imagePath;
    private String selectImagePath;
    private ImageIcon image;
    private ImageIcon selectImage;
    private boolean selectionEnabled;
    
    private static Insets buttonInsets;
    
    /**
     * Creates a new RolloverButton with an associated image
     * and tool tip.
     * 
     * @param img the ImageIcon to be displayed on the button
     * @param tip the button's tool tip
     */
    public RolloverButton(ImageIcon img, String tip) {
        super(img);
        toolTip = tip;
        init();
    }
    
    /**
     * Creates a new RolloverButton with an associated image
     * and tool tip.
     * 
     * @param a the Action to be associated with this button
     * @param tip the button's tool tip
     */
    public RolloverButton(Action a, String tip) {
        super(a);
        toolTip = tip;
        init();
    }
    
    /**
     * Creates a new RolloverButton with an associated image
     * and tool tip.
     * 
     * @param label the button's label
     * @param tip the button's tool tip
     */
    public RolloverButton(String label, String tip, int h, int w) {
        super(label);
        toolTip = tip;
        init();
        setButtonSize(h, w);
    }
    
    /**
     * Creates a new RolloverButton with an associated image
     * and tool tip.
     * 
     * @param label the button's label
     * @param tip the button's tool tip
     */
    public RolloverButton(ImageIcon img, String tip, int h, int w) {
        super(img);
        toolTip = tip;
        init();
        setButtonSize(h, w);
    }
    
    /**
     * Creates a new RolloverButton with an associated image
     * and tool tip.
     * 
     * @param imgPath the path relative to this class of
     *                  the button icon image
     * @param tip the button's tool tip
     */
    public RolloverButton(String imgPath, String tip) {
        this(imgPath, tip, -1);
    }

    /**
     * Creates a new RolloverButton with an associated image
     * and tool tip.
     * 
     * @param imgPath the path relative to this class of
     *                  the button icon image
     * @param tip the button's tool tip
     */
    public RolloverButton(String imgPath, String tip, int size) {
        super();
        setButtonIcon(imgPath);
        selectImagePath = null;
        toolTip = tip;
        init();
    }

    public RolloverButton() {
        init();
    }
    
    static {
        buttonInsets = new Insets(1,1,1,1);
    }
    
    /** 
     * Initialises the state of the button. 
     */
    private void init() {
        selectionEnabled = true;
        setMargin(buttonInsets);
        setToolTipText(toolTip);
        setBorderPainted(false);
        setContentAreaFilled(false);
        addMouseListener(this);
    }

    /**
     * Resets the buttons rollover state.
     */
    public void reset() {
        mouseOver = false;
        setBorderPainted(false);
        setContentAreaFilled(false);
    }
    
    private void setButtonSize(int height, int width) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }
    
    /**
     * Sets the image associated with the button.
     *
     * @param path the path relative to this class of
     *               the button icon image
     */
    public void setButtonIcon(String path) {
        image = new ImageIcon(RolloverButton.class.getResource(path));
        setIcon(image);
    }
    
    public void enableSelectionRollover(boolean enable) {
        selectionEnabled = enable;
    }
    
    public boolean isSelectionRolloverEnabled() {
        return selectionEnabled;
    }
    
    /** indicates a current rollover */
    private boolean mouseOver;
    
    /**
     * Paints the button's borders as the mouse pointer enters.
     *
     * @param e the MouseEvent that created this event
     */
    public void mouseEntered(MouseEvent e) {
        if(isEnabled() && isSelectionRolloverEnabled()) {
            mouseOver = true;
            setBorderPainted(true);
            setContentAreaFilled(true);
        }
    }
    
    /** Override the <code>isFocusable()</code>
     *  method of <code>Component</code> (JDK1.4) to
     *  return false so the button never maintains
     *  the focus.
     *
     *  @return false
     */
    public boolean isFocusable() {
        return false;
    }
    
    /**
     * Sets the button's borders unpainted as the mouse
     * pointer exits.
     *
     * @param e the MouseEvent that created this event
     */
    public void mouseExited(MouseEvent e) {
        mouseOver = false;
        setBorderPainted(false);
        setContentAreaFilled(false);
    }
    
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public JToolTip createToolTip() {        
        JToolTip tip = new PaintedButtonToolTip();
        tip.setComponent(this);
        return tip;
    }

    class PaintedButtonToolTip extends JToolTip {
        public void updateUI() {
            setUI(new AcceleratorToolTipUI());
        }
    } // class PaintedButtonToolTip

}




