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

package org.taskonaut.tray.internal;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
import org.taskonaut.tray.Tray;

/**
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class Activator implements BundleActivator {
	private static EventAdmin eventAdmin;
	private static Map<EventHandler,ServiceRegistration> eventHandlers = 
		new HashMap<EventHandler,ServiceRegistration>();
	private ServiceTracker serviceTracker;
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	
	public static EventAdmin getEventAdmin() {
		return eventAdmin;
	}
	
	public static synchronized void regEventHandler(EventHandler e, Dictionary<String, Object>p) {
		ServiceRegistration r = context.registerService(EventHandler.class.getName(), e, p);
		eventHandlers.put(e, r);
	}
	
	public static synchronized void unregEventHandler(EventHandler e) {
		ServiceRegistration r = eventHandlers.get(e);
		r.unregister();
		eventHandlers.remove(e);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
//		Tray.getInstance().sTrayShow();
		serviceTracker = new ServiceTracker(context, EventAdmin.class.getName(), null);
		serviceTracker.open();
		eventAdmin = (EventAdmin) serviceTracker.getService();
		System.out.println("Bundle Tray started");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					// Ждем подключения сервиса хранения данных
					while (TaskStoreServiceConnector.getStore() == null) {
						Thread.sleep(500);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				Tray.getInstance().sTrayShow();
				System.out.println("Show tray");
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		serviceTracker.close();
		for(EventHandler i : eventHandlers.keySet()) {
			unregEventHandler(i);
		}
	}

}
