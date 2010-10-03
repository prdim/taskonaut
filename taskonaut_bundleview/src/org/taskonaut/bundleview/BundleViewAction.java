/**
 * 
 */
package org.taskonaut.bundleview;

import java.awt.BorderLayout;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.MainApplication;
import org.taskonaut.tasks.gui.DefaultInternalFrame;
import org.taskonaut.tasks.gui.JPanelExt;

/**
 * @author ProlubnikovDA
 *
 */
public class BundleViewAction implements IMenuAction {

	@Action
	public Task<Void, Void>bundleViewAction() {
		return new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				DefaultInternalFrame f = new DefaultInternalFrame(new BundleViewPanel());
				f.setTitle("Список плагинов");
				((MainApplication)MainApplication.getInstance()).addInternalFrame(f);
				return null;
			}
			
		};
	}
	
	private class BundleViewPanel extends JPanelExt {

		public BundleViewPanel() {
			add(new BundleView().getView(), BorderLayout.CENTER);
		}

		@Override
		public boolean checkOk() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void beforeClose() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Override
	public String getActionName() {
		return "bundleViewAction";
	}

	@Override
	public String getMenu() {
		return "fileMenu";
	}

	@Override
	public int getPriority() {
		return 10;
	}

}
