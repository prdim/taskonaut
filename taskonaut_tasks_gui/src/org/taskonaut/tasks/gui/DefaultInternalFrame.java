/**
 * 
 */
package org.taskonaut.tasks.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;

/**
 * @author ProlubnikovDA
 *
 */
public class DefaultInternalFrame extends JInternalFrame implements ParentPanel {
	private JPanelExt pe = null;

	/**
	 * @param pe
	 */
	public DefaultInternalFrame(JPanelExt pe) {
		setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setClosable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
        	public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }

			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
		this.pe = pe;
//		pack();
		pe.setParentPanel(this);
		setLayout(new BorderLayout());
		add(pe, BorderLayout.CENTER);
//		pe.invalidate();
		pack();
	}
	
	private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
        pe.beforeClose();
    }

	@Override
	public void onCloseOk() {
		pe.beforeClose();
		this.dispose();
		
	}

	@Override
	public void onCloseCancel() {
		pe.beforeClose();
		this.dispose();
	}
	
	
}
