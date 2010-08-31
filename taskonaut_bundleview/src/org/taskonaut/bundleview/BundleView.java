/*******************************************************************************
 * Copyright (c) 2009 Siemens AG
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kai Toedter - initial API and implementation
 *******************************************************************************/

package org.taskonaut.bundleview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public class BundleView {

	private ImageIcon icon;
	private JComponent view;
	private JTable table;
	private final Bundle[] bundles;
	
	public JComponent getView() {
		return view;
	}

	@SuppressWarnings("serial")
	class TableModel extends AbstractTableModel {

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "";

			case 1:
				return "#";

			case 2:
				return "Symbolic Name";

			case 3:
				return "State";

			default:
				return null;
			}
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return bundles.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Bundle bundle = bundles[rowIndex];
			switch (columnIndex) {
			case 0:
				return bundle.getState() == Bundle.ACTIVE;

			case 1:
				return bundle.getBundleId();

			case 2:
				return bundle.getSymbolicName();

			case 3:
				switch (bundle.getState()) {
				case Bundle.ACTIVE:
					return "ACTIVE";
				case Bundle.INSTALLED:
					return "INSTALLED";
				case Bundle.RESOLVED:
					return "RESOLVED";
				case Bundle.UNINSTALLED:
					return "UNINSTALLED";
				default:
					return "UNKNOWN";
				}

			default:
				return null;
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col == 0) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			Bundle selectedBundle = bundles[row];
			if (selectedBundle.getState() == Bundle.ACTIVE) {
				try {
					selectedBundle.stop();
				} catch (BundleException e1) {
					e1.printStackTrace();
				}
			} else if (selectedBundle.getState() == Bundle.RESOLVED) {
				try {
					selectedBundle.start();
				} catch (BundleException e1) {
					e1.printStackTrace();
				}
			}
			fireTableDataChanged();
		}
	}

	public BundleView() {
		super();

		bundles = Activator.getBundles();

		Runnable uiCreator = new Runnable() {
			public void run() {
				createUI();
			}
		};
		try {
//			SwingUtilities.invokeAndWait(uiCreator);
			createUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createUI() {
		icon = new ImageIcon(this.getClass().getResource("icons/bundle.png"));
		table = new JTable(new TableModel());
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);

		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(0).setMaxWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setMaxWidth(40);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);

		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);

		view = new JPanel();
		view.setLayout(new BorderLayout());

		JPanel selectionPanel = new JPanel();
		JButton selectAll = new JButton("Select All");
		selectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Bundle bundle : bundles) {
					try {
						bundle.start();
					} catch (BundleException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				table.revalidate();
			}
		});
		JButton deselectAll = new JButton("Deselect All");
		deselectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Bundle bundle : bundles) {
					try {
						bundle.stop();
					} catch (BundleException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				table.revalidate();
			}
		});
		selectionPanel.add(selectAll);
		selectionPanel.add(deselectAll);
		view.add(selectionPanel, BorderLayout.SOUTH);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(2, 2, 2, 2), BorderFactory
				.createLineBorder(Color.lightGray)));
		view.add(scrollPane, BorderLayout.CENTER);
	}
}
