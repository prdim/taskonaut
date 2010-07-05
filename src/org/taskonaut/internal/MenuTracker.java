/**
 * 
 */
package org.taskonaut.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	private Map<String, List<IMenuAction>> items = new ConcurrentHashMap<String, List<IMenuAction>>();
	
	public MenuTracker(BundleContext context) {
		super(context,IMenuAction.class.getName(),null);
		open();
	}

	@Override
	public Object addingService(ServiceReference reference) {
		IMenuAction item = (IMenuAction) context.getService(reference);
		if(items.containsKey(item.getMenu())) {
			List<IMenuAction> k = items.get(item.getMenu());
			boolean f = false;
			for(int i=0; i<k.size(); i++) {
				if(k.get(i).getPriority()>item.getPriority()) {
					k.add(i==0 ? 0 : (i-1), item);
					f = true;
					break;
				}
			}
			if(!f) k.add(item);
			items.put(item.getMenu(), k);
		} else {
			ArrayList<IMenuAction> k = new ArrayList<IMenuAction>();
			k.add(item);
			items.put(item.getMenu(), k);
		}
			
//		items.add(item);
		notifyChanges();
		return item;
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
//		items.remove((IMenuAction)service);
		IMenuAction i = (IMenuAction)service;
		items.get(i.getMenu()).remove(i);
		if(items.get(i.getMenu()).size()==0) items.remove(i.getMenu());
		notifyChanges();
		super.removedService(reference, service);
	}

	private void notifyChanges() {
		for(IChangeDataListener i : changeListeners) {
			i.onChange(null);
		}
	}
	
	public Map<String, List<IMenuAction>> getMenu() {
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
