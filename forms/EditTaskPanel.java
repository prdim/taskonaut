import java.util.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;
import org.jdesktop.swingx.*;
/*
 * Created by JFormDesigner on Sat Jul 10 23:37:05 YEKST 2010
 */



/**
 * @author spec
 */
public class EditTaskPanel extends JPanel {
	public EditTaskPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		ResourceBundle bundle = ResourceBundle.getBundle("EditTaskPanel");
		label2 = new JLabel();
		nameField = new JTextField();
		label1 = new JLabel();
		startDate = new JXDatePicker();
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
		label2.setText(bundle.getString("label2.text"));
		add(label2, cc.xy(1, 1));
		add(nameField, cc.xywh(3, 1, 9, 1));

		//---- label1 ----
		label1.setText(bundle.getString("label1.text"));
		add(label1, cc.xy(1, 3));
		add(startDate, cc.xywh(5, 3, 3, 1));
		add(endDate, cc.xywh(9, 3, 3, 1));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(commentText);
		}
		add(scrollPane1, cc.xywh(1, 5, 11, 1));

		//---- label3 ----
		label3.setText(bundle.getString("label3.text"));
		add(label3, cc.xy(1, 7));
		add(statusBox, cc.xy(3, 7));

		//---- label4 ----
		label4.setText(bundle.getString("label4.text"));
		add(label4, cc.xy(5, 7));
		add(typeBox, cc.xy(7, 7));

		//---- label5 ----
		label5.setText(bundle.getString("label5.text"));
		add(label5, cc.xy(9, 7));
		add(relationTask, cc.xy(11, 7));

		//---- label6 ----
		label6.setText(bundle.getString("label6.text"));
		add(label6, cc.xy(1, 9));
		add(priorityBox, cc.xy(3, 9));

		//---- label7 ----
		label7.setText(bundle.getString("label7.text"));
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
