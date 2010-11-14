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

package org.taskonaut.reports;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.taskonaut.api.IMenuAction;
import org.taskonaut.app.MainApplication;
import org.taskonaut.tasks.gui.DefaultInternalFrame;
import org.taskonaut.tasks.gui.JPanelExt;

/**
 * @author Prolubnikov Dmitry
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
