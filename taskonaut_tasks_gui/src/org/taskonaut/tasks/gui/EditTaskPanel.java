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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;


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
        xTable1.setComponentPopupMenu(menu);
        xTable1.packAll();
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
//		System.out.println(typeBox.getHeight() + ":" + typeBox.getWidth());
//        System.out.println(statusBox.getHeight() + ":" + statusBox.getWidth());
//        System.out.println(priorityBox.getHeight() + ":" + priorityBox.getWidth());
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
        xTable1.setModel(m);
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
        int i = xTable1.convertRowIndexToModel(xTable1.getSelectedRow());
        TimeLogItem tt = ((TimeTableModel)xTable1.getModel()).getDataRow(i);
        if(tt==null) return;
        if(JOptionPane.showConfirmDialog(null, "Удалить запись?",
                "Предупреждение", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)
            return;
//        TimeLogger.getInstance().removeLogItem(tt.getID());
//        TimeLogger.getInstance().save();
        TaskStoreServiceConnector.getStore().deleteTimeLog(tt.getID());
        TimeTableModel m = new TimeTableModel();
        m.setData(TaskStoreServiceConnector.getStore().readTimeLogItems(t.getID()));
        xTable1.setModel(m);
        this.repaint();
    }                                              

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int i = xTable1.convertRowIndexToModel(xTable1.getSelectedRow());
        TimeLogItem tt = ((TimeTableModel)xTable1.getModel()).getDataRow(i);
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

    private void nameFieldKeyPressed(KeyEvent e) {
		if(e.getKeyCode()==10) {
			parentPanel.onCloseOk();
		} else if(e.getKeyCode()==27) {
			parentPanel.onCloseCancel();
		}
	}
    
    private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		nameField = new JTextField();
		panel1 = new JPanel();
		label3 = new JLabel();
		startDate = new JXDatePicker();
		startDate.setEditable(false);
		label2 = new JLabel();
		endDate = new DateTimePicker();
//		SimpleDateFormat df;
//		df = new SimpleDateFormat("dd.MM.yyyy H:mm:ss");
//		endDate.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(df)));
		endDate.setFormats("dd.MM.yyyy H:mm");
		label4 = new JLabel();
		scrollPane1 = new JScrollPane();
		commentText = new JTextArea();
		panel2 = new JPanel();
		label5 = new JLabel();
		statusBox = new JComboBox();
		label6 = new JLabel();
		typeBox = new JComboBox();
		label7 = new JLabel();
		relationTask = new JComboBox();
		panel3 = new JPanel();
		label8 = new JLabel();
		priorityBox = new JComboBox();
		label9 = new JLabel();
		ownerField = new JTextField();
		scrollPane2 = new JScrollPane();
		xTable1 = new JXTable();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			"default, $lcgap, default:grow",
			"3*(default, $lgap), 35dlu:grow, 2*($lgap, default), $lgap, fill:70dlu:grow"));

		//---- label1 ----
		label1.setText("Название:");
		add(label1, cc.xy(1, 1));
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				nameFieldKeyPressed(e);
			}
		});
		add(nameField, cc.xy(3, 1));

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- label3 ----
			label3.setText("Дата создания:");
			panel1.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel1.add(startDate, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- label2 ----
			label2.setText("Срок исполнения:");
			panel1.add(label2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel1.add(endDate, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		add(panel1, cc.xywh(1, 3, 3, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));

		//---- label4 ----
		label4.setText("Описание задачи:");
		add(label4, cc.xywh(1, 5, 3, 1));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(commentText);
		}
		add(scrollPane1, cc.xywh(1, 7, 3, 1, CellConstraints.FILL, CellConstraints.FILL));

		//======== panel2 ========
		{
			panel2.setLayout(new GridBagLayout());
			((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
			((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- label5 ----
			label5.setText("Статус:");
			panel2.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel2.add(statusBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- label6 ----
			label6.setText("Тип:");
			panel2.add(label6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel2.add(typeBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- label7 ----
			label7.setText("Связано с:");
			panel2.add(label7, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			relationTask.addItem("Выбери задачу для перехода");
			panel2.add(relationTask, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		add(panel2, cc.xywh(1, 9, 3, 1));

		//======== panel3 ========
		{
			panel3.setLayout(new FormLayout(
				"3*(default, $lcgap), default:grow",
				"default"));

			//---- label8 ----
			label8.setText("Приоритет:");
			panel3.add(label8, cc.xy(1, 1));
			panel3.add(priorityBox, cc.xy(3, 1));

			//---- label9 ----
			label9.setText("Исполнитель:");
			panel3.add(label9, cc.xy(5, 1));
			panel3.add(ownerField, cc.xy(7, 1));
		}
		add(panel3, cc.xywh(1, 11, 3, 1));

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(xTable1);
		}
		add(scrollPane2, cc.xywh(1, 13, 3, 1, CellConstraints.FILL, CellConstraints.FILL));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		typeBox.setPreferredSize(new Dimension(81, 25));
		statusBox.setPreferredSize(new Dimension(134,25));
		priorityBox.setPreferredSize(new Dimension(96, 25));
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JTextField nameField;
	private JPanel panel1;
	private JLabel label3;
	private JXDatePicker startDate;
	private JLabel label2;
	private DateTimePicker endDate;
	private JLabel label4;
	private JScrollPane scrollPane1;
	private JTextArea commentText;
	private JPanel panel2;
	private JLabel label5;
	private JComboBox statusBox;
	private JLabel label6;
	private JComboBox typeBox;
	private JLabel label7;
	private JComboBox relationTask;
	private JPanel panel3;
	private JLabel label8;
	private JComboBox priorityBox;
	private JLabel label9;
	private JTextField ownerField;
	private JScrollPane scrollPane2;
	private JXTable xTable1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables   
}
