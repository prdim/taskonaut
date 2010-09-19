/**
 * 
 */
package org.taskonaut.tasks.gui;

import com.jgoodies.forms.layout.*;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;
import org.osgi.service.event.Event;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.api.tasks.TimeLogItem;
import org.taskonaut.tasks.gui.internal.Activator;

import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author ProlubnikovDA
 */
public class EditTaskPanel extends JPanelExt {
    private TaskItem t = null;
    public boolean ok = false;
    private boolean isNew = false;
    
    /** Creates new form editTaskPanel */
    public EditTaskPanel() {
        initComponents();
    }

    public EditTaskPanel(TaskItem t, boolean isNew) {
    	this.isNew = isNew;
        initComponents();
        this.t = t;
        fillData();
        
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item1 = new JMenuItem();
        item1.setText("Редактировать");
        item1.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		editMenuItemActionPerformed(evt);
        	}
        });
        menu.add(item1);
        JMenuItem item2 = new JMenuItem();
        item2.setText("Удалить");
        item2.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		deleteMenuItemActionPerformed(evt);
        	}
        });
        menu.add(item2);
        jXTable1.setComponentPopupMenu(menu);
    }

    public void setTask(TaskItem t) {
        this.t = t;
        fillData();
    }

    public TaskItem getTask() {
        readData();
        return t;
    }

    @Override
	public boolean checkOk() {
    	if(nameField.getText().equals("")) return false;
        if(endDate.getDate().before(startDate.getDate())) return false;
        ok = true;
		if (!isNew) {
			readData();
			TaskStoreServiceConnector.getStore().saveTask(t);
			Activator.getEventAdmin().postEvent(
					new Event("org/taskonaut/tasks/gui/events/edit_task",
							getProperties(t.getID())));
		}
        return true;
	}

    private Dictionary<String, Object> getProperties(Long id) {
		Dictionary<String, Object> result = new Hashtable<String, Object>();
		result.put("task_id", id);
		return result;
	}
    
	@Override
	public void beforeClose() {
//		ok = false;
	}


	/**
     * Заполнение данных в панели из переменных
     */
    private void fillData() {
        if(t==null) return;
        commentText.setText(t.getComment());
        endDate.setDate(new Date(t.getExecute()));
        nameField.setText(t.getName());
        ownerField.setText(t.getOwner());
        startDate.setDate(new Date(t.getID()));
        typeBox.setModel(new DefaultComboBoxModel(TaskItem.Type.values()));
        typeBox.setSelectedItem(t.getTypeId());
        statusBox.setModel(new DefaultComboBoxModel(TaskItem.Status.values()));
        statusBox.setSelectedItem(t.getStateId());
        priorityBox.setModel(new DefaultComboBoxModel(TaskItem.Priority.values()));
        priorityBox.setSelectedItem(t.getPriorityId());
        TimeTableModel m = new TimeTableModel();
        m.setData(TaskStoreServiceConnector.getStore().readTimeLogItems(t.getID()));
        jXTable1.setModel(m);
    }

    /**
     * Чтение данных из панели и запись их в переенную
     */
    private void readData() {
        t.setComment(commentText.getText());
        t.setExecute(endDate.getDate().getTime());
        t.setName(nameField.getText());
        t.setOwner(ownerField.getText());
        t.setType(((TaskItem.Type)typeBox.getSelectedItem()).name());
        t.setState(((TaskItem.Status)statusBox.getSelectedItem()).name());
        t.setPriority(((TaskItem.Priority)priorityBox.getSelectedItem()).name());
    }
    
//    public boolean check() {
//        if(nameField.getText().equals("")) return false;
//        if(endDate.getDate().before(startDate.getDate())) return false;
//        return true;
//    }

    /**
     * Модель таблицы с данными времени выполнения
     */
    private class TimeTableModel extends AbstractTableModel {
        private final String[] colNames = {
            "Начало", "Продолжительность", "Коментарий"
        };
        private List<TimeLogItem> data = new ArrayList<TimeLogItem>();

        public void setData(List<TimeLogItem> data) {
            this.data = data;
        }

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        public int getRowCount() {
            if(data.size()<=0) return 0;
            return data.size()+1;
        }

        public int getColumnCount() {
            return colNames.length;
        }

        public TimeLogItem getDataRow(int rowIndex) {
            if(rowIndex >= data.size()) return null;
            return data.get(rowIndex);
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            String s = "";
            SimpleDateFormat df;
            if(rowIndex>=data.size()){
                switch (columnIndex) {
                    case 0:
                        s = "ВСЕГО:";
                        break;
                    case 1:
                        long c = 0;
                        for(TimeLogItem i : data) {
                            c += i.getPeriod();
                        }
                        df = new SimpleDateFormat(":mm:ss");
                        df.setTimeZone(TimeZone.getTimeZone("GMT"));
                        s = String.valueOf(c/3600000) + df.format(new Date(c));
                        break;
                }
                return s;
            }
            TimeLogItem t = data.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//                    df.setTimeZone(TimeZone.getTimeZone("GMT"));
                    s = df.format(new Date(t.getID()));
                    break;
                case 1:
                    df = new SimpleDateFormat("HH:mm:ss");
                    df.setTimeZone(TimeZone.getTimeZone("GMT"));
                    s = df.format(new Date(t.getPeriod()));
                    break;
                case 2:
                    s = t.getComment();
            }
            return s;
        }

    }                     

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                               
        int i = jXTable1.convertRowIndexToModel(jXTable1.getSelectedRow());
        TimeLogItem tt = ((TimeTableModel)jXTable1.getModel()).getDataRow(i);
        if(tt==null) return;
        if(JOptionPane.showConfirmDialog(null, "Удалить запись?",
                "Предупреждение", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)
            return;
//        TimeLogger.getInstance().removeLogItem(tt.getID());
//        TimeLogger.getInstance().save();
        TaskStoreServiceConnector.getStore().deleteTimeLog(tt.getID());
        TimeTableModel m = new TimeTableModel();
        m.setData(TaskStoreServiceConnector.getStore().readTimeLogItems(t.getID()));
        jXTable1.setModel(m);
        this.repaint();
    }                                              

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int i = jXTable1.convertRowIndexToModel(jXTable1.getSelectedRow());
        TimeLogItem tt = ((TimeTableModel)jXTable1.getModel()).getDataRow(i);
        if(tt==null) return;
        DefaultDialog d = new DefaultDialog(new Frame(), true);
        TimeLogEditPanel p = new TimeLogEditPanel(tt);
        d.setPanel(p);
        d.setTitle("Корректировка времени");
        d.setVisible(true);
