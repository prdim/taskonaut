/**
 * 
 */
package org.taskonaut.tasks.gui;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * @author spec
 *
 */
public class DefaultDialog extends JDialog implements ParentPanel {
	public static final int OK = 1;
	public static final int CANCEL = 0;
	private int status = 0;
	private JPanelExt panel;
	
	public DefaultDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initComponents();
	}

	public DefaultDialog(Dialog owner) {
		super(owner);
		initComponents();
	}
	
	public int getReturnStatus() {
		return status;
	}
	
	public void setPanel(JPanelExt p) {
		contentPanel.add(p);
		pack();
		setLocationRelativeTo(getOwner());
		panel = p;
		panel.setParentPanel(this);
	}

	private void okButtonActionPerformed(ActionEvent e) {
		if(!panel.checkOk()) return;
		panel.beforeClose();
		status = OK;
		this.dispose();
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		panel.beforeClose();
		status = CANCEL;
		this.dispose();
	}

	private void initComponents() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(Borders.DIALOG_BORDER);
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new BorderLayout());
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(
					"$glue, $button, $rgap, $button",
					"pref"));

				//---- okButton ----
				okButton.setText("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonBar.add(okButton, cc.xy(2, 1));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed(e);
					}
				});
				buttonBar.add(cancelButton, cc.xy(4, 1));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	@Override
	public void onCloseOk() {
		okButtonActionPerformed(null);
	}

	@Override
	public void onCloseCancel() {
		cancelButtonActionPerformed(null);
	}
}
