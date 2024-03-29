/*
 * Copyright 2010 Prolubnikov Dmitry
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.taskonaut.launcher;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Prolubnikov Dmitry
 */
public class SplashFrame extends javax.swing.JFrame {

    /** Creates new form SplashFrame */
    public SplashFrame() {
        initComponents();
        this.setUndecorated(true);
    }

    public void updateStatus(String s, int p) {
        jLabel1.setText(s);
        if(p<0) {
            jProgressBar1.setIndeterminate(true);
        } else {
            jProgressBar1.setIndeterminate(false);
            jProgressBar1.setValue(p);
        }
        
        this.repaint();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("jLabel1");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel1);
        jPanel1.add(jProgressBar1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-289)/2, (screenSize.height-41)/2, 289, 41);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

}
