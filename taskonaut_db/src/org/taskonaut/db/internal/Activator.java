package org.taskonaut.db.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.taskonaut.api.tasks.ITaskStoreService;
import org.taskonaut.db.TaskStoreService;

public class Activator implements BundleActivator {
	private TaskStoreService service;
	private static BundleContext context;
	private ServiceRegistration registration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		service = new TaskStoreService();
		registration = bundleContext.registerService(ITaskStoreService.class.getName(), service, null);
		System.out.println("Service DB started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		registration.unregister();
		System.out.println("Service DB stop");
	}

}
