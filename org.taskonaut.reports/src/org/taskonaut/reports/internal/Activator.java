package org.taskonaut.reports.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.reports.TimeReportAction;

public class Activator implements BundleActivator {
	private MenuConnector con;
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Start report bundle");
		con = new MenuConnector(bundleContext, new TimeReportAction());
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		con.close();
		System.out.println("Stop report bundle");
	}

}
