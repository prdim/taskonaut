/**
 * 
 */
package org.taskonaut.tasks.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.taskonaut.tasks.OneTask;
import org.taskonaut.tasks.TimeLogItem;
import org.taskonaut.tasks.TimeLogger;


/**
 *
 * @author ProlubnikovDA
 */
public class EditTaskPanel extends javax.swing.JPanel {
    private OneTask t = null;
    
    /** Creates new form editTaskPanel */
    public EditTaskPanel() {
        initComponents();
    }

    public EditTaskPanel(OneTask t) {
        initComponents();
        this.t = t;
        fillData();
    }

    public void setTask(OneTask t) {
        this.t = t;
        fillData();
    }

    public OneTask getTask() {
        readData();
        return t;
    }

    /**
     * Заполнение данных в панели из переменных
     */
    private void fillData() {
        if(t==null) return;
        commentText.setText(t.getComment());
        endDate.setDate(t.getExecute());
        nameField.setText(t.getName());
        ownerField.setText(t.getOwner());
        startDate.setDate(new Date(t.getId()));
        typeBox.setModel(new DefaultComboBoxModel(OneTask.Type.values()));
        typeBox.setSelectedItem(t.getType());
        statusBox.setModel(new DefaultComboBoxModel(OneTask.Status.values()));
        statusBox.setSelectedItem(t.getState());
        priorityBox.setModel(new DefaultComboBoxModel(OneTask.Priority.values()));
        priorityBox.setSelectedItem(t.getPriority());
        TimeTableModel m = new TimeTableModel();
        m.setData(TimeLogger.getInstance().getTaskLog(t.getId()));
        jXTable1.setModel(m);
    }

    /**
     * Чтение данных из панели и запись их в переенную
     */
    private void readData() {
        t.setComment(commentText.getText());
        t.setExecute(endDate.getDate());
        t.setName(nameField.getText());
        t.setOwner(ownerField.getText());
        t.setType((OneTask.Type)typeBox.getSelectedItem());
        t.setState((OneTask.Status)statusBox.getSelectedItem());
        t.setPriority((OneTask.Priority)priorityBox.getSelectedItem());
    }
    
    public boolean check() {
        if(nameField.getText().equals("")) return false;
        if(endDate.getDate().before(startDate.getDate())) return false;
        return true;
    }

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
                    s = df.format(new Date(t.getId()));
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        editMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentText = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        statusBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        typeBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        relationTask = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ownerField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jXTable1 = new org.jdesktop.swingx.JXTable();
        endDate = new org.jdesktop.swingx.JXDatePicker();
        startDate = new org.jdesktop.swingx.JXDatePicker();
        priorityBox = new javax.swing.JComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.taskonaut.app.MainApplication.class).getContext().getResourceMap(EditTaskPanel.class);
        editMenuItem.setText(resourceMap.getString("editMenuItem.text")); // NOI18N
        editMenuItem.setName("editMenuItem"); // NOI18N
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(editMenuItem);

        deleteMenuItem.setText(resourceMap.getString("deleteMenuItem.text")); // NOI18N
        deleteMenuItem.setName("deleteMenuItem"); // NOI18N
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(deleteMenuItem);

        setName("Form"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        nameField.setText(resourceMap.getString("nameField.text")); // NOI18N
        nameField.setName("nameField"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        commentText.setName("commentText"); // NOI18N
        jScrollPane1.setViewportView(commentText);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        statusBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        statusBox.setName("statusBox"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        typeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeBox.setName("typeBox"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        relationTask.setName("relationTask"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        ownerField.setText(resourceMap.getString("ownerField.text")); // NOI18N
        ownerField.setName("ownerField"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jXTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jXTable1.setComponentPopupMenu(jPopupMenu1);
        jXTable1.setName("jXTable1"); // NOI18N
        jXTable1.setShowGrid(true);
        jScrollPane2.setViewportView(jXTable1);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        endDate.setToolTipText(resourceMap.getString("endDate.toolTipText")); // NOI18N
        endDate.setName("endDate"); // NOI18N

        startDate.setToolTipText(resourceMap.getString("startDate.toolTipText")); // NOI18N
        startDate.setEditable(false);
        startDate.setName("startDate"); // NOI18N

        priorityBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        priorityBox.setName("priorityBox"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                        .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(relationTask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priorityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ownerField, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(relationTask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(ownerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priorityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>                        

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                               
        int i = jXTable1.convertRowIndexToModel(jXTable1.getSelectedRow());
        TimeLogItem tt = ((TimeTableModel)jXTable1.getModel()).getDataRow(i);
        if(tt==null) return;
        if(JOptionPane.showConfirmDialog(null, "Удалить запись?",
                "Предупреждение", JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)
            return;
        TimeLogger.getInstance().removeLogItem(tt.getId());
        TimeLogger.getInstance().save();
        TimeTableModel m = new TimeTableModel();
        m.setData(TimeLogger.getInstance().getTaskLog(t.getId()));
        jXTable1.setModel(m);
        this.repaint();
    }                                              

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int i = jXTable1.convertRowIndexToModel(jXTable1.getSelectedRow());
        TimeLogItem tt = ((TimeTableModel)jXTable1.getModel()).getDataRow(i);
        if(tt==null) return;
        String s = JOptionPane.showInputDialog("Коментарий:", tt.getComment());
        if(s==null) return;
        tt.setComment(s);
        TimeLogger.getInstance().save();
    }                                            


    // Variables declaration - do not modify                     
    private javax.swing.JTextPane commentText;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenuItem editMenuItem;
    private org.jdesktop.swingx.JXDatePicker endDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXTable jXTable1;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField ownerField;
    private javax.swing.JComboBox priorityBox;
    private javax.swing.JComboBox relationTask;
    private org.jdesktop.swingx.JXDatePicker startDate;
    private javax.swing.JComboBox statusBox;
    private javax.swing.JComboBox typeBox;
    // End of variables declaration                   

}
