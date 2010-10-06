/*
 * DefaultTableHeaderRenderer.java
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

package org.underworldlabs.swing.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import org.underworldlabs.Constants;

import org.underworldlabs.swing.plaf.UIUtils;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/**
 * Simple header renderer with tool tip text and
 * configurable height.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class DefaultTableHeaderRenderer extends DefaultTableCellRenderer { 
                                        //implements TableCellRenderer {

    /** the height of the header */
    private int height;

    /** the light gradient colour 1 */
    private Color colour1;

    /** the dark gradient colour 2 */
    private Color colour2;

    /** whether to fill a gradient background */
    private boolean fillGradient;

    /** the default height - 20 */
    protected static final int DEFAULT_HEIGHT = 20;
    
    /** Creates a new instance of DefaultTableHeaderRenderer */
    public DefaultTableHeaderRenderer() {
        this(DEFAULT_HEIGHT);
    }

    /** Creates a new instance of DefaultTableHeaderRenderer */
    public DefaultTableHeaderRenderer(int height) {
        this.height = height;
        /*
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setFont(UIManager.getFont("TableHeader.font"));
        setForeground(UIManager.getColor("TableHeader.foreground"));
        setBackground(UIManager.getColor("TableHeader.background"));
        */
        setHorizontalAlignment(JLabel.CENTER);

        fillGradient = UIUtils.isDefaultLookAndFeel() || UIUtils.usingOcean();
        if (fillGradient) {
            colour1 = UIManager.getColor("Label.background");
            colour2 = UIUtils.getDarker(colour1, 0.85);
        }
        
    }
    
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        String label = null;
        if (value != null) {
            label = value.toString();
        } else {
            label = Constants.EMPTY;
        }

        setText(label);
        setToolTipText(label);       

        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setFont(UIManager.getFont("TableHeader.font"));
        setForeground(UIManager.getColor("TableHeader.foreground"));
        setBackground(UIManager.getColor("TableHeader.background"));

        return this;
    }

    public void paintComponent(Graphics g) {
        if (fillGradient) {
            int width = getWidth();
            int height = getHeight();

            Graphics2D g2 = (Graphics2D)g;
            Paint originalPaint = g2.getPaint();
            GradientPaint fade = new GradientPaint(0, height, colour2,
                    0, (int)(height * 0.5), colour1);

            g2.setPaint(fade);
            g2.fillRect(0,0, width, height);

            g2.setPaint(originalPaint);
        }
        super.paintComponent(g);
    }

    public boolean isOpaque() {
        return !fillGradient;
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), height);
    }
    
    /** 
     * Returns the height of the renderer.
     */
    public int getHeight() {
        return height;
    }
    
    
}




