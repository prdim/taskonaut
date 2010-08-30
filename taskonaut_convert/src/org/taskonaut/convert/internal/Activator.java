package org.taskonaut.convert.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.convert.actions.ImportFromXml;

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
		System.out.println("Start import actions");
		Activator.context = bundleContext;
		con = new MenuConnector(context, new ImportFromXml());
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stop import actions");
		con.close();
		Activator.context = null;
	}

}
