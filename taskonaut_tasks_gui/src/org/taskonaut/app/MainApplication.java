/**
 * 
 */
package org.taskonaut.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
import org.taskonaut.tasks.gui.internal.Activator;

/**
 * @author spec
 *
 */
public class MainApplication extends SingleFrameApplication implements IChangeDataListener {
	private StatusBar statusBar;
	public static boolean isInit = false;
	private JDialog aboutBox;
	private JMenuBar menuBar = null;
	private MDIDesktopPane mdi;

	@Override
	protected void startup() {
		getMainFrame().setJMenuBar(createMenuBar());
		// TODO Понаблюдать за порядком запуска бандлов и добавлением пунктов меню!!!
		Activator.getMenuService().addChangeListener(this);
		show(createMainPanel());
		isInit = true;
		updateAllMenu();
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
		menuBar = new JMenuBar();
		return menuBar;
	}
	
	private synchronized void updateAllMenu() {
		System.out.println("Update ALL menu");
		JMenu root = new JMenu("root menu");
		// Добавим пункты которые всегда присутствуют
		JMenu menu = new JMenu("Файл");
		menu.setName("Файл");
		root.add(menu);
		menu = new JMenu("Задачи");
		menu.setName("Задачи");
		root.add(menu);
		menu = new JMenu("Отчеты");
		menu.setName("Отчеты");
		root.add(menu);
		menu = new JMenu("Помощь");
		menu.setName("Помощь");
		root.add(menu);
		// Отсортируем пункты, добавленные плагинами
		List<IMenuAction> m = Activator.getMenuService().getAllItems();
		Comparator<IMenuAction> menuComparator = new Comparator<IMenuAction>() {
			
			@Override
			public int compare(IMenuAction o1, IMenuAction o2) {
				return o1.getPriority() - o2.getPriority();
			}
		};
		Collections.sort(m, menuComparator);
		// Добавим их в меню
		for(IMenuAction i : m) {
			System.out.println(i.getMenuPath());
			JMenu t = getMenu(root, Arrays.asList(i.getMenuPath().split("\\|")));
			JMenuItem menuItem = new JMenuItem();
			menuItem.setAction(getContext().getActionMap(i).get(i.getActionName()));
			menuItem.setIcon(null);
			t.add(menuItem);
		}
		// Теперь пару пунктов, которые присутствуют всегда
		JMenuItem quit = new JMenuItem();
		quit.setAction(getAction("quit"));
		quit.setIcon(null);
		JMenuItem about = new JMenuItem();
		about.setAction(getAction("showAboutBox"));
		about.setIcon(null);
		getMenu(root, Arrays.asList(new String[] {"Файл"})).add(quit);
		getMenu(root, Arrays.asList(new String[] {"Помощь"})).add(about);
		// Все это дело поместим в бар
		menuBar.removeAll();
		for(Component i : root.getMenuComponents()) {
			menuBar.add(i);
		}
	}
	
	private JMenu getMenu(JMenu m, List<String> nextPath) {
		if(nextPath.size()==0) return m;
		String s = nextPath.get(0);
		List<String> p;
		if(nextPath.size()>1) {
			p = nextPath.subList(1, nextPath.size());
		} else {
			p = new ArrayList<String>();
		}
		for(Component i : m.getMenuComponents()) {
			if(s.equals(i.getName())) {
				return getMenu((JMenu)i,p);
			}
		}
		JMenu k = new JMenu(s);
		k.setName(s);
		m.add(k);
		return getMenu(k, p);
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
