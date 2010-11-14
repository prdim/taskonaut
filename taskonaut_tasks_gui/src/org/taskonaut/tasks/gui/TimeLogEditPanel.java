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

package org.taskonaut.tasks.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import org.taskonaut.api.tasks.TimeLogItem;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class TimeLogEditPanel extends JPanelExt {
	TimeLogItem item;
	boolean ok = false;
	
	public TimeLogEditPanel(TimeLogItem t) {
		item = t;
		initComponents();
		fillData();
	}
	
	/**
	 * Заполняет форму данными
	 */
	private void fillData() {
		textField1.setValue(new Date(item.getID()));
		textField2.setValue(new Date(item.getPeriod()));
		textField3.setText(item.getComment());
	}
	
	/**
	 * Читает данные из полей формы
	 */
	private void readData() {
		item.setComment(textField3.getText());
		// TODO проверки. 
		// 1.время окончания не позже текущего времени, 
		// 2. время начала не пересекается с предыдущим окончанием, 
		// 3. время окончание не позже следующего начала.
		long t1 = ((Date)textField1.getValue()).getTime();
		long t2 = ((Date)textField2.getValue()).getTime();
		// Пока для простоты - длительность можно только уменьшить, начало изменить нельзя
		if(t2<item.getPeriod()) {
			item.setEnd(item.getID() + t2);
		}
//		System.out.println(item.getID() + " : " + textField1.getValue());
//		System.out.println(item.getPeriod() + " : " + textField2.getValue());
	}
	
	public boolean isOk() {
		return ok;
	}
	
	public TimeLogItem getData() {
		return item;
	}

	@Override
	public boolean checkOk() {
		readData();
		ok = true;
		return true;
	}

	@Override
	public void beforeClose() {
		// TODO Auto-generated method stub
		
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		textField1 = new JFormattedTextField();
		label2 = new JLabel();
		textField2 = new JFormattedTextField();
		label3 = new JLabel();
		textField3 = new JTextField();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			"default, $lcgap, 100dlu:grow",
			"2*(default, $lgap), default"));

		//---- label1 ----
		label1.setText("Начало");
		add(label1, cc.xy(1, 1));
		add(textField1, cc.xy(3, 1));

		//---- label2 ----
		label2.setText("Длительность");
		add(label2, cc.xy(1, 3));
		add(textField2, cc.xy(3, 3));

		//---- label3 ----
		label3.setText("Комментарий");
		add(label3, cc.xy(1, 5));
		add(textField3, cc.xy(3, 5));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		SimpleDateFormat df;
		df = new SimpleDateFormat("dd.MM.yyyy H:mm:ss");
		textField1.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(df)));
		textField1.setToolTipText("Время начала выполнения задачи (в текущей версии не корректируется)");
		df = new SimpleDateFormat("H:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		textField2.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(df)));
		textField2.setToolTipText("Продолжительность выполнения (в текущей версии можно только уменьшить)");
		textField3.setToolTipText("Комментарий о выполняемых действиях");
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JFormattedTextField textField1;
	private JLabel label2;
	private JFormattedTextField textField2;
	private JLabel label3;
	private JTextField textField3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
