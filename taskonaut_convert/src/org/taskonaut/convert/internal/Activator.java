package org.taskonaut.convert.internal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.convert.actions.ExportData;
import org.taskonaut.convert.actions.ImportData;

public class Activator implements BundleActivator {
	private List<MenuConnector> con = new ArrayList<MenuConnector>();
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
		con.add(new MenuConnector(context, new ImportData()));
		con.add(new MenuConnector(context, new ExportData()));
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stop import actions");
		for(MenuConnector i : con) {
			i.close();
		}
		Activator.context = null;
	}

}
