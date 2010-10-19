package org.taskonaut.tasks.gui.internal;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jdesktop.application.Application;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.taskonaut.api.AbstractProperties;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.IMenuService;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.app.GuiConfig;
import org.taskonaut.app.MenuService;
import org.taskonaut.app.MainApplication;
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
	private static MenuService menu;
	private ServiceRegistration registration;
	private ServiceRegistration registration2;

	static BundleContext getContext() {
		return context;
	}
	
	public static synchronized MenuService getMenuService() {
		return menu;
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
		
		menu = new MenuService();
		registration = context.registerService(IMenuService.class.getName(), menu, null);
		registration2 = context.registerService(AbstractProperties.class.getName(), GuiConfig.getInstance(), null);
		Application.launch(MainApplication.class, new String[] {});
		
		System.out.println("GUI service started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		menu.clearAllListeners();
		registration.unregister();
		registration2.unregister();
		con.close();
//		reg.unregister();
		
		serviceTracker.close();
		for(EventHandler i : eventHandlers.keySet()) {
			unregEventHandler(i);
		}
	}

}
