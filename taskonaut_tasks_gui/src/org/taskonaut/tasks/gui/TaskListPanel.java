/**
 * 
 */
package org.taskonaut.tasks.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.swingx.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.taskonaut.api.tasks.ActiveTask;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.app.MainApplication;
import org.taskonaut.tasks.gui.internal.Activator;

/**
 * Список задач
 * 
 * @author spec
 *
 */
public class TaskListPanel extends JPanelExt {
	private ChangeTaskEventHandler eventHandler;
	
	/**
	 * Конструктор
	 */
	public TaskListPanel() {
//		super();
		initComponents();
		attachTableModel();
		eventHandler = new ChangeTaskEventHandler();
		Activator.regEventHandler(eventHandler, getHandlerServiceProperties("org/taskonaut/tasks/gui/events/*"));
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item1 = new JMenuItem();
		item1.setText("Редактировать");
		item1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TaskItem t = ((TaskListTableModel) xTable1.getModel())
						.getData().get(
								xTable1.convertRowIndexToModel(xTable1
										.getSelectedRow()));
				MainApplication
						.getInstance()
						.getContext()
						.getTaskService()
						.execute(new TaskEdit(MainApplication.getInstance(), t));
			}
		});
		menu.add(item1);
		
		JMenuItem item2 = new JMenuItem();
		item2.setText("Запустить");
		item2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TaskItem t = ((TaskListTableModel) xTable1.getModel())
				.getData().get(
						xTable1.convertRowIndexToModel(xTable1
								.getSelectedRow()));
				if(ActiveTask.getInstance().isActive())
					ActiveTask.getInstance().stop();
				ActiveTask.getInstance().start(t.getID());
				ActiveTask.getInstance().save();
			}
		});
		menu.add(item2);
		
		JMenuItem item3 = new JMenuItem();
		item3.setText("Остановить");
		item3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ActiveTask.getInstance().stop();
				ActiveTask.getInstance().save();
			}
		});
		menu.add(item3);
		
		JMenuItem item4 = new JMenuItem();
		item4.setText("Удалить");
		item4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TaskItem t = ((TaskListTableModel) xTable1.getModel())
				.getData().get(
						xTable1.convertRowIndexToModel(xTable1
								.getSelectedRow()));
				MainApplication
				.getInstance()
				.getContext()
				.getTaskService()
				.execute(new TaskDelete(MainApplication.getInstance(), t));
			}
		});
		menu.add(new JPopupMenu.Separator());
		menu.add(item4);
		xTable1.setComponentPopupMenu(menu);
	}
	
	private void attachTableModel() {
		xTable1.setModel(new TaskListTableModel());
		xTable1.getColumnModel().getColumn(0).setCellRenderer(new StatusCellRenderer());
		xTable1.getColumnModel().getColumn(1).setCellRenderer(new PriorityCellRenderer());
		xTable1.setColumnControlVisible(true);
		xTable1.setRowHeight(22); // Подгоняем высоту строки под иконку
		xTable1.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		xTable1.packAll();
		xTable1.getColumnModel().getColumn(0).setPreferredWidth(24);
		xTable1.getColumnModel().getColumn(1).setPreferredWidth(24);
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.tasks.gui.JPanelExt#checkOk()
	 */
	@Override
	public boolean checkOk() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.tasks.gui.JPanelExt#beforeCloseCancel()
	 */
	@Override
	public void beforeClose() {
		System.out.println("Close it");
		Activator.unregEventHandler(eventHandler);
	}

	/**
	 * По двойному клику на строке вызываем процедуру редактирования задачи
	 * @param e
	 */
	private void xTable1MouseClicked(MouseEvent e) {
		if(e.getClickCount()>=2) {
//			System.out.println(xTable1.convertRowIndexToModel(xTable1.getSelectedRow()));
			TaskItem t = ((TaskListTableModel)xTable1.getModel()).
				getData().get(xTable1.convertRowIndexToModel(xTable1.getSelectedRow()));
			MainApplication.getInstance().getContext().getTaskService().
				execute(new TaskEdit(MainApplication.getInstance(), t));
		}
	}

	/**
	 * Закрываем панель при нажатии на кнопку
	 * @param e
	 */
	private void okButtonActionPerformed(ActionEvent e) {
		parentPanel.onClose();
	}

	/**
	 * Обработка реакции на кнопку фильтра
	 * @param e
	 */
	private void filterButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}
	
	/**
	 * Task редактирования задачи
	 * @author spec
	 *
	 */
	private class TaskEdit extends Task<Void, Void> {
		private TaskItem ts;

		public TaskEdit(Application application) {
			super(application);
		}
		
		public TaskEdit(Application app, TaskItem t) {
			super(app);
			ts = t;
		}

		@Override
		protected Void doInBackground() throws Exception {
//			DefaultDialog d = new DefaultDialog(new JFrame(), true);
			EditTaskPanel p = new EditTaskPanel(ts);
			InternalFrameDialog d = new InternalFrameDialog(p);
			d.setTitle("Редактирование задачи " + ts.getName());
			((MainApplication)MainApplication.getInstance()).addInternalFrame(d);
//			d.setPanel(p);
//			d.setTitle("Редактирование задачи");
//			d.setVisible(true);
//			if(p.ok) {
//				setMessage("Задача изменена");
////				TaskList.getInstance().putTask(p.getTask());
//				TaskStoreServiceConnector.getStore().saveTask(p.getTask());
//				Activator.getEventAdmin().postEvent(
//						new Event("org/taskonaut/tasks/gui/events/edit_task", 
//								getProperties(p.getTask().getID())));
//				// TODO запись в БД
//			} else {
//				setMessage("Операция отменена пользователем");
//			}
			return null;
		}
		
		private Dictionary<String, Object> getProperties(Long id) {
			Dictionary<String, Object> result = new Hashtable<String, Object>();
			result.put("task_id", id);
			return result;
		}
	}
	
	/**
	 * Таск удаления задачи
	 * @author spec
	 *
	 */
	private class TaskDelete extends Task<Void, Void> {
		private TaskItem ts = null;

		public TaskDelete(Application application) {
			super(application);
		}
		
		public TaskDelete(Application app, TaskItem t) {
			super(app);
			ts = t;
		}

		@Override
		protected Void doInBackground() throws Exception {
			if(ts==null) return null;
			if(JOptionPane.showConfirmDialog(null, "Удалить задачу \"" + ts.getName() + "\"?",
	                "Предупреждение", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION) {
				setMessage("Операция отменена пользователем");
	            return null;
			}
			TaskStoreServiceConnector.getStore().deleteAllTimeLog(ts.getID());
			TaskStoreServiceConnector.getStore().deleteTask(ts.getID());
			Activator.getEventAdmin().postEvent(
					new Event("org/taskonaut/tasks/gui/events/delete_task", 
							getProperties(ts.getID())));
			setMessage("Задача удалена");
			return null;
		}
		
		private Dictionary<String, Object> getProperties(Long id) {
			Dictionary<String, Object> result = new Hashtable<String, Object>();
			result.put("task_id", id);
			return result;
		}
	}
	
	/**
	 * Обработчик события изменения задачи
	 * Необходимо обновить список задач
	 * @author spec
	 *
	 */
	private class ChangeTaskEventHandler implements EventHandler {

		@Override
		public void handleEvent(Event event) {
			System.out.println("Event: " + event.getTopic());
			System.out.println("Change task: " + event.getProperty("task_id"));
			if("org/taskonaut/tasks/gui/events/edit_task".equals(event.getTopic())) {
				// При редактировании задачи просто обновим данные в таблице
				((TaskListTableModel)xTable1.getModel()).updateData();
//				xTable1.packAll();
				contentPanel.repaint();
			} else {
				// При добавлении новых данных нужно переформировать таблицу полностью
				attachTableModel();
				contentPanel.repaint();
			}
		}
		
	}
	
	/**
	 * Возвращает фильтр по топику события
	 * @param topics
	 * @return
	 */
	private Dictionary<String, Object> getHandlerServiceProperties(String... topics) {
	    Dictionary<String, Object> result = new Hashtable<String, Object>();
	    result.put(EventConstants.EVENT_TOPIC, topics);
	    return result;
	  }
	
	/**
	 * Рисует ячейку таблицы со статусом задачи
	 * @author spec
	 *
	 */
	private class StatusCellRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row, int col) {
			JPanel p = new JPanel(new BorderLayout());
			TaskItem t = ((TaskListTableModel)xTable1.getModel()).
				getData().get(xTable1.convertRowIndexToModel(row));
			if(ActiveTask.getInstance().getActiveTaskId() == t.getID()) {
				Icon ico = MainApplication.getInstance().getContext().
				getResourceMap(TaskListPanel.class).getIcon("ico_play");
				p.add(new JLabel(ico));
				p.setToolTipText("Текущая");
			} else if(TaskItem.Status.запланирована.equals(value)) {
				Icon ico = MainApplication.getInstance().getContext().
					getResourceMap(TaskListPanel.class).getIcon("ico_chronometer");
				p.add(new JLabel(ico));
				p.setToolTipText(TaskItem.Status.запланирована.name());
			} else if(TaskItem.Status.выполняется.equals(value)) {
				Icon ico = MainApplication.getInstance().getContext().
					getResourceMap(TaskListPanel.class).getIcon("ico_player_time");
				p.add(new JLabel(ico));
				p.setToolTipText(TaskItem.Status.выполняется.name());
			} else if(TaskItem.Status.выполнена.equals(value)) {
				Icon ico = MainApplication.getInstance().getContext().
					getResourceMap(TaskListPanel.class).getIcon("ico_success");
				p.add(new JLabel(ico));
				p.setToolTipText(TaskItem.Status.выполнена.name());
			} else if(TaskItem.Status.отложена.equals(value)) {
				Icon ico = MainApplication.getInstance().getContext().
					getResourceMap(TaskListPanel.class).getIcon("ico_cancel2");
				p.add(new JLabel(ico));
				p.setToolTipText(TaskItem.Status.отложена.name());
			} else if(TaskItem.Status.отменена.equals(value)) {
				Icon ico = MainApplication.getInstance().getContext().
					getResourceMap(TaskListPanel.class).getIcon("ico_cancel1");
				p.add(new JLabel(ico));
				p.setToolTipText(TaskItem.Status.отменена.name());
			}
			return p;
		}
		
	}
	
	/**
	 * Рисует ячейку таблицы с приоритетом задачи
	 * @author spec
	 *
	 */
	private class PriorityCellRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row, int col) {
			JPanel p = new JPanel(new BorderLayout());
			if(TaskItem.Priority.средний.equals(value)) {
				// Ячейка остается пустой
				p.setToolTipText(TaskItem.Priority.средний.name());
			} else if(TaskItem.Priority.низкий.equals(value)) {
				Icon ico = MainApplication.getInstance().getContext().
					getResourceMap(TaskListPanel.class).getIcon("ico_down");
				p.add(new JLabel(ico));
				p.setToolTipText(TaskItem.Priority.низкий.name());
			} else if(TaskItem.Priority.высокий.equals(value)) {
				Icon ico = MainApplication.getInstance().getContext().
					getResourceMap(TaskListPanel.class).getIcon("ico_up");
				p.add(new JLabel(ico));
				p.setToolTipText(TaskItem.Priority.высокий.name());
			} else if(TaskItem.Priority.критично.equals(value)) {
				Icon ico = MainApplication.getInstance().getContext().
					getResourceMap(TaskListPanel.class).getIcon("ico_critical");
				p.add(new JLabel(ico));
				p.setToolTipText(TaskItem.Priority.критично.name());
			}
			return p;
		}
		
	}
	
	/**
	 * Класс модели таблицы со списком задач
	 * @author spec
	 *
	 */
	private class TaskListTableModel extends AbstractTableModel {
		private String[] columns = {"Статус", "Приоритет", "Задача", "Срок"};
		private Vector<TaskItem> data;

		/**
		 * 
		 */
		public TaskListTableModel() {
			data = new Vector<TaskItem>();
//			for(long i : TaskList.getInstance().getTaskList().keySet()) {
//				data.add(TaskList.getInstance().getTask(i));
//			}
			for(TaskItem i : TaskStoreServiceConnector.getStore().readAllTasks()) {
				data.add(i);
			}
		}
		
		/**
		 * Обновить данные в таблицы после их изменения
		 */
		public void updateData() {
			for(int i=0; i<data.size(); i++) {
//				data.set(i, TaskList.getInstance().getTask(data.get(i).getId()));
				data.set(i, TaskStoreServiceConnector.getStore().readTask(data.get(i).getID()));
			}
		}
		
		public Vector<TaskItem> getData() {
			return data;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int c) {
			return columns[c];
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int r, int c) {
			SimpleDateFormat df;
			String s = "";
			switch(c) {
			case 0:
				return data.get(r).getStateId();
			case 1:
				return data.get(r).getPriorityId();
			case 2:
				return data.get(r).getName();
			case 3:
				df = new SimpleDateFormat("yyyy-MM-dd");
				s = df.format(new Date(data.get(r).getExecute()));
				return s;
			}
			return data.get(r).getName();
		}
		
	}

	/**
	 * Инициализация всех компонентов формы
	 */
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
//		dialogPane = new JPanel();
		contentPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		xTable1 = new JXTable();
		buttonBar = new JPanel();
		okButton = new JButton();
		panel1 = new JPanel();
		filterButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== dialogPane ========
		{
			setBorder(Borders.DIALOG_BORDER);
			setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new BorderLayout());

				//======== scrollPane1 ========
				{

					//---- xTable1 ----
					xTable1.setVisibleRowCount(5);
					xTable1.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							xTable1MouseClicked(e);
						}
					});
					scrollPane1.setViewportView(xTable1);
				}
				contentPanel.add(scrollPane1, BorderLayout.CENTER);
			}
			add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(
					"$glue, $button",
					"pref"));

				//---- okButton ----
				okButton.setText("Close");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonBar.add(okButton, cc.xy(2, 1));
			}
			add(buttonBar, BorderLayout.SOUTH);

			//======== panel1 ========
			{
				panel1.setLayout(new FormLayout(
					"2*(default, $lcgap), default",
					"default"));

				//---- filterButton ----
				filterButton.setText("Filter");
				filterButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filterButtonActionPerformed(e);
					}
				});
				panel1.add(filterButton, cc.xy(1, 1));
			}
			add(panel1, BorderLayout.NORTH);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
//	private JPanel dialogPane;
	private JPanel contentPanel;
	private JScrollPane scrollPane1;
	private JXTable xTable1;
	private JPanel buttonBar;
	private JButton okButton;
	private JPanel panel1;
	private JButton filterButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
