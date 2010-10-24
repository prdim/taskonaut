/*
 * PropertyEditPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package org.taskonaut.customizer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.swingx.JXTable;
import org.taskonaut.api.AbstractProperties;
import org.taskonaut.tasks.gui.JPanelExt;

/**
 *
 * @author  __USER__
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
		JXTable jTable1;
		PropertyDescriptor[] pr;

		jTabbedPane1 = new JTabbedPane();
		for (AbstractProperties i : props) {
			jScrollPane1 = new JScrollPane();
			jTable1 = new JXTable();
			pr = PropertyUtils.getPropertyDescriptors(i);

			setLayout(new java.awt.BorderLayout());

			jTable1.setModel(new PropertyTableModel(pr, i));
			jScrollPane1.setViewportView(jTable1);

			jTabbedPane1.addTab(i.getTitle(), jScrollPane1);

			add(jTabbedPane1, java.awt.BorderLayout.CENTER);
		}
	}
	
	@Override
	public boolean checkOk() {
		// TODO Auto-generated method stub
		return false;
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
		
		
	}
}