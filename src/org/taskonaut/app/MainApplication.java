/**
 * 
 */
package org.taskonaut.app;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.application.SingleFrameApplication;
import org.taskonaut.app.StatusBar;
import org.taskonaut.app.MDIDesktopPane;

/**
 * @author spec
 *
 */
public class MainApplication extends SingleFrameApplication {
	private StatusBar statusBar;
	public static boolean isInit = false;

	@Override
	protected void startup() {
		getMainFrame().setJMenuBar(createMenuBar());
		show(createMainPanel());
		isInit = true;
	}
	
	/**
	 * Создаем основное окно приложения
	 * @return
	 */
	private JComponent createMainPanel() {
		statusBar = new StatusBar(this, getContext().getTaskMonitor());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new MDIDesktopPane(), BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);
		panel.setBorder(new EmptyBorder(0, 2, 2, 2)); // top, left, bottom, right
		panel.setPreferredSize(new Dimension(640, 480));
		return panel;
	}
	
	private JMenuBar createMenuBar() {
		// TODO Файл, Задачи, Отчеты, Свойства, Окна, Помощь
		JMenu file = new JMenu();
		file.setName("fileMenu");
		
		JMenuItem menuItem = new JMenuItem();
		menuItem.setAction(getAction("quit"));
		menuItem.setIcon(null);
		file.add(menuItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(file);
		return menuBar;
	}

	private javax.swing.Action getAction(String actionName) {
		return getContext().getActionMap().get(actionName);
	}
}
