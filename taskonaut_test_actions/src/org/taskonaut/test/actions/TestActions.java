/**
 * 
 */
package org.taskonaut.test.actions;

import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.Action;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.tasks.TaskItem;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.test.actions.internal.Activator;

/**
 * @author spec
 *
 */
public class TestActions implements IMenuAction {
	
	@Action
	public void testAction1() throws FileNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		List<TaskItem> i = TaskStoreServiceConnector.getStore().readAllTasks();
//		for(TaskItem t : i) {
//			System.out.println(t.getName());
//		}
//		System.out.println(TaskStoreServiceConnector.getStore().readAllTimeLogItems().size());

//		GuiConfig.getInstance().setHideMinimized(true);
//		GuiConfig.getInstance().save();
		
		// Тест формата iCal
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Taskonaut//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		UidGenerator ug = null;
		try {
			ug = new UidGenerator("1");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(TaskItem i : TaskStoreServiceConnector.getStore().readAllTasks()) {
			if(i.getExecute()> System.currentTimeMillis()) { // Имеет смысл выгружать только будущие события
			VEvent ev = new VEvent(new Date(i.getExecute()), i.getName());
			// initialise as an all-day event..
//			ev.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);
//			try {
//				ug = new UidGenerator(String.valueOf(i.getID()));
//			} catch (SocketException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			ev.getProperties().add(ug.generateUid());
			
			calendar.getComponents().add(ev);
			}
		}
//		System.out.println(calendar);
		FileOutputStream fout = new FileOutputStream("./mycalendar.ics");
		CalendarOutputter outputter = new CalendarOutputter();
		try {
			outputter.output(calendar, fout);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getActionName() {
		return "testAction1";
	}

	@Override
	public String getMenuPath() {
		return "Задачи|Тест|Вложенное меню|Еще немного...|Глубже";
	}

	@Override
	public int getPriority() {
		return 100;
	}
	
	
}
