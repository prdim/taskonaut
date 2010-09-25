package org.taskonaut.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;
//import org.taskonaut.app.MainApplication;

public class Activator implements BundleActivator {
	private static BundleContext context;
//	private static MenuService menu;
//	private ServiceRegistration registration;
	private TaskStoreServiceConnector con;

	static BundleContext getContext() {
		return context;
	}
	
//	public static synchronized MenuService getMenuService() {
//		return menu;
//	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		con = new TaskStoreServiceConnector(context);
//		menu = new MenuService();
//		registration = context.registerService(IMenuService.class.getName(), menu, null);
//		Application.launch(MainApplication.class, new String[] {});
		System.out.println("start core bundle");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		Activator.context = null;
//		menu.clearAllListeners();
//		registration.unregister();
		con.close();
		System.out.println("stop core bundle");
	}

}
