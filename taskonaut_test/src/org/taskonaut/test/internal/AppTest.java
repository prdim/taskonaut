/**
 * 
 */
package org.taskonaut.test.internal;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import org.jdesktop.application.SingleFrameApplication;

/**
 * @author spec
 *
 */
public class AppTest extends SingleFrameApplication {
	private StatusBar statusBar;

	@Override
	protected void startup() {
		getMainFrame().setJMenuBar(createMenuBar());
		show(createMainPanel());
		
	}

	private JMenu createMenu(String menuName, String[] actionNames) {
		JMenu menu = new JMenu();
		menu.setName(menuName);
		for (String actionName : actionNames) {
			if (actionName.equals("---")) {
				menu.add(new JSeparator());
			} else {
				JMenuItem menuItem = new JMenuItem();
				menuItem.setAction(getAction(actionName));
				menuItem.setIcon(null);
				menu.add(menuItem);
			}
		}
		return menu;
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		String[] fileMenuActionNames = {
			    "previousImage",
			    "nextImage",
			    "refreshImage",
			    "stopLoading",
			    "---",
			    "quit"
			};
		menuBar.add(createMenu("fileMenu", fileMenuActionNames));
		return menuBar;
	}
	
	private javax.swing.Action getAction(String actionName) {
		return getContext().getActionMap().get(actionName);
	}
	
	private JComponent createMainPanel() {
		statusBar = new StatusBar(this, getContext().getTaskMonitor());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new MDIDesktopPane(), BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);
		panel.setBorder(new EmptyBorder(0, 2, 2, 2)); // top, left, bottom, right
		panel.setPreferredSize(new Dimension(640, 480));
		return panel;
	}
}
