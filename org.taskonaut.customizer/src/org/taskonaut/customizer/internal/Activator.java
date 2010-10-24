package org.taskonaut.customizer.internal;

import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.taskonaut.api.AbstractProperties;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.customizer.PropertyEditAction;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static PropsConnector prc;
	private MenuConnector con;

	static BundleContext getContext() {
		return context;
	}
	
	public static List<AbstractProperties> getProps() {
		return prc.getProps();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Start customizer service");
		Activator.context = bundleContext;
		prc = new PropsConnector(context);
		con = new MenuConnector(context, new PropertyEditAction());
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		prc.close();
		con.close();
		System.out.println("Stop customizer service");
	}

}
