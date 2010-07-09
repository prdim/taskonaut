package org.taskonaut.tasks.gui.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.tasks.gui.NewTaskAction;

public class Activator implements BundleActivator {
//	private ServiceRegistration reg;
	private static BundleContext context;
	private MenuConnector con;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		con = new MenuConnector(bundleContext, new NewTaskAction());
//		reg = context.registerService(IMenuAction.class.getName(), new NewTaskAction(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		con.close();
//		reg.unregister();
	}

}