/**
 * 
 */
package org.taskonaut.tasks.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;

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
		this.pe = pe;
		pe.setParentPanel(this);
		setLayout(new BorderLayout());
		add(pe, BorderLayout.CENTER);
		pack();
	}

	@Override
	public void onClose() {
		this.dispose();
		
	}
	
	
}
