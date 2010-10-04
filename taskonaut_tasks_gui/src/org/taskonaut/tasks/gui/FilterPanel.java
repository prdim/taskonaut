/**
 * 
 */
package org.taskonaut.tasks.gui;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.Set;

import javax.swing.*;

import com.jgoodies.forms.layout.*;
import org.jdesktop.swingx.*;
import org.taskonaut.api.tasks.TaskItem;

/**
 * @author spec
 *
 */
public class FilterPanel extends JPanelExt {
	EnumSet<TaskItem.Status> statusFiltered = EnumSet.noneOf(TaskItem.Status.class);
	EnumSet<TaskItem.Priority> priorityFiltered = EnumSet.noneOf(TaskItem.Priority.class);
	EnumSet<TaskItem.Type> typeFiltered = EnumSet.noneOf(TaskItem.Type.class);
	
	public FilterPanel() {
		initComponents();
		xList1.setCellRenderer(new CheckListRenderer(TaskItem.Status.выполнена));
		xList1.setListData(TaskItem.Status.values());
		xList1.addMouseListener(new MouseAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = xList1.locationToIndex(e.getPoint());
				TaskItem.Status item = (TaskItem.Status) xList1.getModel().getElementAt(index);
				if(statusFiltered.contains(item)) {
					statusFiltered.remove(item);
				} else {
					statusFiltered.add(item);
				}
				Rectangle rect = xList1.getCellBounds(index, index);
				xList1.repaint(rect);
			}
			
		});
		
		xList2.setCellRenderer(new CheckListRenderer(TaskItem.Priority.высокий));
		xList2.setListData(TaskItem.Priority.values());
		xList2.addMouseListener(new MouseAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = xList2.locationToIndex(e.getPoint());
				TaskItem.Priority item = (TaskItem.Priority) xList2.getModel().getElementAt(index);
				if(priorityFiltered.contains(item)) {
					priorityFiltered.remove(item);
				} else {
					priorityFiltered.add(item);
				}
				Rectangle rect = xList2.getCellBounds(index, index);
				xList2.repaint(rect);
			}
			
		});
		xList3.setCellRenderer(new CheckListRenderer(TaskItem.Type.задача));
		xList3.setListData(TaskItem.Type.values());
		xList3.addMouseListener(new MouseAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = xList3.locationToIndex(e.getPoint());
				TaskItem.Type item = (TaskItem.Type) xList3.getModel().getElementAt(index);
				if(typeFiltered.contains(item)) {
					typeFiltered.remove(item);
				} else {
					typeFiltered.add(item);
				}
				Rectangle rect = xList3.getCellBounds(index, index);
				xList3.repaint(rect);
			}
			
		});
		
		// Выполненные задачи отфильтруем по умолчанию
		statusFiltered.add(TaskItem.Status.выполнена);
		statusFiltered.add(TaskItem.Status.отменена);
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.tasks.gui.JPanelExt#checkOk()
	 */
	@Override
	public boolean checkOk() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.tasks.gui.JPanelExt#beforeClose()
	 */
	@Override
	public void beforeClose() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Истина - если задачу необходимо скрыть из списка
	 * @param t
	 * @return
	 */
	public boolean isFiltered(TaskItem t) {
		return statusFiltered.contains(t.getStateId()) ||
			typeFiltered.contains(t.getTypeId()) ||
			priorityFiltered.contains(t.getPriorityId());
	}
	
	class CheckListRenderer extends JCheckBox implements ListCellRenderer {
		Object marker;
		
		public CheckListRenderer(Object o) {
		      setBackground(UIManager.getColor("List.textBackground"));
		      setForeground(UIManager.getColor("List.textForeground"));
		      this.marker = o;
		    }

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
		        int index, boolean isSelected, boolean hasFocus) {
		      setEnabled(list.isEnabled());
//		      setSelected(true/*((CheckableItem) value).isSelected()*/);
		      if(marker instanceof TaskItem.Status) {
		    	  setSelected(!statusFiltered.contains(value));
		      } else if(marker instanceof TaskItem.Priority) {
		    	  setSelected(!priorityFiltered.contains(value));
		      } else if(marker instanceof TaskItem.Type) {
		    	  setSelected(!typeFiltered.contains(value));
		      }
		      setFont(list.getFont());
		      setText(value.toString());
		      return this;
		    }
		
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		scrollPane1 = new JScrollPane();
		xList1 = new JXList();
		scrollPane2 = new JScrollPane();
		xList2 = new JXList();
		scrollPane3 = new JScrollPane();
		xList3 = new JXList();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			"2*(default:grow, $lcgap), default:grow",
			"default, $lgap, fill:default:grow"));

		//---- label1 ----
		label1.setText("Статус");
		add(label1, cc.xy(1, 1));

		//---- label2 ----
		label2.setText("Приоритет");
		add(label2, cc.xy(3, 1));

		//---- label3 ----
		label3.setText("Тип");
		add(label3, cc.xy(5, 1));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(xList1);
		}
		add(scrollPane1, cc.xy(1, 3));

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(xList2);
		}
		add(scrollPane2, cc.xy(3, 3));

		//======== scrollPane3 ========
		{
			scrollPane3.setViewportView(xList3);
		}
		add(scrollPane3, cc.xy(5, 3));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JScrollPane scrollPane1;
	private JXList xList1;
	private JScrollPane scrollPane2;
	private JXList xList2;
	private JScrollPane scrollPane3;
	private JXList xList3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
