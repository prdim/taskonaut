package org.taskonaut.test.actions.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.taskonaut.api.AbstractProperties;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.test.actions.TestActions;

public class Activator implements BundleActivator {
//	private ServiceRegistration reg;
	private MenuConnector con;
	private static TestTracker test;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Start actions service");
		con = new MenuConnector(context, new TestActions());
		test = new TestTracker(context);
//		reg = context.registerService(IMenuAction.class.getName(), new TestActions(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stop actions service");
		con.close();
		test.close();
//		reg.unregister();
	}
	
	public static AbstractProperties getProps() {
		return test.getProps();
	}
	
	private class TestTracker extends ServiceTracker implements ServiceTrackerCustomizer{
		private AbstractProperties pr = null;
		
		public TestTracker (BundleContext context) {
			super(context, AbstractProperties.class.getName(), null);
			open();
		}
		
		public AbstractProperties getProps() {
			return pr;
		}

		/* (non-Javadoc)
		 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
		 */
		@Override
		public Object addingService(ServiceReference reference) {
			Object service = super.addingService(reference);
			pr = (AbstractProperties) service;
			return service;
		}

		/* (non-Javadoc)
		 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
		 */
		@Override
		public void removedService(ServiceReference reference, Object service) {
			pr = null;
			super.removedService(reference, service);
		}
		
		
	}

}