//        String s = JOptionPane.showInputDialog("Коментарий:", tt.getComment());
//        if(s==null) return;
//        tt.setComment(s);
//        TimeLogger.getInstance().save();
        if(p.isOk()) {
        	tt = p.getData();
        	TaskStoreServiceConnector.getStore().saveTimeLog(tt);
        }
    }                                                            

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
//		ResourceBundle bundle = ResourceBundle.getBundle("EditTaskPanel");
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.taskonaut.app.MainApplication.class).getContext().getResourceMap(EditTaskPanel.class);
		label2 = new JLabel();
		nameField = new JTextField();
		label1 = new JLabel();
		startDate = new JXDatePicker();
		startDate.setEditable(false);
		endDate = new JXDatePicker();
		scrollPane1 = new JScrollPane();
		commentText = new JTextArea();
		label3 = new JLabel();
		statusBox = new JComboBox();
		label4 = new JLabel();
		typeBox = new JComboBox();
		label5 = new JLabel();
		relationTask = new JComboBox();
		label6 = new JLabel();
		priorityBox = new JComboBox();
		label7 = new JLabel();
		ownerField = new JTextField();
		scrollPane2 = new JScrollPane();
		jXTable1 = new JXTable();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			"default, $lcgap, default:grow, $lcgap, default, $lcgap, default:grow, $lcgap, default, $lcgap, default:grow",
			"2*(default, $lgap), fill:30dlu, 2*($lgap, default), $lgap, fill:70dlu:grow"));

		//---- label2 ----
		label2.setText(resourceMap.getString("label2.text"));
		add(label2, cc.xy(1, 1));
		add(nameField, cc.xywh(3, 1, 9, 1));

		//---- label1 ----
		label1.setText(resourceMap.getString("label1.text"));
		add(label1, cc.xy(1, 3));
		add(startDate, cc.xywh(5, 3, 3, 1));
		add(endDate, cc.xywh(9, 3, 3, 1));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(commentText);
		}
		add(scrollPane1, cc.xywh(1, 5, 11, 1));

		//---- label3 ----
		label3.setText(resourceMap.getString("label3.text"));
		add(label3, cc.xy(1, 7));
		add(statusBox, cc.xy(3, 7));

		//---- label4 ----
		label4.setText(resourceMap.getString("label4.text"));
		add(label4, cc.xy(5, 7));
		add(typeBox, cc.xy(7, 7));

		//---- label5 ----
		label5.setText(resourceMap.getString("label5.text"));
		add(label5, cc.xy(9, 7));
		add(relationTask, cc.xy(11, 7));

		//---- label6 ----
		label6.setText(resourceMap.getString("label6.text"));
		add(label6, cc.xy(1, 9));
		add(priorityBox, cc.xy(3, 9));

		//---- label7 ----
		label7.setText(resourceMap.getString("label7.text"));
		add(label7, cc.xy(5, 9));
		add(ownerField, cc.xywh(7, 9, 5, 1));

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(jXTable1);
		}
		add(scrollPane2, cc.xywh(1, 11, 11, 1));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label2;
	private JTextField nameField;
	private JLabel label1;
	private JXDatePicker startDate;
	private JXDatePicker endDate;
	private JScrollPane scrollPane1;
	private JTextArea commentText;
	private JLabel label3;
	private JComboBox statusBox;
	private JLabel label4;
	private JComboBox typeBox;
	private JLabel label5;
	private JComboBox relationTask;
	private JLabel label6;
	private JComboBox priorityBox;
	private JLabel label7;
	private JTextField ownerField;
	private JScrollPane scrollPane2;
	private JXTable jXTable1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables    
}
