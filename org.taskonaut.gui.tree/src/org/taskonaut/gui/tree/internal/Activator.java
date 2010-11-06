package org.taskonaut.gui.tree.internal;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.gui.tree.TaskListTreeAction;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private MenuConnector con;
	private static EventAdmin eventAdmin;
	private ServiceTracker serviceTracker;
	private static Map<EventHandler,ServiceRegistration> eventHandlers = 
		new HashMap<EventHandler,ServiceRegistration>();

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		List<IMenuAction> m = new ArrayList<IMenuAction>();
		m.add(new TaskListTreeAction());
		con = new MenuConnector(bundleContext, m);
		serviceTracker = new ServiceTracker(context, EventAdmin.class.getName(), null);
		serviceTracker.open();
		eventAdmin = (EventAdmin) serviceTracker.getService();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		con.close();
		serviceTracker.close();
		for(EventHandler i : eventHandlers.keySet()) {
			unregEventHandler(i);
		}
	}
	
	public static EventAdmin getEventAdmin() {
		return eventAdmin;
	}
	
	public static synchronized void regEventHandler(EventHandler e, Dictionary<String, Object>p) {
		ServiceRegistration r = context.registerService(EventHandler.class.getName(), e, p);
		eventHandlers.put(e, r);
		System.out.println("reg:" + e.toString());
	}
	
	public static synchronized void unregEventHandler(EventHandler e) {
		System.out.println("unreg:" + e.toString());
		ServiceRegistration r = eventHandlers.get(e);
		r.unregister();
		eventHandlers.remove(e);
	}

}
