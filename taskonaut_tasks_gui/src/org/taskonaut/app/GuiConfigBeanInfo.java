/**
 * 
 */
package org.taskonaut.app;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

/**
 * @author spec
 *
 */
public class GuiConfigBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			ArrayList<PropertyDescriptor> pr = new ArrayList<PropertyDescriptor>();
			PropertyDescriptor p;
			p = new PropertyDescriptor("hideMinimized", GuiConfig.class);
			p.setDisplayName("Скрывать свернутое");
			pr.add(p);
			p = new PropertyDescriptor("enableFilterOnFly", GuiConfig.class);
			p.setDisplayName("Автоматически применять фильтр задач");
			pr.add(p);
			p = new PropertyDescriptor("trackMouseOnTaskList", GuiConfig.class);
			p.setDisplayName("Отслеживать перемещение мыши в списке задач");
			pr.add(p);
			
			return pr.toArray(new PropertyDescriptor[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
