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

package org.taskonaut.api.tasks;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Prolubnikov Dmitry
 *
 */
public class TaskStoreServiceConnector extends ServiceTracker implements ServiceTrackerCustomizer{
	private static ITaskStoreService srv = null;
	
	public TaskStoreServiceConnector(BundleContext context) {
		super(context, ITaskStoreService.class.getName(),null);
		open();
		srv = (ITaskStoreService)getService();
	}
	
	public static ITaskStoreService getStore() {
		return srv;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#close()
	 */
	@Override
	public void close() {
		srv = null;
		super.close();
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
	 */
	@Override
	public Object addingService(ServiceReference reference) {
		Object service = super.addingService(reference);
		srv = (ITaskStoreService)service;
		return service;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	@Override
	public void removedService(ServiceReference reference, Object service) {
		srv = null;
		super.removedService(reference, service);
	}
	
	
}
