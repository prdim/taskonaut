/**
 * 
 */
package org.taskonaut.internal;

import java.util.ArrayList;
import java.util.List;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import org.taskonaut.api.IChangeDataListener;
import org.taskonaut.api.IMenuAction;

/**
 * @author spec
 *
 */
public class MenuTracker extends ServiceTracker implements ServiceTrackerCustomizer {
	private List<IChangeDataListener> changeListeners = new ArrayList<IChangeDataListener>();
	private List<IMenuAction> items = new ArrayList<IMenuAction>();
	
	public MenuTracker(BundleContext context) {
		super(context,IMenuAction.class.getName(),null);
		open();
	}

	@Override
	public Object addingService(ServiceReference reference) {
		IMenuAction item = (IMenuAction) context.getService(reference);
		items.add(item);
		notifyChanges();
		return item;
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		items.remove((IMenuAction)service);
		notifyChanges();
		super.removedService(reference, service);
	}

	private void notifyChanges() {
		for(IChangeDataListener i : changeListeners) {
			i.onChange(null);
		}
	}
	
	public List<IMenuAction> getMenu() {
		return items;
	}

	public void addChangeListener(IChangeDataListener ls) {
		changeListeners.add(ls);
	}
	
	public void removeChangeListener(IChangeDataListener ls) {
		changeListeners.remove(ls);
	}
	
	public void clearAllListeners() {
		changeListeners.clear();
	}
}
