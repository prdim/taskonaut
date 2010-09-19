import javax.swing.*;
import com.jgoodies.forms.layout.*;
import org.jdesktop.swingx.*;
/*
 * Created by JFormDesigner on Sun Sep 19 14:21:23 YEKST 2010
 */



/**
 * @author spec
 */
public class FilterPanel extends JPanel {
	public FilterPanel() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		scrollPane1 = new JScrollPane();
		xList1 = new JXList();
		scrollPane2 = new JScrollPane();
		xList2 = new JXList();
		scrollPane3 = new JScrollPane();
		xList3 = new JXList();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			"2*(default:grow, $lcgap), default:grow",
			"default, $lgap, fill:default:grow"));

		//---- label1 ----
		label1.setText("text");
		add(label1, cc.xy(1, 1));

		//---- label2 ----
		label2.setText("text");
		add(label2, cc.xy(3, 1));

		//---- label3 ----
		label3.setText("text");
		add(label3, cc.xy(5, 1));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(xList1);
		}
		add(scrollPane1, cc.xy(1, 3));

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(xList2);
		}
		add(scrollPane2, cc.xy(3, 3));

		//======== scrollPane3 ========
		{
			scrollPane3.setViewportView(xList3);
		}
		add(scrollPane3, cc.xy(5, 3));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JScrollPane scrollPane1;
	private JXList xList1;
	private JScrollPane scrollPane2;
	private JXList xList2;
	private JScrollPane scrollPane3;
	private JXList xList3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
