/**
 * 
 */
package org.taskonaut.customizer;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.MainApplication;
import org.taskonaut.customizer.internal.Activator;
import org.taskonaut.tasks.gui.DefaultInternalFrame;
import org.taskonaut.tasks.gui.InternalFrameDialog;

/**
 * @author spec
 *
 */
public class PropertyEditAction implements IMenuAction {
	
	@Action
	public Task<Void,Void> propertyEditAction() {
		return new Task<Void,Void>(MainApplication.getInstance()) {

			@Override
			protected Void doInBackground() throws Exception {
				PropertyEditPanel p = new PropertyEditPanel(Activator.getProps());
				InternalFrameDialog d = new InternalFrameDialog(p);
				d.setTitle("Настройки программы");
				((MainApplication)MainApplication.getInstance()).addInternalFrame(d);
				return null;
			}
			
		};
	}

	@Override
	public String getActionName() {
		return "propertyEditAction";
	}

	@Override
	public String getMenuPath() {
		return "Файл";
	}

	@Override
	public int getPriority() {
		return 40;
	}

}
