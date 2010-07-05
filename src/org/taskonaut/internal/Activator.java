package org.taskonaut.internal;

import org.jdesktop.application.Application;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.taskonaut.app.MainApplication;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static MenuTracker menu;

	static BundleContext getContext() {
		return context;
	}
	
	public static synchronized MenuTracker getMenuTracker() {
		return menu;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		menu = new MenuTracker(context);
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
		System.out.println("stop core bundle");
	}

}
