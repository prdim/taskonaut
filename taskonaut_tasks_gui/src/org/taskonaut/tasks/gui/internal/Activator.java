package org.taskonaut.tasks.gui.internal;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.tasks.gui.NewTaskAction;
import org.taskonaut.tasks.gui.TaskListAction;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
//	private ServiceRegistration reg;
	private static BundleContext context;
	private MenuConnector con;
	private static EventAdmin eventAdmin;
	private ServiceTracker serviceTracker;
	private static Map<EventHandler,ServiceRegistration> eventHandlers = 
					new HashMap<EventHandler,ServiceRegistration>();

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
		List<IMenuAction> m = new ArrayList<IMenuAction>();
		m.add(new NewTaskAction());
		m.add(new TaskListAction());
		con = new MenuConnector(bundleContext, m);
//		reg = context.registerService(IMenuAction.class.getName(), new NewTaskAction(), null);
		
		serviceTracker = new ServiceTracker(context, EventAdmin.class.getName(), null);
		serviceTracker.open();
		eventAdmin = (EventAdmin) serviceTracker.getService();
		System.out.println("GUI service started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		con.close();
//		reg.unregister();
		
		serviceTracker.close();
		for(EventHandler i : eventHandlers.keySet()) {
			unregEventHandler(i);
		}
	}

}
