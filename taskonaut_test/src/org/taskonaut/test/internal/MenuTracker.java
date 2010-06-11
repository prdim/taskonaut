/**
 * 
 */
package org.taskonaut.test.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.taskonaut.api.IMenuAction;

/**
 * @author ProlubnikovDA
 *
 */
public class MenuTracker extends ServiceTracker implements ServiceTrackerCustomizer {
	public static IMenuAction menu = null;
	
	public MenuTracker(BundleContext context) {
		super(context, IMenuAction.class.getName(), null);
		open();
	}

	@Override
	public Object addingService(ServiceReference reference) {
		menu = (IMenuAction)context.getService(reference);
		System.out.println("Register " + menu.getActionName());
		((AppTest)AppTest.getInstance()).updateActions();
		return menu;
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		menu = null;
		super.removedService(reference, service);
		((AppTest)AppTest.getInstance()).updateActions();
	}
}
