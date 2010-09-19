import javax.swing.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sat Sep 18 15:05:27 YEKST 2010
 */



/**
 * @author spec
 */
public class TimeLogEditPanel extends JPanel {
	public TimeLogEditPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		formattedTextField1 = new JFormattedTextField();
		label2 = new JLabel();
		textField2 = new JTextField();
		label3 = new JLabel();
		textField3 = new JTextField();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			"default, $lcgap, 100dlu:grow",
			"2*(default, $lgap), default"));

		//---- label1 ----
		label1.setText("\u041d\u0430\u0447\u0430\u043b\u043e");
		add(label1, cc.xy(1, 1));
		add(formattedTextField1, cc.xy(3, 1));

		//---- label2 ----
		label2.setText("\u0414\u043b\u0438\u0442\u0435\u043b\u044c\u043d\u043e\u0441\u0442\u044c");
		add(label2, cc.xy(1, 3));
		add(textField2, cc.xy(3, 3));

		//---- label3 ----
		label3.setText("\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439");
		add(label3, cc.xy(1, 5));
		add(textField3, cc.xy(3, 5));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JFormattedTextField formattedTextField1;
	private JLabel label2;
	private JTextField textField2;
	private JLabel label3;
	private JTextField textField3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
