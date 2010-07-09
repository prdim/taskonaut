package org.taskonaut.test.actions.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.test.actions.TestActions;

public class Activator implements BundleActivator {
//	private ServiceRegistration reg;
	private MenuConnector con;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Start actions service");
		con = new MenuConnector(context, new TestActions());
//		reg = context.registerService(IMenuAction.class.getName(), new TestActions(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stop actions service");
		con.close();
//		reg.unregister();
	}

}
