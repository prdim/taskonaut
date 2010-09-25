import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;
import org.jdesktop.swingx.*;
/*
 * Created by JFormDesigner on Sat Sep 25 16:11:17 YEKST 2010
 */



/**
 * @author spec
 */
public class EditTaskPanel2 extends JPanel {
	public EditTaskPanel2() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		nameField = new JTextField();
		panel1 = new JPanel();
		label3 = new JLabel();
		startDate = new JXDatePicker();
		label2 = new JLabel();
		endDate = new JXDatePicker();
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
			"3*(default, $lgap), 35dlu:grow, 2*($lgap, default), $lgap, fill:default:grow"));

		//---- label1 ----
		label1.setText("text");
		add(label1, cc.xy(1, 1));
		add(nameField, cc.xy(3, 1));

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//---- label3 ----
			label3.setText("text");
			panel1.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel1.add(startDate, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- label2 ----
			label2.setText("text");
			panel1.add(label2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel1.add(endDate, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		add(panel1, cc.xywh(1, 3, 3, 1, CellConstraints.RIGHT, CellConstraints.DEFAULT));

		//---- label4 ----
		label4.setText("text");
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
			label5.setText("text");
			panel2.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel2.add(statusBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- label6 ----
			label6.setText("text");
			panel2.add(label6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel2.add(typeBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));

			//---- label7 ----
			label7.setText("text");
			panel2.add(label7, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
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
			label8.setText("text");
			panel3.add(label8, cc.xy(1, 1));
			panel3.add(priorityBox, cc.xy(3, 1));

			//---- label9 ----
			label9.setText("text");
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
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JTextField nameField;
	private JPanel panel1;
	private JLabel label3;
	private JXDatePicker startDate;
	private JLabel label2;
	private JXDatePicker endDate;
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
