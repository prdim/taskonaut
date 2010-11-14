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

package org.taskonaut.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.api.tasks.TimeLogItem;
import org.taskonaut.app.GuiDefaultSize;
import org.taskonaut.app.MainApplication;
import org.taskonaut.tasks.gui.EditTaskPanel;
import org.taskonaut.tasks.gui.InternalFrameDialog;
import org.taskonaut.tasks.gui.JPanelExt;
import org.taskonaut.tasks.gui.TaskListPanel;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class TimeReportPanel extends JPanelExt {
    private Vector<TableElement> table = new Vector<TableElement>();
    private HashMap<Long, TableElement> table2 = new HashMap<Long, TableElement>();
    private boolean f_refresh = false;
    private long all = 0;

    /** Creates new form TimeReportPanel */
    public TimeReportPanel() {
        initComponents();
        if(GuiDefaultSize.getInstance().isFormStored("TimeReportPanel")) {
			this.setPreferredSize(GuiDefaultSize.getInstance().getDimensionForm("TimeReportPanel"));
		}
        date1.setDate(new Date(System.currentTimeMillis()));
        date2.setDate(new Date(System.currentTimeMillis() + 24*3600000));
        fillData();
        f_refresh = true;
    }
    
    /**
     * Обновление данных в таблице
     */
    private void fillData() {
        table = new Vector<TableElement>();
        table2 = new HashMap<Long, TableElement>();
        all = 0;
        for(TimeLogItem t : TaskStoreServiceConnector.getStore().readAllTimeLogItems()) {
            if(t.getID()>=date1.getDate().getTime() && t.getID()<=date2.getDate().getTime()) {
                if(isGroupButton.isSelected()) {
                    if(table2.containsKey(t.getTaskId()))
                        table2.get(t.getTaskId()).add(t.getPeriod());
                    else
                        table2.put(t.getTaskId(), new TableElement(t.getID(), t.getPeriod(),
                            TaskStoreServiceConnector.getStore().readTask(t.getTaskId()).getName(),
                            t.getTaskId(), t.getComment()));
                } else {
                	// TODO Почему возникают записи со ссылкой на нулевую задачу?
                	System.out.println(t.getID() + ", " + t.getTaskId() + ", " + t.getPeriod());
                    if(t.getTaskId()!=0)
                	table.add(new TableElement(t.getID(), t.getPeriod(),
                    		TaskStoreServiceConnector.getStore().readTask(t.getTaskId()).getName(),
                            t.getTaskId(), t.getComment()));
                    else
                    	table.add(new TableElement(t.getID(), t.getPeriod(),
                        		"-",
                                t.getTaskId(), t.getComment()));
                }
                all += t.getPeriod();
            }
        }
        if(isGroupButton.isSelected()) {
            for(TableElement t : table2.values()) {
                table.add(t);
            }
        }
        jXTable1.setModel(new TimeTableModel());
        jXTable1.packAll();
    }

    private class TimeTableModel extends AbstractTableModel {
        private String[] names = {"id", "Начало", "Продолжительность", "Задача", "Примечание"};
        private String[] names2 = {"id", "Затраты", "Задача"};

        public int getRowCount() {
            return table.size()>0 ? (table.size() + 1) : 0;
        }

        public int getColumnCount() {
            if(isGroupButton.isSelected())
                return names2.length;
            else
                return names.length;
        }

        @Override
        public String getColumnName(int column) {
            if(isGroupButton.isSelected())
                return names2[column];
            else
                return names[column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            SimpleDateFormat df;
            if(rowIndex==table.size()) {
                switch (columnIndex) {
                    case 1:
                        return "ВСЕГО:";
                    case 2:
                        df = new SimpleDateFormat(":mm:ss");
                        df.setTimeZone(TimeZone.getTimeZone("GMT"));
                        return String.valueOf(all/3600000) + df.format(new Date(all));
                }
                return "";
            }
            if(isGroupButton.isSelected()) {
                switch (columnIndex) {
                    case 0:
                        return rowIndex;
                    case 1:
                        df = new SimpleDateFormat(":mm:ss");
                        df.setTimeZone(TimeZone.getTimeZone("GMT"));
                        return String.valueOf(table.get(rowIndex).period/3600000)
                                + df.format(new Date(table.get(rowIndex).period));
                    case 2:
                        return table.get(rowIndex).taskName;
                }
                return "";
            }
            switch (columnIndex) {
                case 0:
                    return rowIndex;
                case 1:
                    df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    return df.format(new Date(table.get(rowIndex).time));
                case 2:
                    df = new SimpleDateFormat(":mm:ss");
                    df.setTimeZone(TimeZone.getTimeZone("GMT"));
                    return String.valueOf(table.get(rowIndex).period/3600000)
                            + df.format(new Date(table.get(rowIndex).period));
                case 3:
                    return table.get(rowIndex).taskName;
                case 4:
                    return table.get(rowIndex).comment;
            }
            return "";
        }

    }

    private class TableElement {
//        public long id = 0;
        public long time = 0;
        public long period = 0;
        public String taskName = "";
        public long taskId = 0;
        public String comment = "";

        public TableElement(long time, long period, String taskName, long taskId, String comment) {
            this.time = time;
            this.period = period;
            this.taskName = taskName;
            this.taskId = taskId;
            this.comment = comment;
        }

        public void add(long t) {
            period += t;
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

        date1 = new org.jdesktop.swingx.JXDatePicker();
        jXLabel1 = new org.jdesktop.swingx.JXLabel();
        jXLabel2 = new org.jdesktop.swingx.JXLabel();
        date2 = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane1 = new javax.swing.JScrollPane();
        jXTable1 = new org.jdesktop.swingx.JXTable();
        isGroupButton = new javax.swing.JToggleButton();

        setName("Form"); // NOI18N

        date1.setName("date1"); // NOI18N
        date1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date1PropertyChange(evt);
            }
        });

//        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.taskonaut.app.MainApplication.class).getContext().getResourceMap(TimeReportPanel.class);
        jXLabel1.setText("От"); // NOI18N
        jXLabel1.setName("jXLabel1"); // NOI18N

        jXLabel2.setText("До"); // NOI18N
        jXLabel2.setName("jXLabel2"); // NOI18N

        date2.setName("date2"); // NOI18N
        date2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date2PropertyChange(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

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
        jXTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jXTable1.setColumnControlVisible(true);
        jXTable1.setName("jXTable1"); // NOI18N
        jXTable1.setShowGrid(true);
        jXTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jXTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jXTable1);

        isGroupButton.setText("Группировать по задачам"); // NOI18N
        isGroupButton.setName("isGroupButton"); // NOI18N
        isGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isGroupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(isGroupButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isGroupButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>                        

    private void date1PropertyChange(java.beans.PropertyChangeEvent evt) {                                     
        if("date".equals(evt.getPropertyName()) && f_refresh) 
            fillData();
//        System.out.println(evt.getPropertyName() + " - " + evt.getNewValue());
    }                                    

    private void date2PropertyChange(java.beans.PropertyChangeEvent evt) {                                     
        if("date".equals(evt.getPropertyName()) && f_refresh)
            fillData();
    }                                    

    private void jXTable1MouseClicked(java.awt.event.MouseEvent evt) {                                      
        if(evt.getClickCount()>=2) {
            int i = jXTable1.convertRowIndexToModel(jXTable1.getSelectedRow());
//            OneTask t = TaskList.getInstance().getTask(table.get(i).taskId);
//            EditTaskDialog d = new EditTaskDialog(null, true);
//            d.setTaskPanel(new EditTaskPanel(t));
//            d.setVisible(true);
//            if(!d.isOk()) return;
//            t = d.getTaskPanel().getTask();
//            TaskList.getInstance().putTask(t);
//            TaskList.getInstance().save();
            
            TaskItem t = TaskStoreServiceConnector.getStore().readTask(table.get(i).taskId);
            EditTaskPanel p = new EditTaskPanel(t, false);
			InternalFrameDialog d = new InternalFrameDialog(p);
			d.setTitle("Редактирование задачи " + t.getName());
			((MainApplication)MainApplication.getInstance()).addInternalFrame(d);
        }
    }        
    
    

    private void isGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        fillData();
    }                                             


    // Variables declaration - do not modify                     
    private org.jdesktop.swingx.JXDatePicker date1;
    private org.jdesktop.swingx.JXDatePicker date2;
    private javax.swing.JToggleButton isGroupButton;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXLabel jXLabel1;
    private org.jdesktop.swingx.JXLabel jXLabel2;
    private org.jdesktop.swingx.JXTable jXTable1;
    // End of variables declaration                   

	@Override
	public boolean checkOk() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void beforeClose() {
		GuiDefaultSize.getInstance().storeDimensionForm("TimeReportPanel", this.getSize());
		GuiDefaultSize.getInstance().save();
	}

}
