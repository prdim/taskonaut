package org.taskonaut.internal;

import org.jdesktop.application.Application;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.taskonaut.api.IMenuService;
import org.taskonaut.app.MainApplication;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static MenuService menu;
	private ServiceRegistration registration;

	static BundleContext getContext() {
		return context;
	}
	
	public static synchronized MenuService getMenuService() {
		return menu;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		menu = new MenuService();
		registration = context.registerService(IMenuService.class.getName(), menu, null);
		Application.launch(MainApplication.class, new String[] {});
		System.out.println("start core bundle");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		Activator.context = null;
		menu.clearAllListeners();
		registration.unregister();
		System.out.println("stop core bundle");
	}

}
