/**
 * 
 */
package org.taskonaut.api;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс сервиса, предоставляющего меню
 * 
 * @author ProlubnikovDA
 *
 */
public interface IMenuService {
	public Map<String, List<IMenuAction>> getAllItems();
	public void bindMenuItem(IMenuAction m);
	public void ubindMenuItem(IMenuAction m);
}
