/*
 * InterruptibleProgressDialog.java
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
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.underworldlabs.Constants;
import org.underworldlabs.swing.util.InterruptibleProcess;

/**
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class InterruptibleProgressDialog extends JDialog
                                         implements Runnable,
                                                    ActionListener {
    
    /** The event parent to this object */
    private InterruptibleProcess process;

    /** The progress bar widget */
    private IndeterminateProgressBar progressBar;
    
    /** The parent frame of this dialog */
    private Frame parentFrame;
    
    /** The progress bar label text */
    private String labelText;
    
    public InterruptibleProgressDialog(Frame parentFrame,
                                       String title,
                                       String labelText,
                                       InterruptibleProcess process) {
        super(parentFrame, title, true);
        this.process = process;
        this.labelText = labelText;
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        pack();
        setLocation(GUIUtils.getLocationForDialog(parentFrame, getSize()));
        progressBar.start();
        setVisible(true);
    }
    
    private void jbInit() throws Exception {
        
        progressBar = new IndeterminateProgressBar();
        progressBar.setPreferredSize(new Dimension(260, 18));

        JPanel base = new JPanel(new GridBagLayout());
        
        JButton cancelButton = new CancelButton();
        cancelButton.addActionListener(this);
        
        GridBagConstraints gbc = new GridBagConstraints();
        Insets ins = new Insets(10, 20, 10, 20);
        gbc.insets = ins;
        base.add(new JLabel(labelText), gbc);
        gbc.gridy = 1;
        gbc.insets.top = 0;
        base.add(progressBar, gbc);
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        base.add(cancelButton, gbc);
        
        base.setBorder(BorderFactory.createEtchedBorder());
        
        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        c.add(base, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0,
                                           GridBagConstraints.SOUTHEAST, 
                                           GridBagConstraints.BOTH,
                                           new Insets(5, 5, 5, 5), 0, 0));
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);        
    }

    public void actionPerformed(ActionEvent e) {    
        process.setCancelled(true);
        process.interrupt();
        dispose();
    }
    
    public void dispose() {
        if (progressBar != null) {
            progressBar.stop();
            progressBar.cleanup();
        }
        super.dispose();        
    }

    
    class CancelButton extends JButton {
        
        private int DEFAULT_WIDTH = 75;
        private int DEFAULT_HEIGHT = 30;
        
        public CancelButton() {
            super("Cancel");
            setMargin(Constants.EMPTY_INSETS);
        }
        
        public int getWidth() {
            int width = super.getWidth();
            if (width < DEFAULT_WIDTH) {
                return DEFAULT_WIDTH;
            }
            return width;
        }
        
        public int getHeight() {
            int height = super.getHeight();
            if (height < DEFAULT_HEIGHT) {
                return DEFAULT_HEIGHT;
            }
            return height;
        }
        
        public Dimension getPreferredSize() {
            return new Dimension(getWidth(), getHeight());
        }
    }

    
}

