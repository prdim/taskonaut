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

package org.taskonaut.customizer.internal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.taskonaut.api.AbstractProperties;

/**
 * Собирает информацию о зарегистрированных сервисах настроек
 *  
 * @author Prolubnikov Dmitry
 *
 */
public class PropsConnector extends ServiceTracker implements ServiceTrackerCustomizer {
	private List<AbstractProperties> props = new ArrayList<AbstractProperties>();
	
	public PropsConnector(BundleContext context) {
		super(context, AbstractProperties.class.getName(), null);
		open();
	}
	
	public List<AbstractProperties> getProps() {
		return props;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#close()
	 */
	@Override
	public void close() {
		props.clear();
		super.close();
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
	 */
	@Override
	public Object addingService(ServiceReference reference) {
		Object service = super.addingService(reference);
		props.add((AbstractProperties)service);
		return service;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void removedService(ServiceReference reference, Object service) {
		props.remove(service);
		super.removedService(reference, service);
	}
	
	
}
