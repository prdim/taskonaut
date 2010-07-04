/**
 * 
 */
package org.taskonaut.api;

import javax.swing.Action;


/**
 * @author spec
 *
 */
public interface IMenuAction {
	
	/**
	 * Имя действия
	 * @return
	 */
	public String getActionName();
	
	/**
	 * В каком меню расположить пункт?
	 * @return
	 */
	public String getMenu();
	
	/**
	 * Приоритет данного пункта - для сортировки пунктов в меню
	 * @return
	 */
	public int getPriority();
}
