/**
 * 
 */
package org.taskonaut.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.taskonaut.api.IChangeDataListener;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.api.IMenuService;

/**
 * Сервис, предоставляющий возможность добавлять новые пункты меню при помощи дркгих бандлов
 * 
 * @author ProlubnikovDA
 *
 */
public class MenuService implements IMenuService {
	private List<IChangeDataListener> changeListeners = new ArrayList<IChangeDataListener>();
//	private Map<String, List<IMenuAction>> items = new ConcurrentHashMap<String, List<IMenuAction>>();
	private List<IMenuAction> items = new ArrayList<IMenuAction>();
	
	/* (non-Javadoc)
	 * @see org.taskonaut.api.IMenuService#bindMenuItem(org.taskonaut.api.IMenuAction)
	 */
	@Override
	public synchronized void bindMenuItem(IMenuAction item) {
		System.out.println("bind " + item.getActionName());
		items.add(item);
//		if(items.containsKey(item.getMenu())) {
//			List<IMenuAction> k = items.get(item.getMenu());
//			boolean f = false;
//			for(int i=0; i<k.size(); i++) {
//				if(k.get(i).getPriority()>item.getPriority()) {
//					k.add(i==0 ? 0 : (i-1), item);
//					f = true;
//					break;
//				}
//			}
//			if(!f) k.add(item);
//			items.put(item.getMenu(), k);
//		} else {
//			ArrayList<IMenuAction> k = new ArrayList<IMenuAction>();
//			k.add(item);
//			items.put(item.getMenu(), k);
//		}
		notifyChanges();
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.IMenuService#getAllItems()
	 */
	@Override
	public List<IMenuAction> getAllItems() {
		return items;
	}

	/* (non-Javadoc)
	 * @see org.taskonaut.api.IMenuService#ubindMenuItem(org.taskonaut.api.IMenuAction)
	 */
	@Override
	public synchronized void ubindMenuItem(IMenuAction item) {
		System.out.println("ubind " + item.getActionName());
		items.remove(item);
//		items.get(item.getMenu()).remove(item);
//		if(items.get(item.getMenu()).size()==0) items.remove(item.getMenu());
		notifyChanges();
	}

	public void addChangeListener(IChangeDataListener ls) {
		changeListeners.add(ls);
		System.out.println("add listener");
	}
	
	public void removeChangeListener(IChangeDataListener ls) {
		changeListeners.remove(ls);
	}
	
	public void clearAllListeners() {
		changeListeners.clear();
	}	
	
	private void notifyChanges() {
		for(IChangeDataListener i : changeListeners) {
			i.onChange(null);
		}
	}
}
