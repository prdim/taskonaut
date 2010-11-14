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

package org.taskonaut.tray;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.taskonaut.api.tasks.ActiveTask;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.MainApplication;
import org.taskonaut.tasks.gui.NewTaskAction;
import org.taskonaut.tray.internal.Activator;

/**
 * 
 * @author Prolubnikov Dmitry
 */
public class Tray /* implements IChangeDataListener */{
	private static Tray me = null;
	static TrayIcon trayIcon = null;
	static SystemTray tray = SystemTray.getSystemTray();
	// TaskonautView win;
	static Image image1;
	static Image image2;
	static Image image3;
	List<TaskItem> taskMenu = new ArrayList<TaskItem>();
	List<TaskItem> lastTaskMenu = new ArrayList<TaskItem>();
	private double traySize = 0;

	public static Tray getInstance() {
		if (me == null) {
			me = new Tray();
		}
		return me;
	}

	// public void onChange(Object o) {
	// System.out.println("!");
	// trayIcon.getPopupMenu().remove(1);
	// trayIcon.getPopupMenu().insert(getMenu(), 1);
	// }

	// public void setWin(TaskonautView win) {
	// this.win = win;
	// }

	public void sTrayShow() { 
		if (SystemTray.isSupported()) {
			traySize = SystemTray.getSystemTray().getTrayIconSize().getHeight();
			image1 = Toolkit.getDefaultToolkit()
					.getImage(
							this.getClass().getResource(
									"resources/icons/clock-16.png"));
			image2 = Toolkit.getDefaultToolkit().getImage(
					this.getClass().getResource(
							"resources/icons/clock_play-16.png"));
			image3 = Toolkit.getDefaultToolkit().getImage(
					this.getClass().getResource(
							"resources/icons/clock_stop-16.png"));
			// image1 = ((ImageIcon)MainApplication.getInstance().getContext().
			// getResourceMap(Tray.class).getIcon("ico_main")).getImage();
			// image2 = ((ImageIcon)MainApplication.getInstance().getContext().
			// getResourceMap(Tray.class).getIcon("ico_play")).getImage();
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainApplication.getInstance().quit(null);
				}
			};
//			ActionListener hideListener = new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					if (((MainApplication) MainApplication.getInstance())
//							.getMainFrame().isVisible())
//						((MainApplication) MainApplication.getInstance())
//								.getMainFrame().setVisible(false);
//					else
//						((MainApplication) MainApplication.getInstance())
//								.getMainFrame().setVisible(true);
//				}
//			};
			ActionListener stopListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!ActiveTask.getInstance().isActive()) {
						return;
					}
					ActiveTask.getInstance().stop();
					ActiveTask.getInstance().save();
					Dictionary<String, Object> p = new Hashtable<String, Object>();
					p.put("task_id", 0);
					Activator.getEventAdmin().postEvent(
							new Event("org/taskonaut/tasks/gui/events/active_task",	p));
					Tray.getInstance().message("Заканчиваю...");
				}
			};
				ActionListener createTaskListener = new ActionListener() {
	
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainApplication.getInstance().getContext().getTaskService().execute(new NewTaskAction().newTaskAction());
					}
					
				};

			PopupMenu popup = new PopupMenu();

			MenuItem createTask = new MenuItem("Новая задача");
			createTask.addActionListener(createTaskListener);
			popup.add(createTask);
			MenuItem itemProps = new MenuItem("Остановить");
			itemProps.addActionListener(stopListener);
			popup.add(itemProps);
			popup.add(getMenu());
			popup.add(getLastMenu());
			MenuItem defaultItem = new MenuItem("Выход");
			defaultItem.addActionListener(listener);
			popup.add(defaultItem);

			trayIcon = new TrayIcon(image1, "Показать главное окно", popup);
