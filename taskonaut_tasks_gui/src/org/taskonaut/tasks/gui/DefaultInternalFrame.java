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

package org.taskonaut.tasks.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;

/**
 * @author Prolubnikov Dmitry
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
//		pe.beforeClose();
		this.dispose();
		
	}

	@Override
	public void onCloseCancel() {
//		pe.beforeClose();
		this.dispose();
	}
	
	
}
