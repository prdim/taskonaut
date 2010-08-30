/**
 * 
 */
package org.taskonaut.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;
import org.taskonaut.api.IChangeDataListener;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.StatusBar;
import org.taskonaut.app.MDIDesktopPane;
import org.taskonaut.internal.Activator;

/**
 * @author spec
 *
 */
public class MainApplication extends SingleFrameApplication implements IChangeDataListener {
	private StatusBar statusBar;
	public static boolean isInit = false;
	private JDialog aboutBox;
	private JMenu fileMenu = new JMenu();
	private JMenu taskMenu = new JMenu();
	private JMenu reportMenu = new JMenu();
	private JMenu propsMenu = new JMenu();
//	private JMenu windowsMenu = new JMenu();
	private JMenu helpMenu = new JMenu();
	private JMenuBar menuBar = null;
	private MDIDesktopPane mdi;

	@Override
	protected void startup() {
		getMainFrame().setJMenuBar(createMenuBar());
		// TODO Понаблюдать за порядком запуска бандлов и добавлением пунктов меню!!!
		Activator.getMenuService().addChangeListener(this);
		show(createMainPanel());
		isInit = true;
	}
	
	public void addInternalFrame(JInternalFrame f) {
		mdi.add(f);
	}
	
	/**
	 * Создаем основное окно приложения
	 * @return
	 */
	private JComponent createMainPanel() {
		statusBar = new StatusBar(this, getContext().getTaskMonitor());
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane sp = new JScrollPane();
		mdi = new MDIDesktopPane();
		sp.setViewportView(mdi);
		panel.add(sp, BorderLayout.CENTER);
//		panel.add(new MDIDesktopPane(), BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);
		panel.setBorder(new EmptyBorder(0, 2, 2, 2)); // top, left, bottom, right
		panel.setPreferredSize(new Dimension(640, 480));
		return panel;
	}
	
	private JMenuBar createMenuBar() {
		// TODO Файл, Задачи, Отчеты, Свойства, Окна, Помощь		
		fileMenu.setName("fileMenu");
		taskMenu.setName("taskMenu");
		reportMenu.setName("reportMenu");
		propsMenu.setName("propsMenu");
//		windowsMenu.setName("windowsMenu");
		helpMenu.setName("helpMenu");
		
		updateAllMenu();
		
//		JMenuItem menuItem = new JMenuItem();
//		menuItem.setAction(getAction("quit"));
//		menuItem.setIcon(null);
//		fileMenu.add(menuItem);
//		menuItem = new JMenuItem();
//		menuItem.setAction(getAction("showAboutBox"));
//		menuItem.setIcon(null);
//		helpMenu.add(menuItem);
		
		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(taskMenu);
		menuBar.add(reportMenu);
//		menuBar.add(propsMenu);
//		menuBar.add(windowsMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
	
	private void updateAllMenu() {
		System.out.println("Update ALL menu");
//		if(menuBar == null) return;
//		Map<String, List<IMenuAction>> m = Activator.getMenuTracker().getMenu();
		updateMenu("fileMenu", fileMenu);
		updateMenu("taskMenu", taskMenu);
		updateMenu("reportMenu", reportMenu);
		updateMenu("helpMenu", helpMenu);
		// Постоянные пункты, которые не зависят от наличия плагинов
		JMenuItem menuItem = new JMenuItem();
		menuItem.setAction(getAction("quit"));
		menuItem.setIcon(null);
		fileMenu.add(menuItem);
		menuItem = new JMenuItem();
		menuItem.setAction(getAction("showAboutBox"));
		menuItem.setIcon(null);
		helpMenu.add(menuItem);
	}
	
	private void updateMenu(String s, JMenu mn) {
		mn.removeAll();
		if(!Activator.getMenuService().getAllItems().containsKey(s)) return;
		System.out.println("Update menu -> " + s);
		List<IMenuAction> mp = Activator.getMenuService().getAllItems().get(s);
		for(IMenuAction i : mp) {
			JMenuItem menuItem = new JMenuItem();
			menuItem.setAction(getContext().getActionMap(i).get(i.getActionName()));
			menuItem.setIcon(null);
			mn.add(menuItem);
		}
	}

	private javax.swing.Action getAction(String actionName) {
		return getContext().getActionMap().get(actionName);
	}
	
	@Action public void showAboutBox() {
		if (aboutBox == null) {
			aboutBox = new TaskonautAboutBox(getMainFrame());
			aboutBox.setLocationRelativeTo(getMainFrame());
		}
		show(aboutBox);
	}

	@Override
	public void onChange(Object o) {
		updateAllMenu();	
	}
	
	public class MessageTask extends Task<Void, Void> {
		private String msg;

		public MessageTask(Application application, String msg) {
			super(application);
			this.msg = msg;
		}

		@Override
		protected Void doInBackground() throws Exception {
			setMessage(msg);
			return null;
		}
		
	}
}