//			trayIcon.addActionListener(hideListener);
			trayIcon.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == 1){
						Frame f = ((MainApplication) MainApplication.getInstance()).getMainFrame();
						f.setVisible(true);
						if((f.getState() & Frame.ICONIFIED) != 0) {
							f.setState(f.getState() & Frame.NORMAL);
						}
					}
					
				}
			});
			try {
				tray.add(trayIcon);
			} catch (AWTException ex) {
				// Logger.getLogger(Main.class.getName()).log(Level.SEVERE,
				// null, ex);
			}

			// Регистрируем обработчик события изменения задач
			Activator
					.regEventHandler(
							new ChangeTaskEventHandler(),
							getHandlerServiceProperties("org/taskonaut/tasks/gui/events/*"));
		} else {
			// TODO Как-то сообщить, что трей не доступен
		}
		TrackerThread runner = new TrackerThread();
		runner.start();
		// TaskList.getInstance().addListener(this);
	}

	public Menu getMenu() {
		Menu m = new Menu("Запустить");
		try {
			taskMenu = TaskStoreServiceConnector.getStore().getTasksForStatus(
					TaskItem.Status.выполняется);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = e.getActionCommand();
				for (TaskItem i : taskMenu) {
					if (s.equals(i.getName())) {
						if (ActiveTask.getInstance().isActive()) {
							ActiveTask.getInstance().stop();
							ActiveTask.getInstance().save();
						}
						ActiveTask.getInstance().start(i.getID());
						Dictionary<String, Object> p = new Hashtable<String, Object>();
						p.put("task_id", i.getID());
						Activator.getEventAdmin().postEvent(
								new Event("org/taskonaut/tasks/gui/events/active_task",	p));
//						addLastTask(i);
//						trayIcon.getPopupMenu().remove(3);
//						trayIcon.getPopupMenu().insert(getLastMenu(), 3);
						message("Выполняю " + i.getName());
					}
				}
			}
		};
		for (TaskItem i : taskMenu) {
			MenuItem item = new MenuItem(i.getName());
			item.addActionListener(listener);
			m.add(item);
		}
		return m;
	}
	
	public Menu getLastMenu() {
		Menu m = new Menu("Последние");
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = e.getActionCommand();
				for (TaskItem i : lastTaskMenu) {
					if (s.equals(i.getName())) {
						if (ActiveTask.getInstance().isActive()) {
							ActiveTask.getInstance().stop();
							ActiveTask.getInstance().save();
						}
						ActiveTask.getInstance().start(i.getID());
						Dictionary<String, Object> p = new Hashtable<String, Object>();
						p.put("task_id", i.getID());
						Activator.getEventAdmin().postEvent(
								new Event("org/taskonaut/tasks/gui/events/active_task",	p));
						message("Выполняю " + i.getName());
					}
				}
			}
		};
		for (TaskItem i : lastTaskMenu) {
			MenuItem item = new MenuItem(i.getName());
			item.addActionListener(listener);
			m.add(item);
		}
		return m;
	}
	
	public void addLastTask(TaskItem t) {
		boolean f = false;
		for(TaskItem i : lastTaskMenu) {
			if(i.getID() == t.getID()) {
				f = true;
				break;
			}
		}
		if(!f) {
			lastTaskMenu.add(t);
		}
	}
	
	/**
	 * Обновить список последних задач, удалить из списка уже выполненные задачи
	 */
	public void refreshLastTask() {
		List<TaskItem> tt = new ArrayList<TaskItem>();
		for(TaskItem i : lastTaskMenu) {
			TaskItem t = TaskStoreServiceConnector.getStore().readTask(i.getID());
			if(t!=null) { // задача не удалена из базы
				if(t.getStateId()!=TaskItem.Status.выполнена && 
						t.getStateId()!=TaskItem.Status.отменена && 
						t.getStateId()!=TaskItem.Status.отложена) {
					tt.add(t);
				}
			}
		}
		lastTaskMenu = tt;
	}

	public void setOfflineIcon() {
		trayIcon.setImage(image1);
	}

	public void setWorkIcon() {
		trayIcon.setImage(image2);
	}

	public void message(String msg) {
		trayIcon.displayMessage("Сообщение", msg, TrayIcon.MessageType.INFO);
	}

	class TrackerThread implements Runnable {
		private Thread my = null;

		public void start() {
			if (my == null) {
				my = new Thread(this, "TaskTracker");
				my.start();
			}
		}

		public void stop() {
			my = null;
		}

		public void checkTask() {
			if (!ActiveTask.getInstance().isActive())
				return;

			ActiveTask.getInstance().check();
			ActiveTask.getInstance().save();

			TaskItem t = TaskStoreServiceConnector.getStore().readTask(
					ActiveTask.getInstance().getActiveTaskId());
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			trayIcon.setToolTip(t.getName() + " "
					+ df.format(new Date(ActiveTask.getInstance().getTime())));
		}

		public void run() {
			Thread myThread = Thread.currentThread();
			while (my == myThread) {
				// TODO Понаблюдать за ошибками
				try {
					if (!ActiveTask.getInstance().isActive()) {
						trayIcon.setImage(image2);
					} else {
						trayIcon.setImage(image1);
						checkTask();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(200);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				try {
					if (!ActiveTask.getInstance().isActive()) {
						trayIcon.setImage(image1);
					} else {
						trayIcon.setImage(image2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(1000 + (int) (Math.random() * 15000));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * Обработчик события изменения задачи Необходимо обновить список задач
	 * 
	 * @author spec
	 * 
	 */
	private class ChangeTaskEventHandler implements EventHandler {

		@Override
		public void handleEvent(Event event) {
			if("org/taskonaut/tasks/gui/events/active_task".equals(event.getTopic()) && ActiveTask.getInstance().isActive()) {
				TaskItem t = TaskStoreServiceConnector.getStore().readTask(ActiveTask.getInstance().getActiveTaskId());
				addLastTask(t);
			}
			trayIcon.getPopupMenu().remove(2);
			trayIcon.getPopupMenu().insert(getMenu(), 2);
			refreshLastTask();
			trayIcon.getPopupMenu().remove(3);
			trayIcon.getPopupMenu().insert(getLastMenu(), 3);
		}

	}

	/**
	 * Возвращает фильтр по топику события
	 * 
	 * @param topics
	 * @return
	 */
	private Dictionary<String, Object> getHandlerServiceProperties(
			String... topics) {
		Dictionary<String, Object> result = new Hashtable<String, Object>();
		result.put(EventConstants.EVENT_TOPIC, topics);
		return result;
	}
}
