/**
 * 
 */
package org.taskonaut.api;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Service tracker, позволяющий добавлять новые пункты в сервис меню
 * 
 * @author ProlubnikovDA
 *
 */
public class MenuConnector extends ServiceTracker implements ServiceTrackerCustomizer{
	private List<IMenuAction> in = new ArrayList<IMenuAction>();
	private List<IMenuAction> to = new ArrayList<IMenuAction>();
	
	public MenuConnector(BundleContext context) {
		super(context,IMenuService.class.getName(),null);
		open();
		connectMenu((IMenuService)getService());
	}
	
	public MenuConnector(BundleContext context, List<IMenuAction> lm) {
		super(context,IMenuService.class.getName(),null);
		if(lm!=null) in.addAll(lm);
		open();
		connectMenu((IMenuService)getService());
	}
	
	public MenuConnector(BundleContext context, IMenuAction m) {
		super(context,IMenuService.class.getName(),null);
		if(m!=null) in.add(m);
		open();
		connectMenu((IMenuService)getService());
	}
	
	@Override
	public Object addingService(ServiceReference reference) {
		Object service = super.addingService(reference);
		connectMenu((IMenuService)service);
		return service;
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		in.addAll(to);
		to.clear();
		super.removedService(reference, service);
	}

	protected void connectMenu(IMenuService m) {
		if(m!=null) {
			for(IMenuAction i : in) {
				m.bindMenuItem(i);
				to.add(i);
			}
			in.clear();
		}
	}

	@Override
	public void close() {
		for(IMenuAction i : to) {
			((IMenuService)getService()).ubindMenuItem(i);
		}
		to.clear();
		super.close();
	}
}
