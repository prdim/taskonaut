package org.taskonaut.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.taskonaut.api.AbstractProperties;
import org.taskonaut.api.GlobalConfig;
import org.taskonaut.api.tasks.TaskStoreServiceConnector;


public class Activator implements BundleActivator {
	private static BundleContext context;
	private TaskStoreServiceConnector con;
	private ServiceRegistration reg;

	static BundleContext getContext() {
		return context;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Activator.context = context;
		con = new TaskStoreServiceConnector(context);
		reg = context.registerService(AbstractProperties.class.getName(), GlobalConfig.getInstance(), null);
		System.out.println("start core bundle");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		Activator.context = null;
		con.close();
		reg.unregister();
		System.out.println("stop core bundle");
	}

}
