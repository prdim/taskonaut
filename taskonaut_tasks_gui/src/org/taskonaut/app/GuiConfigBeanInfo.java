/**
 * 
 */
package org.taskonaut.app;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author spec
 *
 */
public class GuiConfigBeanInfo extends SimpleBeanInfo {
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor p1 = new PropertyDescriptor("hideMinimized", GuiConfig.class);
			p1.setDisplayName("Скрывать свернутое");
			
			return new PropertyDescriptor[] {p1};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
