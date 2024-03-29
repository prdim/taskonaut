/*
 * AcceleratorToolTipUI.java
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

package org.underworldlabs.swing.plaf.base;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicToolTipUI;

import org.underworldlabs.Constants;
import org.underworldlabs.util.MiscUtils;

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
public class AcceleratorToolTipUI extends BasicToolTipUI {

    private static String delimiter = "+";

    public AcceleratorToolTipUI() {
        super();
    }

    public void paint(Graphics g, JComponent c) {
        Font font = c.getFont();
        FontMetrics metrics = c.getFontMetrics(font);

        Dimension size = c.getSize();
        if (c.isOpaque()) {
            g.setColor(c.getBackground());
            g.fillRect(0, 0, size.width + 20, size.height);
        }

        g.setColor(c.getForeground());
        g.setFont(font);

        JToolTip tip = (JToolTip)c;
        String keyText = getAccelerator(tip);

        if (!MiscUtils.isNull(keyText)) {
            Insets insets = c.getInsets();
            Rectangle paintTextR = new Rectangle(
                insets.left,
                insets.top,
                size.width - (insets.left + insets.right),
                size.height - (insets.top + insets.bottom));
            g.drawString(keyText, 
                    paintTextR.x + 3,
                    paintTextR.y + metrics.getAscent());
        }

    }

    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);

        JToolTip tip = (JToolTip)c;
        String keyText = getAccelerator(tip);

        if (!MiscUtils.isNull(keyText)) {
            Font font = c.getFont();
            FontMetrics fm = c.getFontMetrics(font);	
            d.width = fm.stringWidth(keyText) + 8;
        }
        return d;
    }

    private String getAccelerator(JToolTip tip) {
        String text = tip.getTipText();
        if (text == null) {
            text = Constants.EMPTY; 
        }

        Action action = ((AbstractButton)tip.getComponent()).getAction();

        if (action != null) {
            String modText = null;
            KeyStroke keyStroke = (KeyStroke)action.getValue(
                                                Action.ACCELERATOR_KEY);

            if (keyStroke != null) {
                int mod = keyStroke.getModifiers();
                modText = KeyEvent.getKeyModifiersText(mod);

                if (!MiscUtils.isNull(modText)) {
                    modText += delimiter;
                }

                String keyText = KeyEvent.getKeyText(keyStroke.getKeyCode());
                if (!MiscUtils.isNull(keyText)) {
                    modText += keyText;
                }

            }

            if (!MiscUtils.isNull(modText)) {
                text = text + "  (" + modText + ")";
            }                    

        }
        return text;
    }

}




