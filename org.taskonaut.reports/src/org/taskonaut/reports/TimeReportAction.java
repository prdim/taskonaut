/**
 * 
 */
package org.taskonaut.reports;

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
public class TimeReportAction implements IMenuAction {
	
	@Action
	public Task<Void, Void> timeReportAction() {
		Task<Void,Void> t = new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				TimeReportPanel p = new TimeReportPanel();
				DefaultInternalFrame f = new DefaultInternalFrame(p);
				f.setTitle("Затраты времени");
				((MainApplication)MainApplication.getInstance()).addInternalFrame(f);
				return null;
			}
			
		};
		return t;
	}

	@Override
	public String getActionName() {
		return "timeReportAction";
	}

	@Override
	public String getMenuPath() {
		return "Отчеты";
	}

	@Override
	public int getPriority() {
		return 10;
	}

}
