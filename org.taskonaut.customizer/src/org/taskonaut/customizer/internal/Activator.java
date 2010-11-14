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

package org.taskonaut.customizer.internal;

import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.taskonaut.api.AbstractProperties;
import org.taskonaut.api.MenuConnector;
import org.taskonaut.customizer.PropertyEditAction;

/**
 * 
 * @author Prolubnikov Dmitry
 *
 */
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
