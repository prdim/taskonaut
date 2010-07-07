/**
 * 
 */
package org.taskonaut.test.actions;

import org.jdesktop.application.Action;
import org.taskonaut.api.IMenuAction;

/**
 * @author spec
 *
 */
public class TestActions implements IMenuAction {
	
	@Action
	public void testAction1() {
		System.out.println("Test action!");
	}

	@Override
	public String getActionName() {
		return "testAction1";
	}

	@Override
	public String getMenu() {
		return "taskMenu";
	}

	@Override
	public int getPriority() {
		return 110;
	}
	
	
}
