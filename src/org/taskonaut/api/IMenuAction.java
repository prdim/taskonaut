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
	 * Путь в меню (например: "Файл|Экспорт") без заключительного пункта
	 * @return
	 */
	public String getMenuPath();
	
	/**
	 * Приоритет данного пункта - для сортировки пунктов в меню
	 * @return
	 */
	public int getPriority();
}
