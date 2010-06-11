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

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;

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
	
	public void updateActions() {
		getMainFrame().setJMenuBar(createMenuBar());
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
		JMenuItem menuItem = new JMenuItem();
		menuItem.setAction(getContext().getActionMap(new TestActionInt()).get("testAction2"));
		menuItem.setIcon(null);
		menu.add(menuItem);
		if(MenuTracker.menu!=null) {
			menuItem = new JMenuItem();
			menuItem.setAction(getContext().getActionMap(MenuTracker.menu).get(MenuTracker.menu.getActionName()));
			menuItem.setIcon(null);
			menu.add(menuItem);
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
	
	public boolean isNextImageEnabled() {
		return true;
	}
	
	@Action(enabledProperty = "nextImageEnabled")
	public Task previousImage() {
		return new TestTask(this);
	}
	
	private class TestTask extends Task<Object, Void> {

		public TestTask(Application app) {
			super(app);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected Object doInBackground() throws Exception {
			System.out.println("Task started!");
			return null;
		}
	}
}
