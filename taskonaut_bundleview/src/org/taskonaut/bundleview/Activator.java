/*
 * Copyright 2010 Prolubnikov Dmitry
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.taskonaut.bundleview;

import java.util.ArrayList;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener; 
import org.taskonaut.api.MenuConnector;

/**
 * 
 * @author Prolubnikov Dmitry
 *
 */
public class Activator implements BundleActivator, SynchronousBundleListener {
	private static Bundle[] bundles;
	private static BundleContext context;
	private MenuConnector con;

	static BundleContext getContext() {
		return context;
	}
	
	public static synchronized Bundle[] getBundles() {
        return bundles;
    }

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		Bundle[] allBundles = context.getBundles();
        ArrayList<Bundle> bundleList = new ArrayList<Bundle>();

        // Hack for adding only our relevant bundles to the list
        for (Bundle bundle : allBundles) {
            String symbolicName = bundle.getSymbolicName();
            if (symbolicName.startsWith("org.taskonaut")) {
                bundleList.add(bundle);
            }
        }
        bundles = bundleList.toArray(new Bundle[] {});
        
        con = new MenuConnector(bundleContext, new BundleViewAction());
        System.out.println("Start service bundle view");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		con.close();
		System.out.println("Stop service bundle view");
		Activator.context = null;
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		if (BundleEvent.STARTED == event.getType()) {

        } else if (BundleEvent.STOPPED == event.getType()) {

        }
	}

}
