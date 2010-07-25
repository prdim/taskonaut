/**
 * 
 */
package org.taskonaut.tasks.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import org.jdesktop.swingx.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.taskonaut.tasks.OneTask;
import org.taskonaut.tasks.TaskList;
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
	 * 
	 */
	public TaskListPanel() {
//		super();
		initComponents();
		xTable1.setModel(new TaskListTableModel());
		eventHandler = new ChangeTaskEventHandler();
		Activator.regEventHandler(eventHandler, getHandlerServiceProperties("org/taskonaut/tasks/gui/events/*"));
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

	private void xTable1MouseClicked(MouseEvent e) {
		// TODO add your code here
	}

	private void okButtonActionPerformed(ActionEvent e) {
		parentPanel.onClose();
	}

	private void filterButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}
	
	private class ChangeTaskEventHandler implements EventHandler {

		@Override
		public void handleEvent(Event event) {
			System.out.println("Event: " + event.getTopic());
			System.out.println("Change task: " + event.getProperty("task_id"));
			xTable1.setModel(new TaskListTableModel());
			contentPanel.repaint();
		}
		
	}
	
	protected Dictionary<String, Object> getHandlerServiceProperties(String... topics) {
	    Dictionary<String, Object> result = new Hashtable<String, Object>();
	    result.put(EventConstants.EVENT_TOPIC, topics);
	    return result;
	  }
	
	private class TaskListTableModel extends AbstractTableModel {
		private String[] columns = {"Задача"};
		private Vector<OneTask> data;

		/**
		 * 
		 */
		public TaskListTableModel() {
			data = new Vector<OneTask>();
			for(long i : TaskList.getInstance().getTaskList().keySet()) {
				data.add(TaskList.getInstance().getTask(i));
			}
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
			return data.get(r).getName();
		}
		
	}

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
