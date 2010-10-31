/*
 * PropertyEditPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package org.taskonaut.customizer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.swingx.JXTable;
import org.taskonaut.api.AbstractProperties;
import org.taskonaut.tasks.gui.JPanelExt;

/**
 * Панель редактирования свойств программы
 *
 * @author  Dmitry Prolubnikov
 */
public class PropertyEditPanel extends JPanelExt {
	private List<AbstractProperties> props;

	/** Creates new form PropertyEditPanel */
	public PropertyEditPanel(List<AbstractProperties> props) {
		this.props = props;
		initComponents();
	}

	private void initComponents() {
		JTabbedPane jTabbedPane1;
		JScrollPane jScrollPane1;
		JXTable table;
		PropertyDescriptor[] pr;

		jTabbedPane1 = new JTabbedPane();
		for (AbstractProperties i : props) {
			jScrollPane1 = new JScrollPane();
			table = new JXTable();
			pr = PropertyUtils.getPropertyDescriptors(i);

			setLayout(new java.awt.BorderLayout());

			table.setModel(new PropertyTableModel(pr, i));
			
			EachRowEditor rowEditor = new EachRowEditor(table);
			CheckBoxTableCellRenderer checkBoxRenderer = null;
			EachRowRenderer rowRendererKeys = null;
			EachRowRenderer rowRendererValues = new EachRowRenderer();
			for(int k=0; k<pr.length; k++) {
				DefaultCellEditor editor = null;
				String c = pr[k].getPropertyType().getSimpleName();
				if("String".equals(c)) {
					// TODO
				} else if("int".equals(c) || "long".equals(c)) {
					final NumberCellEditor numEditor = new NumberCellEditor(10, true);
					editor = new DefaultCellEditor(numEditor) {
						public Object getCellEditorValue() {
							return numEditor.getStringValue();
						}
					};
					rowEditor.setEditorAt(k, editor);
				} else if("boolean".equals(c)) {
					if (checkBoxRenderer == null) {
						checkBoxRenderer = new CheckBoxTableCellRenderer();
						checkBoxRenderer.setHorizontalAlignment(JLabel.LEFT);
					}
					rowRendererValues.add(k, checkBoxRenderer);
					rowEditor.setEditorAt(k, new DefaultCellEditor(new JCheckBox()));
				}
			}
			
			TableColumnModel tcm = table.getColumnModel();
			TableColumn column = tcm.getColumn(1);
			column.setCellRenderer(rowRendererValues);
			column.setCellEditor(rowEditor);
			column = tcm.getColumn(0);
			column.setCellRenderer(rowRendererKeys);
			
			table.setSortable(false);
			table.setCellSelectionEnabled(true);
			table.setColumnSelectionAllowed(false);
			table.setRowSelectionAllowed(false);
			table.packAll();
			jScrollPane1.setViewportView(table);

			jTabbedPane1.addTab(i.getTitle(), jScrollPane1);

			add(jTabbedPane1, java.awt.BorderLayout.CENTER);
		}
	}
	
	@Override
	public boolean checkOk() {
		try {
			for(AbstractProperties i : props) {
				i.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void beforeClose() {
		// TODO Auto-generated method stub
		
	}

	private class PropertyTableModel extends DefaultTableModel {
		private PropertyDescriptor[] prd = null;
		private AbstractProperties prp = null;
		private final String[] COLUMN_NAMES = {"Опция", "Значение"}; 
		
		public PropertyTableModel(PropertyDescriptor[] prd, AbstractProperties prp) {
			this.prd = prd;
			this.prp = prp;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 2;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			if(prd == null) return 0;
			return prd.length;
		}
		
		public PropertyDescriptor getPropertyDescriptor(int row) {
			return prd[row];
		}
		
		public Object getProperty(int row) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
			return PropertyUtils.getProperty(prp, prd[row].getName());
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int column) {
			if(prd == null) return "";
			if(column==0) {
				return prd[row].getDisplayName();
			} else {
				try {
					return PropertyUtils.getProperty(prp, prd[row].getName());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return "";
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object o, int row, int col) {
//			System.out.println("set " + row + " - " + o);
			try {
				String c = prd[row].getPropertyType().getSimpleName();
				if("int".equals(c)) {
					PropertyUtils.setProperty(prp, prd[row].getName(), Integer.parseInt(o.toString()));
				} else if("long".equals(c)) {
					PropertyUtils.setProperty(prp, prd[row].getName(), Long.parseLong(o.toString()));
				} else if("boolean".equals(c)) {
					PropertyUtils.setProperty(prp, prd[row].getName(), !(Boolean)PropertyUtils.getProperty(prp, prd[row].getName()));
				} else {
					PropertyUtils.setProperty(prp, prd[row].getName(), o);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return column !=0;
		}

		
	}
}