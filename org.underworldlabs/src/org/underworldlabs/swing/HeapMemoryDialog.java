/*
 * HeapMemoryDialog.java
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

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

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
public class HeapMemoryDialog extends JDialog 
                              implements ActionListener {
    
    private HeapMemoryPanel heapPanel;
    
    public HeapMemoryDialog(Frame owner) {
        super(owner, "Java Heap Memory", false);        
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HeapMemoryDialog(Dialog owner) {
        super(owner, "Java Heap Memory", false);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() {
        heapPanel = new HeapMemoryPanel();
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        
        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,0,1);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(heapPanel, gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets.bottom = 10;
        gbc.anchor = GridBagConstraints.CENTER;
        c.add(closeButton, gbc);
        
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        pack();
        setLocation(GUIUtils.getLocationForDialog(getOwner(), getSize()));
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
    }
    
    public void dispose() {
        if (heapPanel != null) {
            heapPanel.stopTimer();
        }        
        super.dispose();
    }
    
}







