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

package org.taskonaut.app;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;

/**
 * @author Prolubnikov Dmitry
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
			
			return pr.toArray(new PropertyDescriptor[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
